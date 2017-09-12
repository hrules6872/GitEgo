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

package com.hrules.gitego.di.modules;

import com.hrules.gitego.App;
import com.hrules.gitego.data.network.Network;
import com.hrules.gitego.data.persistence.database.Database;
import com.hrules.gitego.data.repository.AuthRepoRepository;
import com.hrules.gitego.data.repository.AuthUserRepository;
import com.hrules.gitego.data.repository.cache.AuthRepoAPIBasicCache;
import com.hrules.gitego.data.repository.cache.AuthRepoBddBasicCache;
import com.hrules.gitego.data.repository.cache.AuthUserAPIBasicCache;
import com.hrules.gitego.data.repository.cache.AuthUserBddBasicCache;
import com.hrules.gitego.data.repository.datasources.api.AuthRepoAPIDataSourceReadable;
import com.hrules.gitego.data.repository.datasources.api.AuthUserAPIDataSourceReadable;
import com.hrules.gitego.data.repository.datasources.bdd.AuthRepoBddDataSourceReadable;
import com.hrules.gitego.data.repository.datasources.bdd.AuthRepoBddDataSourceWriteable;
import com.hrules.gitego.data.repository.datasources.bdd.AuthUserBddDataSourceReadable;
import com.hrules.gitego.data.repository.datasources.bdd.AuthUserBddDataSourceWriteable;
import com.hrules.imclean.data.repository.Repository;
import com.hrules.imclean.data.repository.cache.BasicCache;
import com.hrules.imclean.data.repository.datasources.DataSourceReadable;
import com.hrules.imclean.data.repository.datasources.DataSourceWriteable;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

@Module public final class DataModule {
  @Provides @Named("authUserRepository") Repository providesAuthUserRepository(
      @Named("authUserBddDataSourceReadable") DataSourceReadable authUserBddDataSourceReadable,
      @Named("authUserAPIDataSourceReadable") DataSourceReadable authUserAPIDataSourceReadable,
      @Named("authUserBddDataSourceWriteable") DataSourceWriteable authUserBddDataSourceWriteable) {
    // readables: first local then remote, writeables: first remote then local
    return new AuthUserRepository(new DataSourceReadable[] { authUserBddDataSourceReadable, authUserAPIDataSourceReadable },
        new DataSourceWriteable[] { authUserBddDataSourceWriteable });
  }

  @Provides @Named("authUserBddDataSourceReadable") DataSourceReadable providesAuthUserBddDataSourceReadable(Database database,
      @Named("authUserBddBasicCache") BasicCache cache) {
    return new AuthUserBddDataSourceReadable(database, cache);
  }

  @Provides @Named("authUserBddDataSourceWriteable") DataSourceWriteable providesAuthUserBddDataSourceWriteable(Database database) {
    return new AuthUserBddDataSourceWriteable(database);
  }

  @Provides @Named("authUserAPIDataSourceReadable") DataSourceReadable providesAuthUserAPIDataSourceReadable(Network network,
      @Named("authUserAPIBasicCache") BasicCache cache) {
    return new AuthUserAPIDataSourceReadable(network, cache);
  }

  @Provides @Named("authRepoRepository") Repository providesAuthRepoRepository(
      @Named("authRepoBddDataSourceReadable") DataSourceReadable authRepoBddDataSourceReadable,
      @Named("authRepoAPIDataSourceReadable") DataSourceReadable authRepoAPIDataSourceReadable,
      @Named("authRepoBddDataSourceWriteable") DataSourceWriteable authRepoBddDataSourceWriteable) {
    // readables: first local then remote, writeables: first remote then local
    return new AuthRepoRepository(new DataSourceReadable[] { authRepoBddDataSourceReadable, authRepoAPIDataSourceReadable },
        new DataSourceWriteable[] { authRepoBddDataSourceWriteable });
  }

  @Provides @Named("authRepoBddDataSourceReadable") DataSourceReadable providesAuthRepoBddDataSourceReadable(Database database,
      @Named("authRepoBddBasicCache") BasicCache cache) {
    return new AuthRepoBddDataSourceReadable(database, cache);
  }

  @Provides @Named("authRepoBddDataSourceWriteable") DataSourceWriteable providesAuthRepoBddDataSourceWriteable(Database database) {
    return new AuthRepoBddDataSourceWriteable(database);
  }

  @Provides @Named("authRepoAPIDataSourceReadable") DataSourceReadable providesAuthRepoAPIDataSourceReadable(Network network,
      @Named("authRepoAPIBasicCache") BasicCache cache) {
    return new AuthRepoAPIDataSourceReadable(network, cache);
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
