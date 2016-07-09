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

import com.hrules.gitego.BuildConfig;
import com.hrules.gitego.data.network.Network;
import com.hrules.gitego.data.repository.base.Repository;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.interactors.GetAccessTokenInteractor;
import com.hrules.gitego.domain.interactors.GetAuthRepoInteractor;
import com.hrules.gitego.domain.interactors.GetAuthUserInteractor;
import com.hrules.gitego.domain.threads.InteractorExecutor;
import com.hrules.gitego.domain.threads.UIThreadExecutor;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class DomainModule {
  @Provides GitHubAPI provideGitHubAPI() {
    return new GitHubAPI(BuildConfig.GITHUB_API_CLIENTID, BuildConfig.GITHUB_API_CLIENTSECRET,
        BuildConfig.GITHUB_API_SCOPES);
  }

  @Provides InteractorExecutor provideInteractorExecutor() {
    return new InteractorExecutor();
  }

  @Provides UIThreadExecutor provideUIThreadExecutor() {
    return new UIThreadExecutor();
  }

  @Provides GetAccessTokenInteractor provideGetAccessTokenInteractor(
      InteractorExecutor interactorExecutor, GitHubAPI gitHubAPI, Network network) {
    return new GetAccessTokenInteractor(interactorExecutor, gitHubAPI, network);
  }

  @Provides GetAuthUserInteractor provideGetAuthUserInteractor(
      InteractorExecutor interactorExecutor,
      @Named("authUserRepository") Repository authUserRepository) {
    return new GetAuthUserInteractor(interactorExecutor, authUserRepository);
  }

  @Provides GetAuthRepoInteractor provideGetAuthRepoInteractor(
      InteractorExecutor interactorExecutor,
      @Named("authRepoRepository") Repository authRepoRepository) {
    return new GetAuthRepoInteractor(interactorExecutor, authRepoRepository);
  }
}
