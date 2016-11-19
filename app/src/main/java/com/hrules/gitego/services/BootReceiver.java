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

package com.hrules.gitego.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.hrules.gitego.App;
import com.hrules.gitego.AppConstants;
import com.hrules.gitego.data.persistence.preferences.Preferences;

public class BootReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    Preferences preferences = new Preferences(context);
    if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) && preferences.getBoolean(
        AppConstants.PREFS.NOTIFICATIONS, AppConstants.PREFS_DEFAULTS.NOTIFICATIONS_DEFAULT)) {
      NotificationUtils.startNotificationService(App.getApplication());
    }
  }
}