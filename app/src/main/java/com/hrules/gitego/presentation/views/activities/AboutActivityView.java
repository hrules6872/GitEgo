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

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hrules.darealmvp.DRAppCompatActivity;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.commons.AppUtils;
import com.hrules.gitego.presentation.presenters.activities.AboutActivityPresenter;

public class AboutActivityView extends DRAppCompatActivity<AboutActivityPresenter, AboutActivityPresenter.AboutView>
    implements AboutActivityPresenter.AboutView {
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.about_version) TextView aboutVersion;

  @Override protected int getLayoutResource() {
    return R.layout.about_activity;
  }

  @Override protected void initializeViews() {
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(R.string.activity_aboutTitle);
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(true);
    }

    aboutVersion.setText(AppUtils.getAppVersionText(this));
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @OnClick({ R.id.about_rateIt, R.id.about_sendFeedback, R.id.about_moreApps, R.id.about_twitter }) void onClickButton(Button button) {
    getPresenter().onClickButton(button);
  }

  @Override public void goToPlayStore() {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_playStoreURL))));
    } catch (Exception ignored) {
      showUnknownErrorToast();
    }
  }

  @Override public void sendFeedbackByEmail() {
    AppUtils.sendFeedbackByEmail(this);
  }

  @Override public void goToPlayStoreDeveloper() {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_developerPlayStoreURL))));
    } catch (Exception ignored) {
      showUnknownErrorToast();
    }
  }

  @Override public void goToTwitterDeveloper() {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_developerTwitterURL))));
    } catch (Exception ignored) {
      showUnknownErrorToast();
    }
  }

  private void showUnknownErrorToast() {
    Toast.makeText(this, getString(R.string.about_unknownError), Toast.LENGTH_SHORT).show();
  }
}
