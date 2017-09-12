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

package com.hrules.gitego.data.repository.datasources.api;

import android.support.annotation.NonNull;
import com.hrules.gitego.data.network.Network;
import com.hrules.gitego.data.network.RequestNetwork;
import com.hrules.gitego.data.persistence.database.utils.DatabaseDateUtils;
import com.hrules.gitego.data.repository.datasources.specifications.Specifications;
import com.hrules.gitego.domain.models.GitHubAuthUserDto;
import com.hrules.gitego.domain.models.serializers.GitHubAuthUserDtoSerializer;
import com.hrules.imclean.data.repository.cache.BasicCache;
import com.hrules.imclean.data.repository.datasources.DataSourceReadable;
import com.hrules.imclean.domain.specifications.Specification;
import com.hrules.imclean.domain.specifications.SpecificationFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AuthUserAPIDataSourceReadable extends DataSourceReadable<GitHubAuthUserDto> {
  private final Network network;
  private final BasicCache cache;

  public AuthUserAPIDataSourceReadable(@NonNull Network network, @NonNull BasicCache cache) {
    this.network = network;
    this.cache = cache;
  }

  @SuppressWarnings("unchecked") @Override public Collection<GitHubAuthUserDto> query(@NonNull Specification specification)
      throws Exception {
    specification = new SpecificationFactory<String>().create(this, specification, Specifications.get());
    List<GitHubAuthUserDto> list = new ArrayList<>();

    String response = network.get((RequestNetwork) specification.get());
    GitHubAuthUserDto gitHubAuthUserDto = new GitHubAuthUserDtoSerializer().deserialize(response);
    if (gitHubAuthUserDto != null) {
      list.add(gitHubAuthUserDto.withDate(DatabaseDateUtils.formatDateToSQLShort(System.currentTimeMillis())));
      cache.persist();
    }
    return list;
  }

  @Override public boolean isCacheExpired() {
    return cache.isExpired();
  }
}
