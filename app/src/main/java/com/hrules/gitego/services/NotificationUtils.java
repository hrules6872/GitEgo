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

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import java.util.Calendar;

public class NotificationUtils {
  static long getNextNotificationTriggerAtMillis() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, 1);
    calendar.set(Calendar.HOUR_OF_DAY, NotificationService.DEFAULT_ALARM_HOUR);
    calendar.set(Calendar.MINUTE, NotificationService.DEFAULT_ALARM_MINUTE);
    calendar.set(Calendar.SECOND, NotificationService.DEFAULT_ALARM_SECONDS);
    return calendar.getTimeInMillis();
  }

  static PendingIntent getNotificationPendingIntent(@NonNull Context context) {
    Intent intent = new Intent(context, NotificationService.class);
    return PendingIntent.getService(context, NotificationService.SERVICE_REQUEST_CODE, intent,
        PendingIntent.FLAG_UPDATE_CURRENT);
  }

  public static void startNotificationService(@NonNull Context context) {
    context.sendBroadcast(new Intent(NotificationServiceReceiver.ACTION_NOTIFICATION_SERVICE_START));
  }

  public static void stopNotificationService(@NonNull Context context) {
    context.sendBroadcast(new Intent(NotificationServiceReceiver.ACTION_NOTIFICATION_SERVICE_STOP));
  }
}
