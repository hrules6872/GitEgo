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

import android.content.Intent;
import android.support.annotation.NonNull;
import com.hrules.gitego.data.exceptions.LocalIOException;
import com.hrules.gitego.data.exceptions.NetworkUnauthorizedException;
import com.hrules.gitego.data.network.Network;
import com.hrules.gitego.data.network.RequestNetwork;
import com.hrules.gitego.domain.api.GitHubAPI;
import com.hrules.gitego.domain.interactors.base.BaseInteractor;
import com.hrules.gitego.domain.interactors.contracts.GetAccessToken;
import com.hrules.gitego.domain.models.GitHubAccessTokenDto;
import com.hrules.gitego.domain.models.mappers.GitHubAccessTokenDtoToGitHubAccessToken;
import com.hrules.gitego.domain.models.serializers.GitHubAccessTokenDtoSerializer;
import com.hrules.gitego.domain.threads.base.InteractorExecutorInterface;
import com.hrules.gitego.presentation.models.GitHubAccessToken;
import java.util.HashMap;

public final class GetAccessTokenInteractor extends BaseInteractor implements GetAccessToken {
  private final GitHubAPI gitHubAPI;
  private final Network network;

  private Intent intent;
  private String redirectUri;
  private Callback callback;

  public GetAccessTokenInteractor(@NonNull InteractorExecutorInterface interactorExecutor, @NonNull GitHubAPI gitHubAPI,
      @NonNull Network network) {
    super(interactorExecutor);
    this.gitHubAPI = gitHubAPI;
    this.network = network;
  }

  @Override public void execute(@NonNull Intent intent, @NonNull String redirectUri, @NonNull Callback callback) {
    this.intent = intent;
    this.redirectUri = redirectUri;
    this.callback = callback;

    getInteractorExecutor().execute(this);
  }

  @Override public void run() {
    if (intent != null && intent.getData() != null && intent.getData().getScheme() != null && intent.getData()
        .getScheme()
        .equals(redirectUri)) {

      String code = intent.getData().getQueryParameter("code");

      HashMap<String, String> headers = new HashMap<>();
      headers.put("Accept", "application/json");

      HashMap<String, String> params = new HashMap<>();
      params.put("client_id", gitHubAPI.getClientId());
      params.put("client_secret", gitHubAPI.getClientSecret());
      params.put("code", code);

      try {
        String response = network.post(new RequestNetwork(GitHubAPI.GITHUB_OAUTH_TOKEN_URL, headers, params));
        GitHubAccessTokenDto GitHubAccessTokenDto = new GitHubAccessTokenDtoSerializer().deserialize(response);
        if (GitHubAccessTokenDto != null) {
          GitHubAccessToken gitHubAccessToken = new GitHubAccessTokenDtoToGitHubAccessToken().map(GitHubAccessTokenDto);
          notifySuccess(gitHubAccessToken);
        } else {
          throw new LocalIOException("response malformed");
        }
      } catch (Exception exception) {
        notifyFail(exception);
      }
    } else {
      notifyFail(new NetworkUnauthorizedException());
    }
  }

  private void notifySuccess(@NonNull GitHubAccessToken gitHubAccessToken) {
    if (callback != null) {
      callback.onSuccess(gitHubAccessToken);
    }
  }

  private void notifyFail(@NonNull Exception exception) {
    if (callback != null) {
      callback.onFailure(exception);
    }
  }
}
