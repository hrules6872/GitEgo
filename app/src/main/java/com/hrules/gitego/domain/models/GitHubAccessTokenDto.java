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

package com.hrules.gitego.domain.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue public abstract class GitHubAccessTokenDto {
  @SerializedName("access_token") public abstract String getAccessToken();

  @SerializedName("token_type") public abstract String getTokenType();

  @SerializedName("scope") public abstract String getScope();

  public static TypeAdapter<GitHubAccessTokenDto> typeAdapter(Gson gson) {
    return new AutoValue_GitHubAccessTokenDto.GsonTypeAdapter(gson);
  }
}
