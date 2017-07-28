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

import android.support.annotation.NonNull;
import com.hrules.gitego.data.persistence.database.Database;
import com.hrules.gitego.data.persistence.database.DatabaseConstants;
import com.hrules.gitego.data.persistence.database.utils.SQLQueryBuilder;
import com.hrules.gitego.data.repository.datasources.base.DataSourceWriteable;
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.gitego.domain.models.transformers.GitHubAuthRepoDtoToMap;
import com.hrules.gitego.domain.specifications.base.Specification;
import com.hrules.gitego.domain.specifications.base.SpecificationFactory;
import java.util.ArrayList;
import java.util.List;

public final class AuthRepoBddDataSourceWriteable extends DataSourceWriteable<GitHubAuthRepoDto> {
  private final Database database;

  public AuthRepoBddDataSourceWriteable(@NonNull Database database) {
    this.database = database;
  }

  @Override public void addOrUpdate(@NonNull GitHubAuthRepoDto item) throws Exception {
    String sqlDelete = convertDtoToSqlDelete(item);
    String sqlInsert = convertDtoToSqlInsert(item);

    database.execList(new String[] { sqlDelete, sqlInsert });
  }

  @Override public void addOrUpdate(@NonNull Iterable<GitHubAuthRepoDto> items) throws Exception {
    List<String> sqlList = new ArrayList<>();
    for (GitHubAuthRepoDto item : items) {
      sqlList.add(convertDtoToSqlDelete(item));
      sqlList.add(convertDtoToSqlInsert(item));
    }

    database.execList(sqlList.toArray(new String[sqlList.size()]));
  }

  @Override public void addOrUpdate(@NonNull Specification specification) throws Exception {
    throw new UnsupportedOperationException();
  }

  private String convertDtoToSqlInsert(@NonNull GitHubAuthRepoDto item) {
    return new SQLQueryBuilder().insertInto(DatabaseConstants.TABLE_REPO).values(new GitHubAuthRepoDtoToMap().transform(item)).build();
  }

  private String convertDtoToSqlDelete(GitHubAuthRepoDto item) {
    return new SQLQueryBuilder().deleteFrom(DatabaseConstants.TABLE_REPO)
        .where(DatabaseConstants.KEY_REPO_DATE)
        .equalsTo(item.getDate())
        .and(DatabaseConstants.KEY_REPO_ID)
        .equalsTo(item.getId())
        .build();
  }

  @Override public void remove(@NonNull GitHubAuthRepoDto item) throws Exception {
    throw new UnsupportedOperationException();
  }

  @Override public void remove(@NonNull Iterable<GitHubAuthRepoDto> items) throws Exception {
    throw new UnsupportedOperationException();
  }

  @SuppressWarnings("unchecked") @Override public void remove(@NonNull Specification specification) throws Exception {
    specification = new SpecificationFactory<String>().get(this, specification);
    database.execList((String[]) specification.get());
  }
}
