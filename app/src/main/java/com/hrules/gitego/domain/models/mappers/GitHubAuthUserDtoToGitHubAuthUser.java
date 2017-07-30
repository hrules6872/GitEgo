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

package com.hrules.gitego.domain.models.mappers;

import android.support.annotation.NonNull;
import com.hrules.gitego.domain.models.GitHubAuthUserDto;
import com.hrules.gitego.domain.models.mappers.base.InverseMapper;
import com.hrules.gitego.presentation.models.GitHubAuthUser;

public final class GitHubAuthUserDtoToGitHubAuthUser implements InverseMapper<GitHubAuthUserDto, GitHubAuthUser> {
  @NonNull @Override public GitHubAuthUser map(@NonNull GitHubAuthUserDto from) {
    return GitHubAuthUser.builder().
        date(from.getDate()).
        user(from.getUser()).
        avatarUrl(from.getAvatarUrl()).
        htmlUrl(from.getHtmlUrl()).
        type(from.getType()).
        name(from.getName()).
        followers(from.getFollowers()).build();
  }

  @NonNull @Override public GitHubAuthUserDto inverseMap(@NonNull GitHubAuthUser from) {
    return GitHubAuthUserDto.builder().
        date(from.getDate()).
        user(from.getUser()).
        avatarUrl(from.getAvatarUrl()).
        htmlUrl(from.getHtmlUrl()).
        type(from.getType()).
        name(from.getName()).
        followers(from.getFollowers()).build();
  }
}
