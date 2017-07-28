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

import com.google.gson.annotations.SerializedName;
import com.hrules.gitego.domain.models.base.ModelDto;

public final class GitHubAuthUserDto extends ModelDto<String> {
  private String date;

  @SerializedName("login") private String login;

  @SerializedName("avatar_url") private String avatar_url;

  @SerializedName("html_url") private String html_url;

  @SerializedName("type") private String type;

  @SerializedName("name") private String name;

  @SerializedName("followers") private int followers;

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
}
