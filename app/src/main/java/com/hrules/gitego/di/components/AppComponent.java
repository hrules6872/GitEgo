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

package com.hrules.gitego.di.components;

import com.hrules.gitego.App;
import com.hrules.gitego.di.modules.AppModule;
import com.hrules.gitego.di.modules.DataModule;
import com.hrules.gitego.di.modules.DomainModule;
import com.hrules.gitego.di.modules.MainModule;
import com.hrules.gitego.presentation.presenters.activities.LoginActivityPresenter;
import com.hrules.gitego.presentation.presenters.activities.MainActivityPresenter;
import com.hrules.gitego.presentation.presenters.fragments.RepoFragmentPresenter;
import com.hrules.gitego.presentation.presenters.fragments.UserFragmentPresenter;
import com.hrules.gitego.presentation.views.activities.MainActivityView;
import com.hrules.gitego.services.NotificationService;
import dagger.Component;

@Component(modules = { AppModule.class, DomainModule.class, DataModule.class, MainModule.class }) public interface AppComponent {
  App application();

  void inject(App application);

  // activities
  void inject(MainActivityView activity);

  // presenters
  void inject(MainActivityPresenter presenter);

  void inject(LoginActivityPresenter presenter);

  void inject(RepoFragmentPresenter presenter);

  void inject(UserFragmentPresenter presenter);

  // services
  void inject(NotificationService service);
}
