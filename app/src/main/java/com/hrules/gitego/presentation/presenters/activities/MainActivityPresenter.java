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

import android.support.annotation.NonNull;
import com.hrules.darealmvp.DRMVPPresenter;
import com.hrules.darealmvp.DRMVPView;
import com.hrules.gitego.App;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.internal.AccountsManager;
import com.hrules.gitego.domain.models.Account;
import com.hrules.gitego.presentation.commons.PreConditions;
import javax.inject.Inject;

public final class MainActivityPresenter extends DRMVPPresenter<MainActivityPresenter.Contract> {
  @Inject GitHubAPI gitHubAPI;
  @Inject AccountsManager accountsManager;

  @Override public void bind(@NonNull Contract view) {
    super.bind(view);
    App.getApplication().getAppComponent().inject(this);
  }

  public void onViewReady() {
    getView().removeNotification();

    Account account = accountsManager.getDefaultAccount();
    if (PreConditions.isStringNullOrEmpty(account.getToken())) {
      doLogin();
    } else {
      gitHubAPI.setAccount(account);
    }
  }

  private void doLogin() {
    getView().launchLoginActivity();
  }

  public void signOut() {
    accountsManager.removeAccount(accountsManager.getDefaultAccount());
  }

  public interface Contract extends DRMVPView {
    void launchLoginActivity();

    void removeNotification();
  }
}