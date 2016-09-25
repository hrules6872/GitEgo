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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import java.util.Calendar;

public class NotificationServiceReceiver extends BroadcastReceiver {
  public static final String ACTION_START_NOTIFICATION_SERVICE = "com.hrules.gitego.START_NOTIFICATION_SERVICE";
  public static final String ACTION_STOP_NOTIFICATION_SERVICE = "com.hrules.gitego.STOP_NOTIFICATION_SERVICE";

  @Override public void onReceive(Context context, Intent intent) {
    if (ACTION_START_NOTIFICATION_SERVICE.equals(intent.getAction())) {
      startNotificationRepeatingAlarm(context);
    } else if (ACTION_STOP_NOTIFICATION_SERVICE.equals(intent.getAction())) {
      stopNotificationRepeatingAlarm(context);
    }
  }

  private void startNotificationRepeatingAlarm(@NonNull Context context) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, NotificationService.DEFAULT_ALARM_HOUR);
    calendar.set(Calendar.MINUTE, NotificationService.DEFAULT_ALARM_MINUTE);

    Intent intent = new Intent(context, NotificationService.class);
    PendingIntent pendingIntent =
        PendingIntent.getService(context, NotificationService.SERVICE_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
  }

  private void stopNotificationRepeatingAlarm(@NonNull Context context) {
    Intent intent = new Intent(context, NotificationService.class);
    PendingIntent pendingIntent =
        PendingIntent.getService(context, NotificationService.SERVICE_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.cancel(pendingIntent);
  }
}
