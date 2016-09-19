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

import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import com.hrules.darealmvp.DRPresenter;
import com.hrules.darealmvp.DRView;
import com.hrules.gitego.App;
import com.hrules.gitego.AppConstants;
import com.hrules.gitego.R;
import com.hrules.gitego.data.persistence.preferences.Preferences;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.internal.AccountsManager;
import com.hrules.gitego.domain.models.Account;
import javax.inject.Inject;

public class MainActivityPresenter extends DRPresenter<MainActivityPresenter.MainView> {
  @Inject GitHubAPI gitHubAPI;
  @Inject Preferences preferences;
  @Inject AccountsManager accountsManager;

  @Override public void bind(@NonNull MainView view) {
    super.bind(view);
    App.getApplication().getAppComponent().inject(this);
  }

  @Override public void onViewReady() {
    getView().removeNotification();

    Account account = accountsManager.getDefaultAccount();
    if (account == null || (account.getToken() == null || account.getToken().isEmpty())) {
      doLogin();
    } else {
      gitHubAPI.setAccount(account);
    }
  }

  public void onMenuItemClick(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_notifications:
        if (item.isChecked()) {
          item.setChecked(false);
          getView().stopNotificationServiceReceiver();
        } else {
          item.setChecked(true);
          getView().startNotificationServiceReceiver();
        }
        preferences.save(AppConstants.PREFS.NOTIFICATIONS, item.isChecked());
        break;

      case R.id.menu_signOut:
        accountsManager.removeAccount(accountsManager.getDefaultAccount());
        getView().launchLoginActivity();
        getView().finish();
        break;

      default:
        throw new UnsupportedOperationException();
    }
  }

  private void doLogin() {
    getView().launchLoginActivity();
  }

  public void onCreateOptionsMenu(@NonNull Menu menu) {
    menu.findItem(R.id.menu_notifications)
        .setChecked(preferences.getBoolean(AppConstants.PREFS.NOTIFICATIONS, AppConstants.PREFS_DEFAULTS.NOTIFICATIONS_DEFAULT));
  }

  public void goToPlayStore() {
    getView().goToPlayStore();
  }

  public interface MainView extends DRView {
    void launchLoginActivity();

    void finish();

    void startNotificationServiceReceiver();

    void stopNotificationServiceReceiver();

    void removeNotification();

    void goToPlayStore();
  }
}
