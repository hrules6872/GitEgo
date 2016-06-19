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
import com.hrules.gitego.data.persistence.database.DatabaseConstants;
import com.hrules.gitego.domain.models.base.ModelDto;

public class GitHubAuthRepoDto extends ModelDto<String> {
  private String date;

  @SerializedName("id") private String id;

  @SerializedName("name") private String name;

  @SerializedName("private") private boolean _private;

  @SerializedName("html_url") private String html_url;

  @SerializedName("fork") private boolean fork;

  @SerializedName("homepage") private String homepage;

  @SerializedName("stargazers_count") private int stargazers_count;

  @SerializedName("watchers_count") private int watchers_count;

  @SerializedName("forks_count") private int forks_count;

  @SerializedName("language") private String language;

  @SerializedName("subscribers_url") private String subscribers_url;

  @Override public String createModelId() {
    return getDate() + DatabaseConstants.SEPARATOR + getId();
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isPrivate() {
    return _private;
  }

  public void setPrivate(boolean _private) {
    this._private = _private;
  }

  public String getHtml_url() {
    return html_url;
  }

  public void setHtml_url(String html_url) {
    this.html_url = html_url;
  }

  public boolean isFork() {
    return fork;
  }

  public void setFork(boolean fork) {
    this.fork = fork;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public int getStargazers_count() {
    return stargazers_count;
  }

  public void setStargazers_count(int stargazers_count) {
    this.stargazers_count = stargazers_count;
  }

  public int getWatchers_count() {
    return watchers_count;
  }

  public void setWatchers_count(int watchers_count) {
    this.watchers_count = watchers_count;
  }

  public int getForks_count() {
    return forks_count;
  }

  public void setForks_count(int forks_count) {
    this.forks_count = forks_count;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getSubscribers_url() {
    return subscribers_url;
  }

  public void setSubscribers_url(String subscribers_url) {
    this.subscribers_url = subscribers_url;
  }
}
