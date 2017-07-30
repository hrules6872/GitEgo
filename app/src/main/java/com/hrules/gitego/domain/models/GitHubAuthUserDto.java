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

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.hrules.gitego.domain.models.base.ModelDto;

@AutoValue public abstract class GitHubAuthUserDto extends ModelDto {
  @Nullable public abstract String getDate();

  @SerializedName("login") public abstract String getUser();

  @SerializedName("avatar_url") public abstract String getAvatarUrl();

  @SerializedName("html_url") public abstract String getHtmlUrl();

  @SerializedName("type") public abstract String getType();

  @SerializedName("name") public abstract String getName();

  @SerializedName("followers") public abstract int getFollowers();

  public static Builder builder() {
    return new AutoValue_GitHubAuthUserDto.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {
    public abstract Builder date(String newDate);

    public abstract Builder user(String newUser);

    public abstract Builder avatarUrl(String newAvatarUrl);

    public abstract Builder htmlUrl(String newHtmlUrl);

    public abstract Builder type(String newType);

    public abstract Builder name(String newName);

    public abstract Builder followers(int newFollowers);

    public abstract GitHubAuthUserDto build();
  }

  public static TypeAdapter<GitHubAuthUserDto> typeAdapter(Gson gson) {
    return new AutoValue_GitHubAuthUserDto.GsonTypeAdapter(gson);
  }

  abstract Builder toBuilder();

  public GitHubAuthUserDto withDate(String newDate) {
    return toBuilder().date(newDate).build();
  }
}
