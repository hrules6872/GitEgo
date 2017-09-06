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

import com.hrules.gitego.di.modules.DomainModule;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.interactors.base.InteractorExecutor;
import com.hrules.gitego.domain.interactors.contracts.DeleteAuthRepo;
import com.hrules.gitego.domain.interactors.contracts.GetAccessToken;
import com.hrules.gitego.domain.interactors.contracts.GetAuthRepo;
import com.hrules.gitego.domain.interactors.contracts.GetAuthUser;
import com.hrules.gitego.domain.threads.UIThreadExecutor;
import dagger.Component;

@Component(dependencies = DataComponent.class, modules = DomainModule.class) interface DomainComponent {
  GitHubAPI gitHubAPI();

  InteractorExecutor interactorExecutor();

  UIThreadExecutor uiThreadExecutor();

  GetAccessToken getAccessToken();

  GetAuthUser getAuthUser();

  GetAuthRepo getAuthRepo();

  DeleteAuthRepo deleteAuthRepo();
}
