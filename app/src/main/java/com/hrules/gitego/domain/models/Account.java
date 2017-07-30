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

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue public abstract class Account {
  @SerializedName("user") public abstract String getUser();

  @SerializedName("type") public abstract String getType();

  @SerializedName("token") public abstract String getToken();

  @SerializedName("defaultUser") public abstract boolean isDefaultUser();

  public static Builder builder() {
    return new AutoValue_Account.Builder().user("").type("").token("").defaultUser(false);
  }

  @SuppressWarnings("WeakerAccess") @AutoValue.Builder public abstract static class Builder {
    public abstract Builder user(String newUser);

    public abstract Builder type(String newType);

    public abstract Builder token(String newToken);

    public abstract Builder defaultUser(boolean newDefaultUser);

    public abstract Account build();
  }

  public static TypeAdapter<Account> typeAdapter(Gson gson) {
    return new AutoValue_Account.GsonTypeAdapter(gson);
  }

  abstract Builder toBuilder();

  public Account withDefaultUser(boolean newDefaultUser) {
    return toBuilder().defaultUser(newDefaultUser).build();
  }
}
