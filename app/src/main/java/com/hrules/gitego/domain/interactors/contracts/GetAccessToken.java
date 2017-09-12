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

package com.hrules.gitego.domain.interactors.contracts;

import android.support.annotation.NonNull;
import com.hrules.gitego.presentation.models.GitHubAccessToken;
import com.hrules.gitego.presentation.models.Intent;

public interface GetAccessToken {
  interface Callback {
    void onSuccess(@NonNull GitHubAccessToken gitHubAccessToken);

    void onFailure(@NonNull com.hrules.imclean.domain.errors.Error error);
  }

  void execute(@NonNull Intent intent, @NonNull String redirectUri, @NonNull Callback callback);
}
