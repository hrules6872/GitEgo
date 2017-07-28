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

import android.text.Spannable;
import com.hrules.gitego.presentation.models.base.Model;

public final class GitHubAuthRepo extends Model<String> {
  private String date;
  private String id;
  private String name;
  private boolean _private;
  private String html_url;
  private boolean fork;
  private String homepage;
  private int stargazers_count;
  private int watchers_count;
  private int forks_count;
  private String language;
  private Spannable stargazers_countSpannable;
  private Spannable watchers_countSpannable;
  private Spannable forks_countSpannable;
  private GitHubAuthRepo gitHubAuthRepoOlder;

  @Override public String createModelId() {
    return getDate() + SEPARATOR + getId();
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

  public void setPrivate(boolean bool) {
    this._private = bool;
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

  public Spannable getStargazers_countSpannable() {
    return stargazers_countSpannable;
  }

  public void setStargazers_countSpannable(Spannable stargazers_countSpannable) {
    this.stargazers_countSpannable = stargazers_countSpannable;
  }

  public Spannable getWatchers_countSpannable() {
    return watchers_countSpannable;
  }

  public void setWatchers_countSpannable(Spannable watchers_countSpannable) {
    this.watchers_countSpannable = watchers_countSpannable;
  }

  public Spannable getForks_countSpannable() {
    return forks_countSpannable;
  }

  public void setForks_countSpannable(Spannable forks_countSpannable) {
    this.forks_countSpannable = forks_countSpannable;
  }

  public GitHubAuthRepo getGitHubAuthRepoOlder() {
    return gitHubAuthRepoOlder;
  }

  public void setGitHubAuthRepoOlder(GitHubAuthRepo gitHubAuthRepoOlder) {
    this.gitHubAuthRepoOlder = gitHubAuthRepoOlder;
  }
}
