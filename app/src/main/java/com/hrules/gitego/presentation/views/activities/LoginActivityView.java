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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.percent.PercentFrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessage;
import com.hrules.gitego.presentation.presenters.activities.LoginActivityPresenter;
import com.hrules.gitego.presentation.views.activities.base.DRMVPAppCompatActivity;

public final class LoginActivityView extends DRMVPAppCompatActivity<LoginActivityPresenter, LoginActivityPresenter.Contract>
    implements LoginActivityPresenter.Contract {
  @SuppressWarnings("deprecation") @BindView(R.id.rootLayout) PercentFrameLayout rootLayout;

  @SuppressWarnings("deprecation") private ProgressDialog progress;

  @Override public int getLayoutResId() {
    return R.layout.activity_login;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeViews();
  }

  @SuppressWarnings("deprecation") private void initializeViews() {
    ButterKnife.bind(this);
    progress = new ProgressDialog(this);
  }

  public void startMainActivity() {
    startActivity(new Intent(getApplicationContext(), MainActivityView.class));
    finish();
  }

  private void launchAboutActivity() {
    startActivity(new Intent(this, AboutActivityView.class));
  }

  @SuppressWarnings("deprecation") public void showProgressDialog() {
    if (progress.isShowing()) {
      progress.cancel();
    }
    progress.setMessage(getString(R.string.login_gettingUser));
    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progress.setIndeterminate(true);
    progress.setCancelable(false);
    progress.show();
  }

  public void hideProgressDialog() {
    progress.cancel();
  }

  public void showBriefMessage(@StringRes int message) {
    BriefMessage.showLong(rootLayout, getString(message));
  }

  @OnClick(R.id.login) void onLoginClick() {
    getPresenter().onLoginClick();
  }

  @OnClick(R.id.about) void onAboutClick() {
    launchAboutActivity();
  }

  public void onNewIntent(Intent intent) {
    getPresenter().onNewIntent(intent);
  }
}
