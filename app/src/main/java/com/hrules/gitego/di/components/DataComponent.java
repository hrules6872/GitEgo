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

import com.hrules.gitego.data.network.Network;
import com.hrules.gitego.data.persistence.database.Database;
import com.hrules.gitego.di.modules.DataModule;
import com.hrules.imclean.data.repository.Repository;
import com.hrules.imclean.data.repository.cache.BasicCache;
import com.hrules.imclean.data.repository.datasources.DataSourceReadable;
import com.hrules.imclean.data.repository.datasources.DataSourceWriteable;
import dagger.Component;
import javax.inject.Named;

@Component(dependencies = AppComponent.class, modules = DataModule.class) interface DataComponent {
  @Named("authUserRepository") Repository authUserRepository();

  @Named("authUserBddDataSourceReadable") DataSourceReadable authUserBddDataSourceReadable();

  @Named("authUserBddDataSourceWriteable") DataSourceWriteable authUserBddDataSourceWriteable();

  @Named("authUserAPIDataSourceReadable") DataSourceReadable authUserAPIDataSourceReadable();

  @Named("authRepoRepository") Repository authRepoRepository();

  @Named("authRepoBddDataSourceReadable") DataSourceReadable authRepoBddDataSourceReadable();

  @Named("authRepoBddDataSourceWriteable") DataSourceWriteable authRepoBddDataSourceWriteable();

  @Named("authRepoAPIDataSourceReadable") DataSourceReadable authRepoAPIDataSourceReadable();

  Network network();

  Database database();

  @Named("authUserBddBasicCache") BasicCache authUserBddBasicCache();

  @Named("authUserAPIBasicCache") BasicCache authUserAPIBasicCache();

  @Named("authRepoBddBasicCache") BasicCache authRepoBddBasicCache();

  @Named("authRepoAPIBasicCache") BasicCache authRepoAPIBasicCache();
}
