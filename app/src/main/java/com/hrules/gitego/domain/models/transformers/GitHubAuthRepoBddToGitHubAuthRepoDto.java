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

package com.hrules.gitego.domain.models.transformers;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.hrules.gitego.data.persistence.database.DatabaseConstants;
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.imclean.domain.models.transformers.Transformer;

public final class GitHubAuthRepoBddToGitHubAuthRepoDto implements Transformer<Cursor, GitHubAuthRepoDto> {
  @NonNull @Override public GitHubAuthRepoDto transform(@NonNull Cursor from) {
    return GitHubAuthRepoDto.builder()
        .date(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_DATE)))
        .id(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_ID)))
        .name(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_NAME)))
        .privateRepository(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_IS_PRIVATE)) == DatabaseConstants.BOOLEAN.TRUE)
        .htmlUrl(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_HTML_URL)))
        .fork(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_IS_FORK)) == DatabaseConstants.BOOLEAN.TRUE)
        .homepage(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_HOMEPAGE)))
        .stargazersCount(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_STARGAZERS_COUNT)))
        .watchersCount(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_WATCHERS_COUNT)))
        .forksCount(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_FORKS_COUNT)))
        .language(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_LANGUAGE)))
        .build();
  }
}
