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

@AutoValue public abstract class GitHubAuthRepoDto extends ModelDto {
  @Nullable public abstract String getDate();

  @SerializedName("id") public abstract String getId();

  @SerializedName("name") public abstract String getName();

  @SerializedName("private") public abstract boolean isPrivateRepository();

  @SerializedName("html_url") public abstract String getHtmlUrl();

  @SerializedName("fork") public abstract boolean isFork();

  @SerializedName("homepage") @Nullable public abstract String getHomepage();

  @SerializedName("stargazers_count") public abstract int getStargazersCount();

  @SerializedName("watchers_count") public abstract int getWatchersCount();

  @SerializedName("forks_count") public abstract int getForksCount();

  @SerializedName("language") public abstract String getLanguage();

  @SerializedName("subscribers_url") @Nullable public abstract String getSubscribersUrl();

  public static Builder builder() {
    return new AutoValue_GitHubAuthRepoDto.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {

    public abstract Builder date(String newDate);

    public abstract Builder id(String newId);

    public abstract Builder name(String newName);

    public abstract Builder privateRepository(boolean newPrivateRepository);

    public abstract Builder htmlUrl(String newHtmlUrl);

    public abstract Builder fork(boolean newFork);

    public abstract Builder homepage(String newHomepage);

    public abstract Builder stargazersCount(int newStargazersCount);

    public abstract Builder watchersCount(int newWatchersCount);

    public abstract Builder forksCount(int newForksCount);

    public abstract Builder language(String newLanguage);

    public abstract Builder subscribersUrl(String newSubscribersUrl);

    public abstract GitHubAuthRepoDto build();
  }

  public static TypeAdapter<GitHubAuthRepoDto> typeAdapter(Gson gson) {
    return new AutoValue_GitHubAuthRepoDto.GsonTypeAdapter(gson);
  }

  abstract Builder toBuilder();

  public GitHubAuthRepoDto withDate(String newDate) {
    return toBuilder().date(newDate).build();
  }

  public GitHubAuthRepoDto withDateAndWatchersCount(String newDate, int newWatchersCount) {
    return toBuilder().date(newDate).watchersCount(newWatchersCount).build();
  }
}
