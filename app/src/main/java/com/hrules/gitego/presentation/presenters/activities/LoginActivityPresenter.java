/*
 * Copyright (c) 2017. Héctor de Isidro - hrules6872
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hrules.gitego.presentation.presenters.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.hrules.darealmvp.DRMVPPresenter;
import com.hrules.darealmvp.DRMVPView;
import com.hrules.gitego.App;
import com.hrules.gitego.BuildConfig;
import com.hrules.gitego.commons.DebugLog;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.errors.NetworkIOError;
import com.hrules.gitego.domain.errors.NetworkUnauthorizedError;
import com.hrules.gitego.domain.errors.base.Error;
import com.hrules.gitego.domain.interactors.contracts.GetAccessToken;
import com.hrules.gitego.domain.interactors.contracts.GetAuthUser;
import com.hrules.gitego.domain.internal.AccountsManager;
import com.hrules.gitego.domain.models.Account;
import com.hrules.gitego.presentation.commons.PreConditions;
import com.hrules.gitego.presentation.commons.StringResUtils;
import com.hrules.gitego.presentation.models.GitHubAccessToken;
import com.hrules.gitego.presentation.models.GitHubAuthUser;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthUserDateDescendingComparator;
import dagger.Lazy;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public final class LoginActivityPresenter extends DRMVPPresenter<LoginActivityPresenter.Contract> {
  @Inject GitHubAPI gitHubAPI;
  @Inject GetAccessToken getAccessToken;
  @Inject GetAuthUser getAuthUser;
  @Inject Lazy<AccountsManager> accountsManager;
  @Inject StringResUtils stringResUtils;

  private boolean showLoginFail = false;
  private boolean showNetworkFail = false;

  @Override public void bind(@NonNull Contract view) {
    super.bind(view);
    App.getApplication().getAppComponent().inject(this);
  }

  public void onNewIntent(Intent intent) {
    getView().showProgressDialog();

    showLoginFail = false;
    showNetworkFail = false;

    getAccessToken.execute(intent, BuildConfig.GITHUB_API_CALLBACKURL, new GetAccessToken.Callback() {
      @Override public void onSuccess(@NonNull final GitHubAccessToken gitHubAccessToken) {
        getAuthUser.execute(gitHubAccessToken.getAccessToken(), new GetAuthUser.Callback() {
          @Override public void onSuccess(@NonNull List<GitHubAuthUser> response) {
            if (!response.isEmpty()) {
              Collections.sort(response, new GitHubAuthUserDateDescendingComparator());
              GitHubAuthUser gitHubAuthUser = response.get(0);

              String user = gitHubAuthUser.getUser();
              String type = gitHubAuthUser.getType();
              String token = gitHubAccessToken.getAccessToken();
              Account account = Account.builder().user(user).type(type).token(token).defaultUser(true).build();

              if (!PreConditions.isStringNullOrEmpty(account.getUser()) && !PreConditions.isStringNullOrEmpty(account.getToken())) {
                gitHubAPI.setAccount(account);
                accountsManager.get().addAccount(account);
                getView().startMainActivity();
              } else {
                showLoginFail = true;
              }
            }
          }

          @Override public void onFailure(@NonNull Error error) {
            if (error instanceof NetworkUnauthorizedError) {
              showLoginFail = true;
            } else if (error instanceof NetworkIOError) {
              showNetworkFail = true;
            } else {
              DebugLog.e(error.getMessage(), error.getException());
            }
          }

          @Override public void onFinish() {
            getView().hideProgressDialog();

            if (showNetworkFail) {
              networkFail();
            } else if (showLoginFail) {
              loginFail();
            }
          }
        });
      }

      @Override public void onFailure(@NonNull Error error) {
        DebugLog.e(error.getMessage(), error.getException());

        getView().hideProgressDialog();
        loginFail();
      }
    });
  }

  private void loginFail() {
    getView().showBriefMessage(stringResUtils.getLoginFail());
  }

  private void networkFail() {
    getView().showBriefMessage(stringResUtils.getNetworkFail());
  }

  public void onLoginClick() {
    gitHubAPI.launchOAuthLogin(App.getApplication());
  }

  public interface Contract extends DRMVPView {
    void startMainActivity();

    void showProgressDialog();

    void hideProgressDialog();

    void showBriefMessage(@NonNull String message);
  }
}
