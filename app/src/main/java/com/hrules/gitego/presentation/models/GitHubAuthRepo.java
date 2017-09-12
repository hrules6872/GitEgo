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

import android.support.annotation.Nullable;
import android.text.Spannable;
import com.google.auto.value.AutoValue;
import com.hrules.imclean.presentation.models.Model;

@AutoValue public abstract class GitHubAuthRepo extends Model {
  public abstract String getDate();

  public abstract String getId();

  public abstract String getName();

  public abstract boolean isPrivateRepository();

  public abstract String getHtmlUrl();

  public abstract boolean isFork();

  @Nullable public abstract String getHomepage();

  public abstract int getStargazersCount();

  public abstract int getWatchersCount();

  public abstract int getForksCount();

  @Nullable public abstract String getLanguage();

  @Nullable public abstract Spannable getStargazersCountSpannable();

  @Nullable public abstract Spannable getWatchersCountSpannable();

  @Nullable public abstract Spannable getForksCountSpannable();

  @Nullable public abstract GitHubAuthRepo getGitHubAuthRepoOlder();

  public static Builder builder() {
    return new AutoValue_GitHubAuthRepo.Builder();
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

    public abstract Builder stargazersCountSpannable(Spannable newStargazersCountSpannable);

    public abstract Builder watchersCountSpannable(Spannable newWatchersCountSpannable);

    public abstract Builder forksCountSpannable(Spannable newForksCountSpannable);

    public abstract Builder gitHubAuthRepoOlder(GitHubAuthRepo newGitHubAuthRepoOlder);

    public abstract GitHubAuthRepo build();
  }

  abstract Builder toBuilder();

  public GitHubAuthRepo withGitHubAuthRepoOlder(GitHubAuthRepo newGitHubAuthRepoOlder) {
    return toBuilder().gitHubAuthRepoOlder(newGitHubAuthRepoOlder).build();
  }

  public GitHubAuthRepo withCountSpannables(Spannable newStargazersCountSpannable, Spannable newWatchersCountSpannable,
      Spannable newForksCountSpannable) {
    return toBuilder().stargazersCountSpannable(newStargazersCountSpannable)
        .watchersCountSpannable(newWatchersCountSpannable)
        .forksCountSpannable(newForksCountSpannable)
        .build();
  }
}
