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

package com.hrules.gitego.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.hrules.gitego.App;
import com.hrules.gitego.commons.SupportVersion;

public final class NotificationServiceReceiver extends BroadcastReceiver {
  public static final String ACTION_NOTIFICATION_SERVICE_START = "com.hrules.gitego.NOTIFICATION_SERVICE_START";
  public static final String ACTION_NOTIFICATION_SERVICE_STOP = "com.hrules.gitego.NOTIFICATION_SERVICE_STOP";

  @Override public void onReceive(Context context, Intent intent) {
    if (ACTION_NOTIFICATION_SERVICE_START.equals(intent.getAction())) {
      startNotificationRepeatingAlarm();
    } else if (ACTION_NOTIFICATION_SERVICE_STOP.equals(intent.getAction())) {
      stopNotificationRepeatingAlarm();
    }
  }

  private void startNotificationRepeatingAlarm() {
    stopNotificationRepeatingAlarm();

    long triggerAtMillis = NotificationUtils.getNextNotificationTriggerAtMillis();
    PendingIntent pendingIntent = NotificationUtils.getNotificationPendingIntent(App.getApplication());
    AlarmManager alarmManager = (AlarmManager) App.getApplication().getSystemService(Context.ALARM_SERVICE);
    if (SupportVersion.isMarshmallowOrAbove()) {
      alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    } else {
      alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
    }
  }

  private void stopNotificationRepeatingAlarm() {
    AlarmManager alarmManager = (AlarmManager) App.getApplication().getSystemService(Context.ALARM_SERVICE);
    alarmManager.cancel(NotificationUtils.getNotificationPendingIntent(App.getApplication()));
  }
}
