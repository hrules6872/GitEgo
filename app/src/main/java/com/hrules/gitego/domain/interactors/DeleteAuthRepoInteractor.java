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
import com.hrules.gitego.domain.interactors.base.BaseInteractor;
import com.hrules.gitego.domain.interactors.contracts.DeleteAuthRepo;
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.gitego.domain.models.mappers.GitHubAuthRepoDtoToGitHubAuthRepo;
import com.hrules.gitego.domain.models.mappers.base.ListMapper;
import com.hrules.gitego.domain.specifications.DeleteAuthRepoSpecification;
import com.hrules.gitego.domain.specifications.params.DeleteAuthRepoSpecificationParams;
import com.hrules.gitego.domain.threads.base.InteractorExecutorInterface;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import java.util.List;

public class DeleteAuthRepoInteractor extends BaseInteractor implements DeleteAuthRepo {
  private final Repository repository;

  private List<GitHubAuthRepo> list;
  private Callback callback;

  public DeleteAuthRepoInteractor(@NonNull InteractorExecutorInterface interactorExecutor,
      @NonNull Repository repository) {
    super(interactorExecutor);
    this.repository = repository;
  }

  @Override public void execute(@NonNull List<GitHubAuthRepo> list, @NonNull Callback callback) {
    this.list = list;
    this.callback = callback;

    getInteractorExecutor().execute(this);
  }

  @Override public void run() {
    List<GitHubAuthRepoDto> listDto = new ListMapper<>(new GitHubAuthRepoDtoToGitHubAuthRepo()).inverseMap(list);

    DeleteAuthRepoSpecification deleteAuthRepoSpecification = new DeleteAuthRepoSpecification();
    deleteAuthRepoSpecification.setParams(new DeleteAuthRepoSpecificationParams(listDto));

    try {
      repository.remove(deleteAuthRepoSpecification);
    } catch (Exception e) {
      notifyFail(e);
    }
  }

  private void notifyFail(@NonNull Exception exception) {
    if (callback != null) {
      callback.onFailure(exception);
    }
  }
}
