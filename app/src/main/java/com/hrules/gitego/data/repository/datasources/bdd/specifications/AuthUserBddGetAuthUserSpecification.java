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

import com.hrules.gitego.data.persistence.database.DatabaseConstants;
import com.hrules.gitego.data.persistence.database.utils.SQLQueryBuilder;
import com.hrules.gitego.domain.specifications.GetAuthUserSpecification;

@SuppressWarnings("unused") public final class AuthUserBddGetAuthUserSpecification extends GetAuthUserSpecification<String> {
  private static final int DAYS_LIMIT = 2;

  @Override public String get() {
    return new SQLQueryBuilder().select()
        .from(DatabaseConstants.TABLE_USER)
        .orderByDescending(DatabaseConstants.KEY_USER_DATE)
        .limit(DAYS_LIMIT)
        .build();
  }
}
