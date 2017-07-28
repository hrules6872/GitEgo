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

package com.hrules.gitego.domain.interactors;

import android.support.annotation.NonNull;
import com.hrules.gitego.data.repository.base.Repository;
import com.hrules.gitego.data.repository.base.RepositoryInterface;
import com.hrules.gitego.domain.interactors.base.BaseInteractor;
import com.hrules.gitego.domain.interactors.contracts.GetAuthRepo;
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.gitego.domain.models.mappers.GitHubAuthRepoDtoToGitHubAuthRepo;
import com.hrules.gitego.domain.models.mappers.base.ListMapper;
import com.hrules.gitego.domain.specifications.GetAuthRepoSpecification;
import com.hrules.gitego.domain.specifications.params.GetAuthRepoSpecificationParams;
import com.hrules.gitego.domain.threads.base.InteractorExecutorInterface;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import java.util.List;

public final class GetAuthRepoInteractor extends BaseInteractor implements GetAuthRepo {
  private final Repository repository;

  private String access_token;
  private Callback callback;

  public GetAuthRepoInteractor(@NonNull InteractorExecutorInterface interactorExecutor, @NonNull Repository repository) {
    super(interactorExecutor);
    this.repository = repository;
  }

  @Override public void execute(@NonNull String access_token, @NonNull Callback callback) {
    this.access_token = access_token;
    this.callback = callback;

    getInteractorExecutor().execute(this);
  }

  @Override public void run() {
    GetAuthRepoSpecification getAuthRepoSpecification = new GetAuthRepoSpecification();
    getAuthRepoSpecification.setParams(new GetAuthRepoSpecificationParams(access_token));

    repository.query(getAuthRepoSpecification, new RepositoryInterface.QueryCallback<GitHubAuthRepoDto>() {
      @Override public void onSuccess(@NonNull List<GitHubAuthRepoDto> response) {
        List<GitHubAuthRepo> list = new ListMapper<>(new GitHubAuthRepoDtoToGitHubAuthRepo()).map(response);
        notifySuccess(list);
      }

      @Override public void onFailure(@NonNull Exception exception) {
        notifyFail(exception);
      }

      @Override public void onFinish() {
        notifyFinish();
      }
    });
  }

  private void notifyFinish() {
    if (callback != null) {
      callback.onFinish();
    }
  }

  private void notifySuccess(@NonNull List<GitHubAuthRepo> list) {
    if (callback != null) {
      callback.onSuccess(list);
    }
  }

  private void notifyFail(@NonNull Exception exception) {
    if (callback != null) {
      callback.onFailure(exception);
    }
  }
}
