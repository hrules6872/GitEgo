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

package com.hrules.gitego.domain.interactors;

import android.support.annotation.NonNull;
import com.hrules.gitego.data.repository.base.Repository;
import com.hrules.gitego.data.repository.base.RepositoryInterface;
import com.hrules.gitego.domain.errors.base.Error;
import com.hrules.gitego.domain.errors.base.ErrorFactory;
import com.hrules.gitego.domain.interactors.base.BaseInteractor;
import com.hrules.gitego.domain.interactors.contracts.GetAuthUser;
import com.hrules.gitego.domain.models.GitHubAuthUserDto;
import com.hrules.gitego.domain.models.mappers.GitHubAuthUserDtoToGitHubAuthUser;
import com.hrules.gitego.domain.models.mappers.base.ListMapper;
import com.hrules.gitego.domain.specifications.GetAuthUserSpecification;
import com.hrules.gitego.domain.specifications.params.GetAuthUserSpecificationParams;
import com.hrules.gitego.domain.threads.base.InteractorExecutorInterface;
import com.hrules.gitego.presentation.models.GitHubAuthUser;
import java.util.List;

public final class GetAuthUserInteractor extends BaseInteractor implements GetAuthUser {
  private final Repository repository;

  private String access_token;
  private Callback callback;

  public GetAuthUserInteractor(@NonNull InteractorExecutorInterface interactorExecutor, @NonNull Repository repository) {
    super(interactorExecutor);
    this.repository = repository;
  }

  @Override public void execute(@NonNull String access_token, @NonNull Callback callback) {
    this.access_token = access_token;
    this.callback = callback;

    getInteractorExecutor().execute(this);
  }

  @Override public void run() {
    GetAuthUserSpecification getAuthUserSpecification = new GetAuthUserSpecification();
    getAuthUserSpecification.setParams(new GetAuthUserSpecificationParams(access_token));

    repository.query(getAuthUserSpecification, new RepositoryInterface.QueryCallback<GitHubAuthUserDto>() {
      @Override public void onSuccess(@NonNull List<GitHubAuthUserDto> response) {
        List<GitHubAuthUser> gitHubAuthUsers = new ListMapper<>(new GitHubAuthUserDtoToGitHubAuthUser()).map(response);
        notifySuccess(gitHubAuthUsers);
      }

      @Override public void onFailure(@NonNull Exception exception) {
        notifyFail(ErrorFactory.create(exception));
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

  private void notifySuccess(@NonNull List<GitHubAuthUser> gitHubAuthUsers) {
    if (callback != null) {
      callback.onSuccess(gitHubAuthUsers);
    }
  }

  private void notifyFail(@NonNull Error error) {
    if (callback != null) {
      callback.onFailure(error);
    }
  }
}
