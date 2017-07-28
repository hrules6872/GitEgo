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

package com.hrules.gitego.domain.models.transformers;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.hrules.gitego.data.persistence.database.DatabaseConstants;
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.gitego.domain.models.transformers.base.Transformer;

public final class GitHubAuthRepoBddToGitHubAuthRepoDto implements Transformer<Cursor, GitHubAuthRepoDto> {
  @Override public GitHubAuthRepoDto transform(@NonNull Cursor from) {
    GitHubAuthRepoDto to = new GitHubAuthRepoDto();
    to.setDate(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_DATE)));
    to.setId(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_ID)));
    to.setName(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_NAME)));
    to.setPrivate(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_IS_PRIVATE)) == DatabaseConstants.BOOLEAN.TRUE);
    to.setHtml_url(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_HTML_URL)));
    to.setFork(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_IS_FORK)) == DatabaseConstants.BOOLEAN.TRUE);
    to.setHomepage(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_HOMEPAGE)));
    to.setStargazers_count(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_STARGAZERS_COUNT)));
    to.setWatchers_count(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_WATCHERS_COUNT)));
    to.setForks_count(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_REPO_FORKS_COUNT)));
    to.setLanguage(from.getString(from.getColumnIndex(DatabaseConstants.KEY_REPO_LANGUAGE)));
    return to;
  }
}
