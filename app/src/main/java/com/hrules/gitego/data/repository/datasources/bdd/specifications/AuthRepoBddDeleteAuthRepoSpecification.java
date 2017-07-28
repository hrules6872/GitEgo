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

package com.hrules.gitego.data.repository.datasources.bdd.specifications;

import android.support.annotation.NonNull;
import com.hrules.gitego.data.persistence.database.DatabaseConstants;
import com.hrules.gitego.data.persistence.database.utils.SQLQueryBuilder;
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.gitego.domain.specifications.DeleteAuthRepoSpecification;
import com.hrules.gitego.domain.specifications.params.DeleteAuthRepoSpecificationParams;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused") public final class AuthRepoBddDeleteAuthRepoSpecification extends DeleteAuthRepoSpecification<String[]> {
  @Override public String[] get() {
    DeleteAuthRepoSpecificationParams params = (DeleteAuthRepoSpecificationParams) getParams();

    List<String> sqlList = new ArrayList<>();
    for (GitHubAuthRepoDto item : params.getList()) {
      sqlList.add(convertDtoToSqlDelete(item));
    }
    return sqlList.toArray(new String[sqlList.size()]);
  }

  private String convertDtoToSqlDelete(@NonNull GitHubAuthRepoDto item) {
    return new SQLQueryBuilder().deleteFrom(DatabaseConstants.TABLE_REPO)
        .where(DatabaseConstants.KEY_REPO_ID)
        .equalsTo(item.getId())
        .build();
  }
}
