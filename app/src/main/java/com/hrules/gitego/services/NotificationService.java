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
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.hrules.gitego.App;
import com.hrules.gitego.AppLifecycleManager;
import com.hrules.gitego.R;
import com.hrules.gitego.commons.DebugLog;
import com.hrules.gitego.data.persistence.preferences.Preferences;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.interactors.contracts.GetAuthRepo;
import com.hrules.gitego.domain.interactors.contracts.GetAuthUser;
import com.hrules.gitego.domain.internal.AccountsManager;
import com.hrules.gitego.domain.models.Account;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import com.hrules.gitego.presentation.models.GitHubAuthUser;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthRepoDateDescendingComparator;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthUserDateDescendingComparator;
import com.hrules.gitego.presentation.models.utils.ListModelUtils;
import com.hrules.gitego.presentation.views.activities.MainActivityView;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public final class NotificationService extends Service {
  private static final String PREFS_NOTIFICATION_CHECKED_TODAY = "PREFS_NOTIFICATION_CHECKED_TODAY";

  public static final int SERVICE_REQUEST_CODE = 1;
  public static final int NOTIFICATION_ID = 1;
  public static final int DEFAULT_ALARM_HOUR = 11;
  public static final int DEFAULT_ALARM_MINUTE = 0;
  public static final int DEFAULT_ALARM_SECONDS = 0;

  @Inject GitHubAPI gitHubAPI;
  @Inject AccountsManager accountsManager;
  @Inject GetAuthUser getAuthUser;
  @Inject GetAuthRepo getAuthRepo;
  @Inject Preferences preferences;

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public void onCreate() {
    super.onCreate();
    App.getApplication().getAppComponent().inject(this);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    super.onStartCommand(intent, flags, startId);
    if (!isCheckedToday()) {
      init();
      checkDataChanges();
    }
    startAgainNotificationAlarmOnlyForAPI23OrAbove();
    stopSelf(startId);
    return Service.START_NOT_STICKY;
  }

  private void init() {
    Account account = accountsManager.getDefaultAccount();
    if (!TextUtils.isEmpty(account.getToken())) {
      gitHubAPI.setAccount(account);
    }
  }

  private void setCheckedToday() {
    preferences.save(PREFS_NOTIFICATION_CHECKED_TODAY, System.currentTimeMillis());
  }

  private boolean isCheckedToday() {
    Calendar present = milliToCalendar(System.currentTimeMillis());
    Calendar past =
        milliToCalendar(preferences.getLong(PREFS_NOTIFICATION_CHECKED_TODAY, present.getTimeInMillis() - TimeUnit.DAYS.toMillis(1)));
    return TimeUnit.MILLISECONDS.toDays(present.getTimeInMillis() - past.getTimeInMillis()) <= 0;
  }

  private Calendar milliToCalendar(long millis) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millis);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar;
  }

  private void checkDataChanges() {
    Account account = gitHubAPI.getAccount();
    if (account != null && !TextUtils.isEmpty(account.getToken())) {
      setCheckedToday();

      getAuthUser.execute(account.getToken(), new GetAuthUser.Callback() {
        GitHubAuthUser gitHubAuthUser;

        @Override public void onSuccess(@NonNull List<GitHubAuthUser> response) {
          if (!response.isEmpty()) {
            Collections.sort(response, new GitHubAuthUserDateDescendingComparator());
            gitHubAuthUser = ListModelUtils.mergeAuthUserItems(response);
          }
        }

        @Override public void onFailure(@NonNull Exception exception) {
          DebugLog.e(exception.getMessage(), exception);
        }

        @Override public void onFinish() {
          if (gitHubAuthUser != null) {
            int difference = gitHubAuthUser.getFollowers() - gitHubAuthUser.getGitHubAuthUserOlder().getFollowers();
            if (difference != 0) {
              showNotification();
            }
          }
        }
      });

      getAuthRepo.execute(account.getToken(), new GetAuthRepo.Callback() {
        List<GitHubAuthRepo> list;

        @Override public void onSuccess(@NonNull List<GitHubAuthRepo> response) {
          if (!response.isEmpty()) {
            Collections.sort(response, new GitHubAuthRepoDateDescendingComparator());
            list = ListModelUtils.mergeAuthRepoItems(response);
          }
        }

        @Override public void onFailure(@NonNull Exception exception) {
          DebugLog.e(exception.getMessage(), exception);
        }

        @Override public void onFinish() {
          if (list != null) {
            boolean showNotification = false;
            for (GitHubAuthRepo item : list) {
              if (item.getWatchers_count() - item.getGitHubAuthRepoOlder().getWatchers_count() != 0) {
                showNotification = true;
                break;
              }
              if (item.getStargazers_count() - item.getGitHubAuthRepoOlder().getStargazers_count() != 0) {
                showNotification = true;
                break;
              }
              if (item.getForks_count() - item.getGitHubAuthRepoOlder().getForks_count() != 0) {
                showNotification = true;
                break;
              }
            }
            if (showNotification) {
              showNotification();
            }
          }
        }
      });
    }
  }

  private void showNotification() {
    showNotification(R.drawable.ic_notification, getString(R.string.notification_message), getString(R.string.app_name),
        getString(R.string.notification_message));
  }

  @SuppressWarnings("deprecation")
  private void showNotification(int smallIcon, @NonNull String ticker, @NonNull String title, @NonNull String content) {
    if (!AppLifecycleManager.isAppInForeground()) {
      Intent intent = new Intent(this, MainActivityView.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

      NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setWhen(System.currentTimeMillis());
      builder.setSmallIcon(smallIcon)
          .setTicker(ticker)
          .setContentTitle(title)
          .setContentText(content)
          .setAutoCancel(true)
          .setContentIntent(pendingIntent);

      ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(NOTIFICATION_ID, builder.build());
    }
  }

  private void startAgainNotificationAlarmOnlyForAPI23OrAbove() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      long triggerAtMillis = NotificationUtils.getNextNotificationTriggerAtMillis();
      PendingIntent pendingIntent = NotificationUtils.getNotificationPendingIntent(App.getApplication());
      AlarmManager alarmManager = (AlarmManager) App.getApplication().getSystemService(Context.ALARM_SERVICE);
      alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }
  }
}
