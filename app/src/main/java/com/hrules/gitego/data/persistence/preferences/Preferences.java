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

package com.hrules.gitego.data.persistence.preferences;

import android.content.Context;
import android.support.annotation.NonNull;
import me.alexrs.prefs.lib.Prefs;

public class Preferences implements PreferencesInterface {
  private final Prefs preferences;

  public Preferences(Context context) {
    preferences = Prefs.with(context);
  }

  public String getString(@NonNull String key, String defaultValue) {
    return preferences.getString(key, defaultValue);
  }

  public void putString(@NonNull String key, String value) {
    preferences.save(key, value);
  }

  public long getLong(@NonNull String key, long defaultValue) {
    return preferences.getLong(key, defaultValue);
  }

  @Override public boolean getBoolean(@NonNull String key, boolean defaultValue) {
    return preferences.getBoolean(key, defaultValue);
  }

  @Override public void save(@NonNull String key, long value) {
    preferences.save(key, value);
  }

  @Override public void save(@NonNull String key, String value) {
    preferences.save(key, value);
  }

  @Override public void save(@NonNull String key, boolean value) {
    preferences.save(key, value);
  }

  public void remove(@NonNull String key) {
    preferences.remove(key);
  }
}
