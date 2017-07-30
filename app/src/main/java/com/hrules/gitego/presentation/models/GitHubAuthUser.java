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
import com.google.auto.value.AutoValue;
import com.hrules.gitego.presentation.models.base.Model;

@AutoValue public abstract class GitHubAuthUser extends Model {
  public abstract String getDate();

  public abstract String getUser();

  public abstract String getAvatarUrl();

  public abstract String getHtmlUrl();

  public abstract String getType();

  public abstract String getName();

  public abstract int getFollowers();

  @Nullable public abstract GitHubAuthUser getGitHubAuthUserOlder();

  public static Builder builder() {
    return new AutoValue_GitHubAuthUser.Builder();
  }

  @AutoValue.Builder public abstract static class Builder {
    public abstract Builder date(String newDate);

    public abstract Builder user(String newUser);

    public abstract Builder avatarUrl(String newAvatarUrl);

    public abstract Builder htmlUrl(String newHtmlUrl);

    public abstract Builder type(String newType);

    public abstract Builder name(String newName);

    public abstract Builder followers(int newFollowers);

    public abstract Builder gitHubAuthUserOlder(GitHubAuthUser newGitHubAuthUserOlder);

    public abstract GitHubAuthUser build();
  }

  abstract Builder toBuilder();

  public GitHubAuthUser withGitHubAuthRepoOlder(GitHubAuthUser newGitHubAuthUserOlder) {
    return toBuilder().gitHubAuthUserOlder(newGitHubAuthUserOlder).build();
  }
}
