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

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hrules.darealmvp.DRAppCompatActivity;
import com.hrules.gitego.R;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessage;
import com.hrules.gitego.presentation.presenters.activities.LoginActivityPresenter;

public class LoginActivityView
    extends DRAppCompatActivity<LoginActivityPresenter, LoginActivityPresenter.LoginView>
    implements LoginActivityPresenter.LoginView {
  private ProgressDialog progress;

  @Override public int getLayoutResource() {
    return R.layout.activity_login;
  }

  @Override public void initializeViews() {
    ButterKnife.bind(this);

    progress = new ProgressDialog(this);
  }

  @OnClick({ R.id.login }) void onClickButton(Button button) {
    getPresenter().onClickButton(button);
  }

  @OnClick({ R.id.goToAppWebsite }) void onClickTextView(TextView textView) {
    getPresenter().onClickTextView(textView);
  }

  public void goToAppWebsite() {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_website))));
    } catch (Exception e) {
      showBriefMessage(String.format(getString(R.string.login_goToAppWebsiteInstructions),
          getString(R.string.app_website)));
    }
  }

  private void showBriefMessage(@NonNull String message) {
    new BriefMessage().showLong(findViewById(android.R.id.content), message);
  }

  @Override public void hideProgressDialog() {
    progress.cancel();
  }

  public void startMainActivity() {
    startActivity(new Intent(getApplicationContext(), MainActivityView.class));
    finish();
  }

  @Override public void showBriefMessage(@StringRes int message) {
    showBriefMessage(getString(message));
  }

  @Override public void showProgressDialog(@StringRes int message) {
    if (progress.isShowing()) {
      progress.cancel();
    }
    progress.setMessage(getString(message));
    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progress.setIndeterminate(true);
    progress.setCancelable(false);
    progress.show();
  }

  @Override public void launchOAuthLogin(GitHubAPI gitHubAPI) {
    gitHubAPI.launchOAuthLogin(getApplicationContext());
  }

  public void onNewIntent(Intent intent) {
    getPresenter().onNewIntent(intent);
  }
}
