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

package com.hrules.gitego.data.persistence.database;

public final class DatabaseConstants {
  private DatabaseConstants() {
  }

  static final String DATABASE_NAME = "gitego.db";
  static final int DATABASE_VERSION = 2;

  public final class BOOLEAN {
    public static final int FALSE = 0;
    public static final int TRUE = 1;
  }

  public static final String KEY_USER_DATE = "date";
  public static final String KEY_USER_LOGIN = "login";
  public static final String KEY_USER_AVATAR_URL = "avatar_url";
  public static final String KEY_USER_HTML_URL = "html_url";
  public static final String KEY_USER_TYPE = "type";
  public static final String KEY_USER_NAME = "name";
  public static final String KEY_USER_FOLLOWERS = "followers";

  public static final String TABLE_USER = "USER";
  static final String SQL_CREATE_TABLE_USER = "create table "
      + TABLE_USER
      + " ("
      + KEY_USER_DATE
      + " STRING NOT NULL, "
      + KEY_USER_LOGIN
      + " STRING NOT NULL, "
      + KEY_USER_AVATAR_URL
      + " STRING NOT NULL, "
      + KEY_USER_HTML_URL
      + " STRING NOT NULL, "
      + KEY_USER_TYPE
      + " STRING NOT NULL, "
      + KEY_USER_NAME
      + " STRING NOT NULL, "
      + KEY_USER_FOLLOWERS
      + " INTEGER NOT NULL);";

  public static final String KEY_REPO_DATE = "date";
  public static final String KEY_REPO_ID = "repo_id";
  public static final String KEY_REPO_NAME = "name";
  public static final String KEY_REPO_IS_PRIVATE = "is_private";
  public static final String KEY_REPO_HTML_URL = "html_url";
  public static final String KEY_REPO_IS_FORK = "is_fork";
  public static final String KEY_REPO_HOMEPAGE = "homepage";
  public static final String KEY_REPO_STARGAZERS_COUNT = "stargazers_count";
  public static final String KEY_REPO_WATCHERS_COUNT = "watchers_count";
  public static final String KEY_REPO_FORKS_COUNT = "forks_count";
  public static final String KEY_REPO_LANGUAGE = "language";

  public static final String TABLE_REPO = "REPO";
  static final String SQL_CREATE_TABLE_REPO = "create table "
      + TABLE_REPO
      + " ("
      + KEY_REPO_DATE
      + " STRING NOT NULL, "
      + KEY_REPO_ID
      + " STRING NOT NULL, "
      + KEY_REPO_NAME
      + " STRING NOT NULL, "
      + KEY_REPO_IS_PRIVATE
      + " INTEGER NOT NULL, "
      + KEY_REPO_HTML_URL
      + " STRING NOT NULL, "
      + KEY_REPO_IS_FORK
      + " INTEGER NOT NULL, "
      + KEY_REPO_HOMEPAGE
      + " STRING NULL, "
      + KEY_REPO_STARGAZERS_COUNT
      + " INTEGER NOT NULL, "
      + KEY_REPO_WATCHERS_COUNT
      + " INTEGER NOT NULL, "
      + KEY_REPO_FORKS_COUNT
      + " INTEGER NOT NULL, "
      + KEY_REPO_LANGUAGE
      + " STRING NULL);";

  static final String TABLE_REPO_BACKUP = "REPO_BACKUP";
  static final String SQL_CREATE_TABLE_REPO_BACKUP_V1 = "create table " + TABLE_REPO_BACKUP
      + " ("
      + KEY_REPO_DATE
      + " STRING NOT NULL, "
      + KEY_REPO_ID
      + " STRING NOT NULL, "
      + KEY_REPO_NAME
      + " STRING NOT NULL, "
      + KEY_REPO_IS_PRIVATE
      + " INTEGER NOT NULL, "
      + KEY_REPO_HTML_URL
      + " STRING NOT NULL, "
      + KEY_REPO_IS_FORK
      + " INTEGER NOT NULL, "
      + KEY_REPO_HOMEPAGE
      + " STRING NULL, "
      + KEY_REPO_STARGAZERS_COUNT
      + " INTEGER NOT NULL, "
      + KEY_REPO_WATCHERS_COUNT
      + " INTEGER NOT NULL, "
      + KEY_REPO_FORKS_COUNT
      + " INTEGER NOT NULL, "
      + KEY_REPO_LANGUAGE
      + " STRING NOT NULL);";
}
