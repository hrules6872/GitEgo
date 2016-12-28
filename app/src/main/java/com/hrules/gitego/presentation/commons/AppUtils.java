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

package com.hrules.gitego.presentation.commons;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.hrules.gitego.R;

public class AppUtils {
  private static final String DEFAULT_VERSION_CODE = "1";
  private static final String DEFAULT_VERSION_NAME = "1";

  private static final String SCHEME_MAILTO = "mailto";

  private AppUtils() {
  }

  public static String getAppVersionText(@NonNull Context context) {
    String versionName = DEFAULT_VERSION_NAME;
    String versionCode = DEFAULT_VERSION_CODE;
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      versionName = packageInfo.versionName;
      versionCode = String.valueOf(packageInfo.versionCode);
    } catch (PackageManager.NameNotFoundException ignored) {
    }

    return String.format(context.getString(R.string.text_appVersionFormatted), versionName, versionCode);
  }

  public static void sendFeedbackByEmail(@NonNull Context context) {
    Intent intent =
        new Intent(Intent.ACTION_SENDTO, Uri.fromParts(SCHEME_MAILTO, context.getString(R.string.feedback_developerEmail), null));
    intent.putExtra(Intent.EXTRA_SUBJECT, String.format(context.getString(R.string.feedback_emailSubject), getAppVersionText(context)));
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.feedback_emailChooserTitle)));
  }
}
