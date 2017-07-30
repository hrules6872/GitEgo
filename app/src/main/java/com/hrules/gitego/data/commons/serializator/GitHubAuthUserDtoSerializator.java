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

package com.hrules.gitego.data.commons.serializator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hrules.gitego.data.commons.serializator.base.Serializator;
import com.hrules.gitego.data.commons.serializator.extras.SerializatorTypeAdapterFactory;
import com.hrules.gitego.domain.models.GitHubAuthUserDto;

public class GitHubAuthUserDtoSerializator implements Serializator {
  private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(SerializatorTypeAdapterFactory.create()).create();

  @Nullable @Override public <T> String to(@NonNull T output) {
    return gson.toJson(output);
  }

  @SuppressWarnings("unchecked") @Nullable @Override public <T> T from(@NonNull String input) {
    return (T) gson.fromJson(input, GitHubAuthUserDto.class);
  }
}
