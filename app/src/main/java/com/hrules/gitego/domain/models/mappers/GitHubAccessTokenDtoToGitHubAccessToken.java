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

package com.hrules.gitego.domain.models.mappers;

import android.support.annotation.NonNull;
import com.hrules.gitego.domain.models.GitHubAccessTokenDto;
import com.hrules.gitego.domain.models.mappers.base.Mapper;
import com.hrules.gitego.presentation.models.GitHubAccessToken;

public class GitHubAccessTokenDtoToGitHubAccessToken
    implements Mapper<GitHubAccessTokenDto, GitHubAccessToken> {
  @Override public GitHubAccessToken map(@NonNull GitHubAccessTokenDto from) {
    GitHubAccessToken gitHubAccessToken = new GitHubAccessToken();
    gitHubAccessToken.setAccess_token(from.getAccess_token());
    gitHubAccessToken.setToken_type(from.getToken_type());
    gitHubAccessToken.setScope(from.getScope());
    return gitHubAccessToken;
  }
}
