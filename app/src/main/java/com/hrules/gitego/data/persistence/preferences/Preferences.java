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

package com.hrules.gitego.data.persistence.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Preferences {
  private final SharedPreferences preferences;

  public Preferences(@NonNull Context context) {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  public String getString(@NonNull String key, @Nullable String defaultValue) {
    return preferences.getString(key, defaultValue);
  }

  public long getLong(@NonNull String key, long defaultValue) {
    return preferences.getLong(key, defaultValue);
  }

  public boolean getBoolean(@NonNull String key, boolean defaultValue) {
    return preferences.getBoolean(key, defaultValue);
  }

  public void save(@NonNull String key, long value) {
    preferences.edit().putLong(key, value).apply();
  }

  public void save(@NonNull String key, @Nullable String value) {
    preferences.edit().putString(key, value).apply();
  }

  public void save(@NonNull String key, boolean value) {
    preferences.edit().putBoolean(key, value).apply();
  }

  public void remove(@NonNull String key) {
    preferences.edit().remove(key).apply();
  }
}
