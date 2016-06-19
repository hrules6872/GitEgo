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
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.gitego.domain.models.mappers.base.InverseMapper;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;

public class GitHubAuthRepoDtoToGitHubAuthRepo
    implements InverseMapper<GitHubAuthRepoDto, GitHubAuthRepo> {
  @Override public GitHubAuthRepo map(@NonNull GitHubAuthRepoDto from) {
    GitHubAuthRepo gitHubAuthRepo = new GitHubAuthRepo();
    gitHubAuthRepo.setModelId(from.getModelId());
    gitHubAuthRepo.setDate(from.getDate());
    gitHubAuthRepo.setId(from.getId());
    gitHubAuthRepo.setName(from.getName());
    gitHubAuthRepo.setPrivate(from.isPrivate());
    gitHubAuthRepo.setHtml_url(from.getHtml_url());
    gitHubAuthRepo.setFork(from.isFork());
    gitHubAuthRepo.setHomepage(from.getHomepage());
    gitHubAuthRepo.setStargazers_count(from.getStargazers_count());
    gitHubAuthRepo.setWatchers_count(from.getWatchers_count());
    gitHubAuthRepo.setForks_count(from.getForks_count());
    gitHubAuthRepo.setLanguage(from.getLanguage());
    return gitHubAuthRepo;
  }

  @Override public GitHubAuthRepoDto inverseMap(@NonNull GitHubAuthRepo from) {
    GitHubAuthRepoDto gitHubAuthRepoDto = new GitHubAuthRepoDto();
    gitHubAuthRepoDto.setModelId(from.getModelId());
    gitHubAuthRepoDto.setDate(from.getDate());
    gitHubAuthRepoDto.setId(from.getId());
    gitHubAuthRepoDto.setName(from.getName());
    gitHubAuthRepoDto.setPrivate(from.isPrivate());
    gitHubAuthRepoDto.setHtml_url(from.getHtml_url());
    gitHubAuthRepoDto.setFork(from.isFork());
    gitHubAuthRepoDto.setHomepage(from.getHomepage());
    gitHubAuthRepoDto.setStargazers_count(from.getStargazers_count());
    gitHubAuthRepoDto.setWatchers_count(from.getWatchers_count());
    gitHubAuthRepoDto.setForks_count(from.getForks_count());
    gitHubAuthRepoDto.setLanguage(from.getLanguage());
    return gitHubAuthRepoDto;
  }
}
