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

package com.hrules.gitego.data.repository.datasources.bdd;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.hrules.gitego.data.exceptions.LocalIOException;
import com.hrules.gitego.data.persistence.database.Database;
import com.hrules.gitego.data.repository.cache.base.BasicCache;
import com.hrules.gitego.data.repository.datasources.base.DataSourceReadable;
import com.hrules.gitego.data.repository.datasources.specifications.Specifications;
import com.hrules.gitego.domain.models.GitHubAuthUserDto;
import com.hrules.gitego.domain.models.transformers.GitHubAuthUserBddToGitHubAuthUserDto;
import com.hrules.gitego.domain.specifications.base.Specification;
import com.hrules.gitego.domain.specifications.base.SpecificationFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AuthUserBddDataSourceReadable extends DataSourceReadable<GitHubAuthUserDto> {
  private final Database database;
  private final BasicCache cache;

  public AuthUserBddDataSourceReadable(@NonNull Database database, @NonNull BasicCache cache) {
    this.database = database;
    this.cache = cache;
  }

  @SuppressWarnings("unchecked") @Override public Collection<GitHubAuthUserDto> query(@NonNull Specification specification)
      throws Exception {
    specification = new SpecificationFactory<String>().create(this, specification, Specifications.get());
    List<GitHubAuthUserDto> list = new ArrayList<>();

    Cursor cursor = null;
    try {
      database.open();
      cursor = database.get((String) specification.get());
      while (cursor.moveToNext()) {
        list.add(new GitHubAuthUserBddToGitHubAuthUserDto().transform(cursor));
      }
    } catch (Exception e) {
      throw new LocalIOException(e.getMessage());
    } finally {
      if (cursor != null) {
        cursor.close();
      }
      database.close();
    }
    cache.persist();
    return list;
  }

  @Override public boolean isCacheExpired() {
    return cache.isExpired();
  }
}
