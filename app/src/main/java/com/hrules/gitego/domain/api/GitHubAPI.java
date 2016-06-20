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

package com.hrules.gitego.domain.api;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.hrules.gitego.domain.models.Account;

public class GitHubAPI {
  private static final String GITHUB_OAUTH_AUTHORIZE_URL =
      "https://github.com/login/oauth/authorize";
  public static final String GITHUB_OAUTH_TOKEN_URL = "https://github.com/login/oauth/access_token";
  public static final String GITHUB_GET_AUTHUSER_URL = "https://api.github.com/user";
  public static final String GITHUB_GET_AUTHREPO_URL = "https://api.github.com/user/repos";

  private final String clientId;
  private final String clientSecret;
  private final String scopes;

  private Account account;

  public GitHubAPI(@NonNull String clientId, @NonNull String clientSecret, @NonNull String scopes) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.scopes = scopes;
  }

  public void launchOAuthLogin(@NonNull Context context) {
    Uri uri = Uri.parse(GITHUB_OAUTH_AUTHORIZE_URL)
        .buildUpon()
        .appendQueryParameter("client_id", clientId)
        .appendQueryParameter("scopes", scopes)
        .build();

    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    Intent chooser = Intent.createChooser(intent, null);
    chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(chooser);
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Account getAccount() {
    return account;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public String getScopes() {
    return scopes;
  }
}
