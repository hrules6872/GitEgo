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

package com.hrules.gitego.domain.models;

import com.google.gson.annotations.SerializedName;

public class GitHubAccessTokenDto {
  @SerializedName("access_token") private String access_token;

  @SerializedName("token_type") private String token_type;

  @SerializedName("scope") private String scope;

  public String getAccess_token() {
    return access_token;
  }

  public String getToken_type() {
    return token_type;
  }

  public String getScope() {
    return scope;
  }
}
