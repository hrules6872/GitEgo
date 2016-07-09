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

package com.hrules.gitego.domain.specifications.base;

import com.hrules.gitego.data.repository.datasources.DataSource;

public class SpecificationFactory<T> {
  @SuppressWarnings("unchecked")
  public Specification<T> get(DataSource dataSource, Specification<T> specification) {
    try {
      Class<?> clazz = Class.forName(dataSource.getSpecificationsPath()
          + "."
          + dataSource.getSpecificationsPrefix()
          + specification.getClass().getSimpleName());
      Object result = clazz.newInstance();
      ((Specification) result).setAdditionalParams(specification.getAdditionalParams());
      return (Specification<T>) result;
    } catch (Exception e) {
      return specification;
    }
  }
}
