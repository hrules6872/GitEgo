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
import com.hrules.gitego.domain.models.GitHubAuthUserDto;
import com.hrules.imclean.domain.models.transformers.Transformer;

public final class GitHubAuthUserBddToGitHubAuthUserDto implements Transformer<Cursor, GitHubAuthUserDto> {
  @NonNull @Override public GitHubAuthUserDto transform(@NonNull Cursor from) {
    return GitHubAuthUserDto.builder().date(from.getString(from.getColumnIndex(DatabaseConstants.KEY_USER_DATE))).
        user(from.getString(from.getColumnIndex(DatabaseConstants.KEY_USER_LOGIN))).
        avatarUrl(from.getString(from.getColumnIndex(DatabaseConstants.KEY_USER_AVATAR_URL))).
        htmlUrl(from.getString(from.getColumnIndex(DatabaseConstants.KEY_USER_HTML_URL))).
        type(from.getString(from.getColumnIndex(DatabaseConstants.KEY_USER_TYPE))).
        name(from.getString(from.getColumnIndex(DatabaseConstants.KEY_USER_NAME))).
        followers(from.getInt(from.getColumnIndex(DatabaseConstants.KEY_USER_FOLLOWERS))).build();
  }
}
