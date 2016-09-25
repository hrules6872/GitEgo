/*
 * Copyright (c) 2016. Héctor de Isidro - hrules6872
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
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import com.hrules.darealmvp.DRPresenter;
import com.hrules.darealmvp.DRView;
import com.hrules.gitego.App;
import com.hrules.gitego.BuildConfig;
import com.hrules.gitego.R;
import com.hrules.gitego.data.exceptions.NetworkIOException;
import com.hrules.gitego.data.exceptions.NetworkUnauthorizedException;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.interactors.GetAccessTokenInteractor;
import com.hrules.gitego.domain.interactors.GetAuthUserInteractor;
import com.hrules.gitego.domain.interactors.contracts.GetAccessToken;
import com.hrules.gitego.domain.interactors.contracts.GetAuthUser;
import com.hrules.gitego.domain.internal.AccountsManager;
import com.hrules.gitego.domain.models.Account;
import com.hrules.gitego.presentation.models.GitHubAccessToken;
import com.hrules.gitego.presentation.models.GitHubAuthUser;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthUserDateDescendingComparator;
import dagger.Lazy;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class LoginActivityPresenter extends DRPresenter<LoginActivityPresenter.LoginView> {
  @Inject GitHubAPI gitHubAPI;
  @Inject GetAccessTokenInteractor getAccessTokenInteractor;
  @Inject GetAuthUserInteractor getAuthUserInteractor;
  @Inject Lazy<AccountsManager> accountsManager;

  private boolean showLoginFail = false;
  private boolean showNetworkFail = false;

  @Override public void bind(@NonNull LoginView view) {
    super.bind(view);
    App.getApplication().getAppComponent().inject(this);
  }

  public void onNewIntent(Intent intent) {
    getView().showProgressDialog(R.string.login_gettingUser);

    showLoginFail = false;
    showNetworkFail = false;

    getAccessTokenInteractor.execute(intent, BuildConfig.GITHUB_API_CALLBACKURL, new GetAccessToken.Callback() {
      @Override public void onSuccess(@NonNull final GitHubAccessToken gitHubAccessToken) {
        getAuthUserInteractor.execute(gitHubAccessToken.getAccess_token(), new GetAuthUser.Callback() {
          @Override public void onSuccess(@NonNull List<GitHubAuthUser> response) {
            if (response.size() > 0) {
              Collections.sort(response, new GitHubAuthUserDateDescendingComparator());
              GitHubAuthUser gitHubAuthUser = response.get(0);

              String login = gitHubAuthUser.getLogin();
              String type = gitHubAuthUser.getType();
              String token = gitHubAccessToken.getAccess_token();
              boolean defaultAccount = true;
              Account account = new Account(login, type, token, defaultAccount);

              if (!TextUtils.isEmpty(account.getUser()) && !TextUtils.isEmpty(account.getToken())) {
                gitHubAPI.setAccount(account);
                accountsManager.get().addAccount(account);
                getView().startMainActivity();
              }
            }
          }

          @Override public void onFailure(@NonNull Exception exception) {
            if (exception instanceof NetworkUnauthorizedException) {
              showLoginFail = true;
            } else if (exception instanceof NetworkIOException) {
              showNetworkFail = true;
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

      @Override public void onFailure(@NonNull Exception exception) {
        getView().hideProgressDialog();
        loginFail();
      }
    });
  }

  private void loginFail() {
    getView().showBriefMessage(R.string.error_loginFail);
  }

  private void networkFail() {
    getView().showBriefMessage(R.string.error_networkFail);
  }

  public void onClickButton(Button button) {
    switch (button.getId()) {
      case R.id.login:
        getView().launchOAuthLogin(gitHubAPI);
        break;

      default:
        throw new UnsupportedOperationException();
    }
  }

  public void onClickTextView(TextView textView) {
    switch (textView.getId()) {
      case R.id.about:
        getView().launchAboutActivity();
        break;

      default:
        throw new UnsupportedOperationException();
    }
  }

  public interface LoginView extends DRView {
    void startMainActivity();

    void launchOAuthLogin(@NonNull GitHubAPI gitHubAPI);

    void launchAboutActivity();

    void showProgressDialog(@StringRes int message);

    void hideProgressDialog();

    void showBriefMessage(@StringRes int message);
  }
}
