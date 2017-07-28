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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hrules.gitego.R;

import static android.webkit.WebView.SCHEME_MAILTO;

public final class AboutActivityView extends AppCompatActivity {
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.about_version) TextView aboutVersion;

  private static final String DEFAULT_VERSION_CODE = "1";
  private static final String DEFAULT_VERSION_NAME = "1";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about_activity);
    initializeViews();
  }

  private void initializeViews() {
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(R.string.activity_aboutTitle);
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(true);
    }

    aboutVersion.setText(getAppVersionText());
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private String getAppVersionText() {
    String versionName = DEFAULT_VERSION_NAME;
    String versionCode = DEFAULT_VERSION_CODE;
    try {
      PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      versionName = packageInfo.versionName;
      versionCode = String.valueOf(packageInfo.versionCode);
    } catch (PackageManager.NameNotFoundException ignored) {
    }

    return String.format(getString(R.string.text_appVersionFormatted), versionName, versionCode);
  }

  @OnClick({
      R.id.about_rateIt, R.id.about_sendFeedback, R.id.about_twitter, R.id.about_moreApps, R.id.about_sourceCode
  }) void onClickButton(Button button) {
    switch (button.getId()) {
      case R.id.about_rateIt:
        goToPlayStore();
        break;

      case R.id.about_sendFeedback:
        sendFeedbackByEmail();
        break;

      case R.id.about_moreApps:
        goToPlayStoreDeveloper();
        break;

      case R.id.about_twitter:
        goToTwitterDeveloper();
        break;

      case R.id.about_sourceCode:
        goToSourceCode();
        break;
    }
  }

  private void goToPlayStore() {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_playStoreURL))));
    } catch (Exception ignored) {
      showUnknownError();
    }
  }

  private void sendFeedbackByEmail() {
    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(SCHEME_MAILTO, getString(R.string.feedback_developerEmail), null));
    intent.putExtra(Intent.EXTRA_SUBJECT, String.format(getString(R.string.feedback_emailSubject), getAppVersionText()));
    startActivity(Intent.createChooser(intent, getString(R.string.feedback_emailChooserTitle)));
  }

  private void goToPlayStoreDeveloper() {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_developerPlayStoreURL))));
    } catch (Exception ignored) {
      showUnknownError();
    }
  }

  private void goToTwitterDeveloper() {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_developerTwitterURL))));
    } catch (Exception ignored) {
      showUnknownError();
    }
  }

  private void goToSourceCode() {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_sourceCodeURL))));
    } catch (Exception e) {
      showUnknownError();
    }
  }

  private void showUnknownError() {
    Toast.makeText(this, getString(R.string.about_unknownError), Toast.LENGTH_SHORT).show();
  }
}
