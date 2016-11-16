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

package com.hrules.gitego.data.repository.datasources.base;

import android.support.annotation.NonNull;
import com.hrules.gitego.domain.models.base.ModelDto;
import com.hrules.gitego.domain.specifications.base.Specification;

public abstract class DataSourceWriteable<T extends ModelDto> extends DataSource<T> {
  public abstract void addOrUpdate(@NonNull T item) throws Exception;

  public abstract void addOrUpdate(@NonNull Iterable<T> items) throws Exception;

  public abstract void addOrUpdate(@NonNull Specification specification) throws Exception;

  public abstract void remove(@NonNull T item) throws Exception;

  public abstract void remove(@NonNull Iterable<T> items) throws Exception;

  public abstract void remove(@NonNull Specification specification) throws Exception;
}
