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

package com.hrules.gitego.di.components;

import com.hrules.gitego.data.network.Network;
import com.hrules.gitego.data.persistence.database.Database;
import com.hrules.gitego.data.repository.base.Repository;
import com.hrules.gitego.data.repository.cache.base.BasicCache;
import com.hrules.gitego.data.repository.datasources.DataSource;
import com.hrules.gitego.di.modules.DataModule;
import dagger.Component;
import javax.inject.Named;

@Component(dependencies = AppComponent.class, modules = DataModule.class) interface DataComponent {
  @Named("authUserRepository") Repository authUserRepository();

  @Named("authUserBddDataSource") DataSource authUserBddDataSource();

  @Named("authUserAPIDataSource") DataSource authUserAPIDataSource();

  @Named("authRepoRepository") Repository authRepoRepository();

  @Named("authRepoBddDataSource") DataSource authRepoBddDataSource();

  @Named("authRepoAPIDataSource") DataSource authRepoAPIDataSource();

  Network network();

  Database database();

  @Named("authUserBddBasicCache") BasicCache authUserBddBasicCache();

  @Named("authUserAPIBasicCache") BasicCache authUserAPIBasicCache();

  @Named("authRepoBddBasicCache") BasicCache authRepoBddBasicCache();

  @Named("authRepoAPIBasicCache") BasicCache authRepoAPIBasicCache();
}
