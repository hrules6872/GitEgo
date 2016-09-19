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

package com.hrules.gitego.presentation.presenters.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import com.hrules.darealmvp.DRPresenter;
import com.hrules.darealmvp.DRView;
import com.hrules.gitego.App;
import com.hrules.gitego.R;
import com.hrules.gitego.commons.DebugLog;
import com.hrules.gitego.data.exceptions.NetworkIOException;
import com.hrules.gitego.data.exceptions.NetworkUnauthorizedException;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.interactors.GetAuthRepoInteractor;
import com.hrules.gitego.domain.interactors.GetAuthUserInteractor;
import com.hrules.gitego.domain.interactors.contracts.GetAuthRepo;
import com.hrules.gitego.domain.interactors.contracts.GetAuthUser;
import com.hrules.gitego.domain.internal.AccountsManager;
import com.hrules.gitego.domain.models.Account;
import com.hrules.gitego.domain.threads.UIThreadExecutor;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import com.hrules.gitego.presentation.models.GitHubAuthUser;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthRepoDateDescendingComparator;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthUserDateDescendingComparator;
import com.hrules.gitego.presentation.models.utils.MergeUtils;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class UserFragmentPresenter extends DRPresenter<UserFragmentPresenter.UserView> {
  @Inject GitHubAPI gitHubAPI;
  @Inject AccountsManager accountsManager;
  @Inject GetAuthUserInteractor getAuthUserInteractor;
  @Inject GetAuthRepoInteractor getAuthRepoInteractor;

  @Override public void bind(@NonNull UserView view) {
    super.bind(view);
    App.getApplication().getAppComponent().inject(this);

    Account account = accountsManager.getDefaultAccount();
    if (account != null && account.getToken() != null) {
      gitHubAPI.setAccount(account);
    }
  }

  @Override public void unbind() {
    getView().unbind();
    super.unbind();
  }

  private void networkFail() {
    getView().showBriefMessage(R.string.error_networkFail);
  }

  private void loginFail() {
    getView().showBriefMessageAction(R.string.error_loginFail, R.string.action_login);
  }

  @Override protected void onResume() {
    super.onResume();
    refreshData();
  }

  private void refreshData() {
    Account account = gitHubAPI.getAccount();
    if (account != null && (account.getToken() != null && !account.getToken().isEmpty())) {
      getView().setUserLogin(account.getUser());

      getView().showLoading();
      getAuthUserInteractor.execute(account.getToken(), new GetAuthUser.Callback() {
        @Override public void onSuccess(@NonNull List<GitHubAuthUser> response) {
          if (response.size() > 0) {
            Collections.sort(response, new GitHubAuthUserDateDescendingComparator());
            final GitHubAuthUser finalGitHubAuthUser =
                new MergeUtils().mergeAuthUserItems(response);

            new UIThreadExecutor().execute(new Runnable() {
              @Override public void run() {
                getView().setUserData(finalGitHubAuthUser);
              }
            });
          }
        }

        @Override public void onFailure(@NonNull final Exception exception) {
          new UIThreadExecutor().execute(new Runnable() {
            @Override public void run() {
              if (exception instanceof NetworkUnauthorizedException) {
                loginFail();
              } else if (exception instanceof NetworkIOException) {
                networkFail();
              } else {
                DebugLog.e(exception.getMessage(), exception);
              }
            }
          });
        }

        @Override public void onFinish() {
          new UIThreadExecutor().execute(new Runnable() {
            @Override public void run() {
              getView().hideLoading();
            }
          });
        }
      });

      getView().showLoading();
      getAuthRepoInteractor.execute(account.getToken(), new GetAuthRepo.Callback() {
        @Override public void onSuccess(@NonNull List<GitHubAuthRepo> response) {
          if (response.size() > 0) {
            Collections.sort(response, new GitHubAuthRepoDateDescendingComparator());
            final List<GitHubAuthRepo> finalList = new MergeUtils().mergeAuthRepoItems(response);

            new UIThreadExecutor().execute(new Runnable() {
              @Override public void run() {
                getView().setRepoCounters(finalList);
              }
            });
          }
        }

        @Override public void onFailure(@NonNull final Exception exception) {
          new UIThreadExecutor().execute(new Runnable() {
            @Override public void run() {
              if (exception instanceof NetworkUnauthorizedException) {
                loginFail();
              } else if (exception instanceof NetworkIOException) {
                networkFail();
              } else {
                DebugLog.e(exception.getMessage(), exception);
              }
            }
          });
        }

        @Override public void onFinish() {
          new UIThreadExecutor().execute(new Runnable() {
            @Override public void run() {
              getView().hideLoading();
            }
          });
        }
      });
    }
  }

  public void doLogin() {
    getView().launchLoginActivity();
  }

  public interface UserView extends DRView {
    void unbind();

    void setUserLogin(@NonNull String userLogin);

    void launchLoginActivity();

    void setRepoCounters(@NonNull List<GitHubAuthRepo> list);

    void setUserData(@NonNull GitHubAuthUser gitHubAuthUser);

    void showLoading();

    void hideLoading();

    void showBriefMessageAction(@StringRes int message, @StringRes int action);

    void showBriefMessage(@StringRes int message);
  }
}
