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

package com.hrules.gitego.domain.specifications.base;

import android.support.annotation.NonNull;
import com.hrules.gitego.data.repository.datasources.api.specifications.AuthRepoAPIGetAuthRepoSpecification;
import com.hrules.gitego.data.repository.datasources.api.specifications.AuthRepoSubscribersAPIGetAuthRepoSubscribersSpecification;
import com.hrules.gitego.data.repository.datasources.api.specifications.AuthUserAPIGetAuthUserSpecification;
import com.hrules.gitego.data.repository.datasources.base.DataSource;
import com.hrules.gitego.data.repository.datasources.bdd.specifications.AuthRepoBddDeleteAuthRepoSpecification;
import com.hrules.gitego.data.repository.datasources.bdd.specifications.AuthRepoBddGetAuthRepoSpecification;
import com.hrules.gitego.data.repository.datasources.bdd.specifications.AuthUserBddGetAuthUserSpecification;

public final class SpecificationFactory<T> {
  private final Class[] clazzes = {
      AuthRepoAPIGetAuthRepoSpecification.class, AuthRepoSubscribersAPIGetAuthRepoSubscribersSpecification.class,
      AuthUserAPIGetAuthUserSpecification.class, AuthRepoBddDeleteAuthRepoSpecification.class,
      AuthRepoBddGetAuthRepoSpecification.class, AuthUserBddGetAuthUserSpecification.class
  };

  @SuppressWarnings({ "unchecked", "TryWithIdenticalCatches" }) public @NonNull Specification<T> create(
      @NonNull DataSource dataSource, @NonNull Specification<T> specification) {
    for (Class clazz : clazzes) {
      if (clazz.getSimpleName().contains(specification.getClass().getSimpleName())) {
        try {
          Specification candidate = (Specification) clazz.newInstance();
          if (candidate.getParent() == dataSource.getClass()) {
            candidate.setParams(specification.getParams());
            return candidate;
          }
        } catch (InstantiationException ignored) {
        } catch (IllegalAccessException ignored) {
        }
      }
    }
    throw new IllegalArgumentException("Specification not found");
  }
}
