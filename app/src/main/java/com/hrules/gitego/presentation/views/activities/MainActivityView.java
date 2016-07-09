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

package com.hrules.gitego.presentation.views.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hrules.darealmvp.DRAppCompatActivity;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessage;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessageListener;
import com.hrules.gitego.presentation.communicator.BoolStateMessage;
import com.hrules.gitego.presentation.communicator.CommunicatorConstants;
import com.hrules.gitego.presentation.communicator.base.Communicator;
import com.hrules.gitego.presentation.presenters.activities.MainActivityPresenter;
import com.hrules.gitego.presentation.views.fragments.RepoFragmentView;
import com.hrules.gitego.presentation.views.fragments.UserFragmentView;
import com.hrules.gitego.services.NotificationService;
import com.hrules.gitego.services.NotificationServiceReceiver;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivityView
    extends DRAppCompatActivity<MainActivityPresenter, MainActivityPresenter.MainView>
    implements MainActivityPresenter.MainView, Communicator {
  @BindView(R.id.rootLayout) CoordinatorLayout rootLayout;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.progressBar) ProgressBar progressBar;

  private static final AtomicInteger refreshVisibilityCounter = new AtomicInteger();

  @Override public int getLayoutResource() {
    return R.layout.activity_main;
  }

  @Override public void initializeViews() {
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.fragmentUser, new UserFragmentView())
          .commit();
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.fragmentRepo, new RepoFragmentView())
          .commit();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    getPresenter().onCreateOptionsMenu(menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_notifications:
      case R.id.menu_signOut:
        getPresenter().onMenuItemClick(item);
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void launchLoginActivity() {
    startActivity(new Intent(getApplicationContext(), LoginActivityView.class));
    finish();
  }

  @Override public void startNotificationServiceReceiver() {
    sendBroadcast(new Intent(NotificationServiceReceiver.ACTION_START_NOTIFICATION_SERVICE));
  }

  @Override public void stopNotificationServiceReceiver() {
    sendBroadcast(new Intent(NotificationServiceReceiver.ACTION_STOP_NOTIFICATION_SERVICE));
  }

  @Override public void removeNotification() {
    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.cancel(NotificationService.NOTIFICATION_ID);
  }

  @Override public void showBriefMessageAction(@StringRes int message, @StringRes int action) {
    new BriefMessage().showActionIndefinite(rootLayout, getString(message), getString(action),
        new BriefMessageListener() {
          @Override public void onClick() {
            getPresenter().goToPlayStore();
          }
        });
  }

  @Override public void goToPlayStore() {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_playStore))));
    } catch (Exception ignored) {
    }
  }

  @Override public void onMessage(BoolStateMessage message) {
    if (CommunicatorConstants.ACTION_SHOW_LOADING.equals(message.getAction())) {
      if (message.isState() && refreshVisibilityCounter.incrementAndGet() == 1) {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
      } else if (!message.isState() && refreshVisibilityCounter.decrementAndGet() == 0) {
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

        getPresenter().checkUserRating();
      }
    }
  }
}
