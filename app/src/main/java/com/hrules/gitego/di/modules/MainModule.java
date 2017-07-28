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

package com.hrules.gitego.di.modules;

import com.hrules.gitego.App;
import com.hrules.gitego.data.persistence.preferences.Preferences;
import com.hrules.gitego.domain.internal.AccountsManager;
import dagger.Module;
import dagger.Provides;

@Module public final class MainModule {
  @Provides Preferences providesPreferences(App application) {
    return new Preferences(application);
  }

  @Provides AccountsManager providesAccountManager(App application) {
    return new AccountsManager(application);
  }
}
