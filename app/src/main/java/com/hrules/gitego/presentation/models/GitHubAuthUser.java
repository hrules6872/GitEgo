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

import com.hrules.gitego.presentation.models.base.Model;

public final class GitHubAuthUser extends Model<String> {
  private String date;
  private String login;
  private String avatar_url;
  private String html_url;
  private String type;
  private String name;
  private int followers;
  private GitHubAuthUser gitHubAuthUserOlder;

  @Override public String createModelId() {
    return getDate();
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getLogin() {
    return login;
  }

  public String getAvatar_url() {
    return avatar_url;
  }

  public String getHtml_url() {
    return html_url;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public int getFollowers() {
    return followers;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setAvatar_url(String avatar_url) {
    this.avatar_url = avatar_url;
  }

  public void setHtml_url(String html_url) {
    this.html_url = html_url;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }

  public GitHubAuthUser getGitHubAuthUserOlder() {
    return gitHubAuthUserOlder;
  }

  public void setGitHubAuthUserOlder(GitHubAuthUser gitHubAuthUserOlder) {
    this.gitHubAuthUserOlder = gitHubAuthUserOlder;
  }
}
