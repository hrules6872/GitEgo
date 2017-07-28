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

package com.hrules.gitego;

import android.app.Application;
import com.hrules.gitego.data.persistence.preferences.Preferences;
import com.hrules.gitego.di.components.AppComponent;
import com.hrules.gitego.di.components.DaggerAppComponent;
import com.hrules.gitego.di.modules.AppModule;
import com.hrules.gitego.services.NotificationUtils;
import javax.inject.Inject;

public final class App extends Application {
  private static App application;
  private AppComponent appComponent;

  @Inject Preferences preferences;

  @Override public void onCreate() {
    super.onCreate();
    application = this;

    registerActivityLifecycleCallbacks(new AppLifecycleManager());
    initComponents();

    if (preferences.getBoolean(AppConstants.PREFS.NOTIFICATIONS, AppConstants.PREFS_DEFAULTS.NOTIFICATIONS_DEFAULT)) {
      NotificationUtils.startNotificationService(this);
    }
  }

  public static App getApplication() {
    return application;
  }

  private void initComponents() {
    appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    appComponent.inject(this);
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}
