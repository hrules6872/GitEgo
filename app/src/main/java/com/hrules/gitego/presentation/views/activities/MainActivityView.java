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

package com.hrules.gitego.presentation.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hrules.gitego.App;
import com.hrules.gitego.AppConstants;
import com.hrules.gitego.R;
import com.hrules.gitego.data.persistence.preferences.Preferences;
import com.hrules.gitego.presentation.bus.BoolEvent;
import com.hrules.gitego.presentation.presenters.activities.MainActivityPresenter;
import com.hrules.gitego.presentation.views.activities.base.DRMVPAppCompatActivity;
import com.hrules.gitego.presentation.views.fragments.RepoFragmentView;
import com.hrules.gitego.presentation.views.fragments.UserFragmentView;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public final class MainActivityView extends DRMVPAppCompatActivity<MainActivityPresenter, MainActivityPresenter.Contract>
    implements MainActivityPresenter.Contract {
  @BindView(R.id.rootLayout) CoordinatorLayout rootLayout;
  @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout collapsingToolbar;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.progressBar) ProgressBar progressBar;

  @Inject Preferences preferences;

  private static final AtomicInteger refreshVisibilityCounter = new AtomicInteger();

  @Override public int getLayoutResId() {
    return R.layout.activity_main;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    App.getApplication().getAppComponent().inject(this);
    initializeViews();
    EventBus.getDefault().register(this);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().replace(R.id.fragmentUser, new UserFragmentView()).commit();
      getSupportFragmentManager().beginTransaction().replace(R.id.fragmentRepo, new RepoFragmentView()).commit();
    }

    getPresenter().onViewReady();
  }

  private void initializeViews() {
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    menu.findItem(R.id.menu_notifications)
        .setChecked(preferences.getBoolean(AppConstants.PREFS.NOTIFICATIONS, AppConstants.PREFS_DEFAULTS.NOTIFICATIONS_DEFAULT));
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_notifications:
        item.setChecked(!item.isChecked());
        preferences.save(AppConstants.PREFS.NOTIFICATIONS, item.isChecked());
        getPresenter().startOrStopNotificationServiceReceiver(item.isChecked());
        break;

      case R.id.menu_signOut:
        getPresenter().signOut();
        launchLoginActivity();
        finish();
        break;

      case R.id.menu_about:
        launchAboutActivity();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
    EventBus.getDefault().unregister(this);
    super.onDestroy();
  }

  public void launchLoginActivity() {
    startActivity(new Intent(getApplicationContext(), LoginActivityView.class));
    finish();
  }

  private void launchAboutActivity() {
    startActivity(new Intent(this, AboutActivityView.class));
  }

  @SuppressWarnings("unused") @Subscribe(threadMode = ThreadMode.MAIN) public void onEvent(@NonNull final BoolEvent boolEvent) {
    if (boolEvent.isState() && refreshVisibilityCounter.incrementAndGet() == 1) {
      progressBar.setIndeterminate(true);
      progressBar.setVisibility(View.VISIBLE);
    } else if (!boolEvent.isState() && refreshVisibilityCounter.decrementAndGet() == 0) {
      progressBar.setIndeterminate(false);
      progressBar.setVisibility(View.GONE);
    }
  }
}
