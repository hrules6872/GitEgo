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
  @Override public GitHubAuthUser map(@NonNull GitHubAuthUserDto from) {
    GitHubAuthUser gitHubAuthUser = new GitHubAuthUser();
    gitHubAuthUser.setModelId(from.getModelId());
    gitHubAuthUser.setDate(from.getDate());
    gitHubAuthUser.setLogin(from.getLogin());
    gitHubAuthUser.setAvatar_url(from.getAvatar_url());
    gitHubAuthUser.setHtml_url(from.getHtml_url());
    gitHubAuthUser.setType(from.getType());
    gitHubAuthUser.setName(from.getName());
    gitHubAuthUser.setFollowers(from.getFollowers());
    return gitHubAuthUser;
  }

  @Override public GitHubAuthUserDto inverseMap(@NonNull GitHubAuthUser from) {
    GitHubAuthUserDto gitHubAuthUserDto = new GitHubAuthUserDto();
    gitHubAuthUserDto.setModelId(from.getModelId());
    gitHubAuthUserDto.setDate(from.getDate());
    gitHubAuthUserDto.setLogin(from.getLogin());
    gitHubAuthUserDto.setLogin(from.getLogin());
    gitHubAuthUserDto.setAvatar_url(from.getAvatar_url());
    gitHubAuthUserDto.setHtml_url(from.getHtml_url());
    gitHubAuthUserDto.setType(from.getType());
    gitHubAuthUserDto.setName(from.getName());
    gitHubAuthUserDto.setFollowers(from.getFollowers());
    return gitHubAuthUserDto;
  }
}
