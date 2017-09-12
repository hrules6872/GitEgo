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
import com.hrules.gitego.data.repository.datasources.api.specifications.AuthRepoSubscribersAPIGetAuthRepoSubscribersSpecification;
import com.hrules.gitego.data.repository.datasources.specifications.Specifications;
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.gitego.domain.models.serializers.GitHubAuthRepoDtoSerializer;
import com.hrules.gitego.domain.specifications.params.GetAuthRepoSpecificationParams;
import com.hrules.gitego.domain.specifications.params.GetAuthRepoSubscribersSpecificationParams;
import com.hrules.imclean.data.repository.cache.BasicCache;
import com.hrules.imclean.data.repository.datasources.DataSourceReadable;
import com.hrules.imclean.domain.specifications.Specification;
import com.hrules.imclean.domain.specifications.SpecificationFactory;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.json.JSONArray;

public final class AuthRepoAPIDataSourceReadable extends DataSourceReadable<GitHubAuthRepoDto> {
  private final Network network;
  private final BasicCache cache;

  public AuthRepoAPIDataSourceReadable(@NonNull Network network, @NonNull BasicCache cache) {
    this.network = network;
    this.cache = cache;
  }

  @SuppressWarnings("unchecked") @Override public Collection<GitHubAuthRepoDto> query(@NonNull Specification specification)
      throws Exception {
    specification = new SpecificationFactory<String>().create(this, specification, Specifications.get());
    String response = network.get((RequestNetwork) specification.get());
    List<GitHubAuthRepoDto> list = (List<GitHubAuthRepoDto>) new GitHubAuthRepoDtoSerializer().deserialize(response);

    for (ListIterator<GitHubAuthRepoDto> iterator = list.listIterator(); iterator.hasNext(); ) {
      GitHubAuthRepoDto item = iterator.next();

      GetAuthRepoSpecificationParams repoSpecificationParams = (GetAuthRepoSpecificationParams) specification.getParams();
      AuthRepoSubscribersAPIGetAuthRepoSubscribersSpecification subscribersSpecification =
          new AuthRepoSubscribersAPIGetAuthRepoSubscribersSpecification();
      subscribersSpecification.setParams(
          new GetAuthRepoSubscribersSpecificationParams(repoSpecificationParams.getAccess_token(), item.getSubscribersUrl()));
      String subscribers = network.get(subscribersSpecification.get());
      JSONArray subscribersJSONArray = new JSONArray(subscribers);

      iterator.set(
          item.withDateAndWatchersCount(DatabaseDateUtils.formatDateToSQLShort(System.currentTimeMillis()), subscribersJSONArray.length()));
    }
    cache.persist();
    return list;
  }

  @Override public boolean isCacheExpired() {
    return cache.isExpired();
  }
}
