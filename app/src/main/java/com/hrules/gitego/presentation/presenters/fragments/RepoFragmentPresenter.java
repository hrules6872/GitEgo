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

package com.hrules.gitego.presentation.presenters.fragments;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.hrules.darealmvp.DRMVPPresenter;
import com.hrules.darealmvp.DRMVPView;
import com.hrules.gitego.App;
import com.hrules.gitego.commons.DebugLog;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.errors.NetworkIOError;
import com.hrules.gitego.domain.errors.NetworkUnauthorizedError;
import com.hrules.gitego.domain.errors.base.Error;
import com.hrules.gitego.domain.interactors.contracts.DeleteAuthRepo;
import com.hrules.gitego.domain.interactors.contracts.GetAuthRepo;
import com.hrules.gitego.domain.internal.AccountsManager;
import com.hrules.gitego.domain.models.Account;
import com.hrules.gitego.domain.threads.UIThreadExecutor;
import com.hrules.gitego.presentation.bus.BoolEvent;
import com.hrules.gitego.presentation.commons.StringResUtils;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthRepoDateDescendingComparator;
import com.hrules.gitego.presentation.models.utils.ListModelUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

public final class RepoFragmentPresenter extends DRMVPPresenter<RepoFragmentPresenter.Contract> {
  @Inject GitHubAPI gitHubAPI;
  @Inject AccountsManager accountsManager;
  @Inject GetAuthRepo getAuthRepo;
  @Inject DeleteAuthRepo deleteAuthRepo;
  @Inject UIThreadExecutor uiThreadExecutor;
  @Inject StringResUtils stringResUtils;

  private List<GitHubAuthRepo> listToBeDeleted = new ArrayList<>();

  @Override public void bind(@NonNull Contract view) {
    super.bind(view);
    App.getApplication().getAppComponent().inject(this);

    Account account = accountsManager.getDefaultAccount();
    if (!TextUtils.isEmpty(account.getToken())) {
      gitHubAPI.setAccount(account);
    }
  }

  public void onViewResume() {
    refreshData();
  }

  private void refreshData() {
    Account account = gitHubAPI.getAccount();
    if (account != null && !TextUtils.isEmpty(account.getToken())) {

      showLoading();
      getAuthRepo.execute(account.getToken(), new GetAuthRepo.Callback() {
        @Override public void onSuccess(@NonNull List<GitHubAuthRepo> response) {
          if (!response.isEmpty()) {
            Collections.sort(response, new GitHubAuthRepoDateDescendingComparator());
            List<GitHubAuthRepo> list = ListModelUtils.mergeAuthRepoItems(response);
            listToBeDeleted = ListModelUtils.getNotValidAuthRepoItems(list);
            list = ListModelUtils.getValidAuthRepoItems(list);

            final List<GitHubAuthRepo> finalList = list;
            uiThreadExecutor.execute(new Runnable() {
              @Override public void run() {
                getView().updateList(finalList);
              }
            });
          }
        }

        @Override public void onFailure(@NonNull final Error error) {
          if (error instanceof NetworkUnauthorizedError) {
            loginFail();
          } else if (error instanceof NetworkIOError) {
            networkFail();
          } else {
            DebugLog.e(error.getMessage(), error.getException());
          }
        }

        @Override public void onFinish() {
          syncData();
          hideLoading();

          uiThreadExecutor.execute(new Runnable() {
            @Override public void run() {
              getView().updateListState();
            }
          });
        }
      });
    }
  }

  private void hideLoading() {
    EventBus.getDefault().post(new BoolEvent(false));
  }

  private void showLoading() {
    EventBus.getDefault().post(new BoolEvent(true));
  }

  private void syncData() {
    if (!listToBeDeleted.isEmpty()) {
      deleteAuthRepo.execute(listToBeDeleted, new DeleteAuthRepo.Callback() {
        @Override public void onFailure(@NonNull Error error) {
          DebugLog.e(error.getMessage(), error.getException());
        }
      });
    }
  }

  private void networkFail() {
    getView().showBriefMessage(stringResUtils.getNetworkFail());
  }

  private void loginFail() {
    getView().showBriefMessageAction(stringResUtils.getLoginFail(), stringResUtils.getActionLogin());
  }

  public interface Contract extends DRMVPView {
    void updateList(@NonNull List<GitHubAuthRepo> list);

    void updateListState();

    void showBriefMessage(@NonNull String message);

    void showBriefMessageAction(@NonNull String message, @NonNull String action);
  }
}
