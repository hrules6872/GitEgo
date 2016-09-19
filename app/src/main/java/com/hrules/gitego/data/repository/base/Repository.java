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

package com.hrules.gitego.data.repository.base;

import android.support.annotation.NonNull;
import com.hrules.gitego.commons.DebugLog;
import com.hrules.gitego.data.exceptions.NetworkIOException;
import com.hrules.gitego.data.exceptions.NotCacheExpiredException;
import com.hrules.gitego.data.repository.datasources.DataSource;
import com.hrules.gitego.domain.models.base.ModelDto;
import com.hrules.gitego.domain.specifications.base.Specification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Repository<T extends ModelDto> implements RepositoryInterface<T> {
  private final Collection<DataSource<T>> dataSources;

  protected Repository(@NonNull Collection<DataSource<T>> dataSources) {
    this.dataSources = dataSources;
  }

  @SuppressWarnings("unchecked") @Override public void addOrUpdate(@NonNull T item) throws Exception {
    for (DataSource dataSource : dataSources) {
      if (dataSource.isWriteable()) {
        dataSource.addOrUpdate(item);
      }
    }
  }

  @SuppressWarnings("unchecked") @Override public void addOrUpdate(@NonNull Iterable<T> items) throws Exception {
    for (DataSource dataSource : dataSources) {
      if (dataSource.isWriteable()) {
        dataSource.addOrUpdate(items);
      }
    }
  }

  @SuppressWarnings("unchecked") @Override public void remove(@NonNull T item) throws Exception {
    for (DataSource dataSource : dataSources) {
      if (dataSource.isWriteable()) {
        dataSource.remove(item);
      }
    }
  }

  @Override public void remove(@NonNull Specification specification) throws Exception {
    for (DataSource dataSource : dataSources) {
      if (dataSource.isWriteable()) {
        dataSource.remove(specification);
      }
    }
  }

  @SuppressWarnings("unchecked") @Override public void remove(@NonNull Iterable<T> items) throws Exception {
    for (DataSource dataSource : dataSources) {
      if (dataSource.isWriteable()) {
        dataSource.remove(items);
      }
    }
  }

  @SuppressWarnings("unchecked") @Override public void query(@NonNull Specification specification, @NonNull QueryCallback callback) {
    Map<Object, T> map = new HashMap<>();

    for (DataSource dataSource : dataSources) {
      if (dataSource.isReadable()) {
        try {
          List<T> list;
          if (dataSource.isCacheExpired()) {
            list = (List<T>) dataSource.query(specification);
          } else {
            throw new NotCacheExpiredException(dataSource.toString());
          }

          for (ModelDto item : list) {
            map.put(item.getModelId(), (T) item);
          }
          callback.onSuccess(new ArrayList<>(map.values()));
        } catch (Exception e) {
          callback.onFailure(e);
        }
      }
    }
    try {
      addOrUpdate(new ArrayList<>(map.values()));
    } catch (NetworkIOException e) {
      // schedule for later?
    } catch (Exception e) {
      DebugLog.e(e.getMessage(), e);
    }
    callback.onFinish();
  }
}
