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
import com.hrules.gitego.data.network.Network;
import com.hrules.gitego.data.persistence.database.Database;
import com.hrules.gitego.data.repository.AuthUserRepository;
import com.hrules.gitego.data.repository.base.Repository;
import com.hrules.gitego.data.repository.cache.AuthRepoAPIBasicCache;
import com.hrules.gitego.data.repository.cache.AuthRepoBddBasicCache;
import com.hrules.gitego.data.repository.cache.AuthUserAPIBasicCache;
import com.hrules.gitego.data.repository.cache.AuthUserBddBasicCache;
import com.hrules.gitego.data.repository.cache.base.BasicCache;
import com.hrules.gitego.data.repository.datasources.DataSource;
import com.hrules.gitego.data.repository.datasources.api.AuthRepoAPIDataSource;
import com.hrules.gitego.data.repository.datasources.api.AuthUserAPIDataSource;
import com.hrules.gitego.data.repository.datasources.bdd.AuthRepoBddDataSource;
import com.hrules.gitego.data.repository.datasources.bdd.AuthUserBddDataSource;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public class DataModule {
  @Provides @Named("authUserRepository") Repository providesAuthUserRepository(
      @Named("authUserBddDataSource") DataSource authUserBddDataSource, @Named("authUserAPIDataSource") DataSource authUserAPIDataSource) {
    return new AuthUserRepository(authUserBddDataSource, authUserAPIDataSource);
  }

  @Provides @Named("authUserBddDataSource") DataSource providesAuthUserBddDataSource(Database database,
      @Named("authUserBddBasicCache") BasicCache cache) {
    return new AuthUserBddDataSource(database, cache);
  }

  @Provides @Named("authUserAPIDataSource") DataSource providesAuthUserAPIDataSource(Network network,
      @Named("authUserAPIBasicCache") BasicCache cache) {
    return new AuthUserAPIDataSource(network, cache);
  }

  @Provides @Named("authRepoRepository") Repository providesAuthRepoRepository(
      @Named("authRepoBddDataSource") DataSource authRepoBddDataSource, @Named("authRepoAPIDataSource") DataSource authRepoAPIDataSource) {
    return new AuthUserRepository(authRepoBddDataSource, authRepoAPIDataSource);
  }

  @Provides @Named("authRepoBddDataSource") DataSource providesAuthRepoBddDataSource(Database database,
      @Named("authRepoBddBasicCache") BasicCache cache) {
    return new AuthRepoBddDataSource(database, cache);
  }

  @Provides @Named("authRepoAPIDataSource") DataSource providesAuthRepoAPIDataSource(Network network,
      @Named("authRepoAPIBasicCache") BasicCache cache) {
    return new AuthRepoAPIDataSource(network, cache);
  }

  @Provides Network providesNetwork() {
    return new Network();
  }

  @Provides Database providesDatabase(App application) {
    return Database.with(application);
  }

  @Provides @Named("authUserBddBasicCache") BasicCache providesAuthUserBddBasicCache() {
    return new AuthUserBddBasicCache();
  }

  @Provides @Named("authUserAPIBasicCache") BasicCache providesAuthUserAPIBasicCache() {
    return new AuthUserAPIBasicCache();
  }

  @Provides @Named("authRepoBddBasicCache") BasicCache providesAuthRepoBddBasicCache() {
    return new AuthRepoBddBasicCache();
  }

  @Provides @Named("authRepoAPIBasicCache") BasicCache providesAuthRepoAPIBasicCache() {
    return new AuthRepoAPIBasicCache();
  }
}
