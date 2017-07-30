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

package com.hrules.gitego.presentation.models;

import com.google.auto.value.AutoValue;

@AutoValue public abstract class GitHubAccessToken {
  public abstract String getAccessToken();

  public abstract String getTokenType();

  public abstract String getScope();

  public static Builder builder() {
    return new AutoValue_GitHubAccessToken.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {
    public abstract Builder accessToken(String newAccessToken);

    public abstract Builder tokenType(String newTokenType);

    public abstract Builder scope(String newScope);

    public abstract GitHubAccessToken build();
  }
}
