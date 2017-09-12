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

package com.hrules.gitego.data.repository;

import android.support.annotation.NonNull;
import com.hrules.gitego.domain.models.GitHubAuthUserDto;
import com.hrules.imclean.data.repository.Repository;
import com.hrules.imclean.data.repository.datasources.DataSource;
import com.hrules.imclean.data.repository.datasources.DataSourceReadable;
import com.hrules.imclean.data.repository.datasources.DataSourceWriteable;
import java.util.Arrays;

public final class AuthUserRepository extends Repository<GitHubAuthUserDto> {
  @SuppressWarnings("unchecked")
  public AuthUserRepository(@NonNull DataSourceReadable[] dataSourcesReadables, @NonNull DataSourceWriteable[] dataSourcesWriteables) {
    super(Arrays.<DataSource<GitHubAuthUserDto>>asList(dataSourcesReadables),
        Arrays.<DataSource<GitHubAuthUserDto>>asList(dataSourcesWriteables));
  }
}
