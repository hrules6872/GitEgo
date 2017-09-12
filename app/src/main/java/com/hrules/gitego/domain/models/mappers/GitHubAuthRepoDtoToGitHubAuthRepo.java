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
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import com.hrules.imclean.domain.models.mappers.InverseMapper;

public final class GitHubAuthRepoDtoToGitHubAuthRepo implements InverseMapper<GitHubAuthRepoDto, GitHubAuthRepo> {
  @NonNull @Override public GitHubAuthRepo map(@NonNull GitHubAuthRepoDto from) {
    return GitHubAuthRepo.builder()
        .date(from.getDate())
        .id(from.getId())
        .name(from.getName())
        .privateRepository(from.isPrivateRepository())
        .htmlUrl(from.getHtmlUrl())
        .fork(from.isFork())
        .homepage(from.getHomepage())
        .stargazersCount(from.getStargazersCount())
        .watchersCount(from.getWatchersCount())
        .forksCount(from.getForksCount())
        .language(from.getLanguage())
        .build();
  }

  @NonNull @Override public GitHubAuthRepoDto inverseMap(@NonNull GitHubAuthRepo from) {
    return GitHubAuthRepoDto.builder()
        .date(from.getDate())
        .id(from.getId())
        .name(from.getName())
        .privateRepository(from.isPrivateRepository())
        .htmlUrl(from.getHtmlUrl())
        .fork(from.isFork())
        .homepage(from.getHomepage())
        .stargazersCount(from.getStargazersCount())
        .watchersCount(from.getWatchersCount())
        .forksCount(from.getForksCount())
        .language(from.getLanguage())
        .build();
  }
}
