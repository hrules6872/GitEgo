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

package com.hrules.gitego.domain.models.serializers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hrules.gitego.data.commons.serializator.ListGitHubAuthRepoDtoSerializator;
import com.hrules.gitego.data.commons.serializator.base.Serializator;
import com.hrules.gitego.domain.models.GitHubAuthRepoDto;
import com.hrules.imclean.domain.models.serializers.Serializer;
import java.util.ArrayList;
import java.util.Collection;

public final class GitHubAuthRepoDtoSerializer implements Serializer<Collection<GitHubAuthRepoDto>, String> {
  private final Serializator serializator = new ListGitHubAuthRepoDtoSerializator();

  @NonNull public Collection<GitHubAuthRepoDto> deserialize(@NonNull String from) {
    Collection<GitHubAuthRepoDto> list = serializator.from(from);
    return list != null ? list : new ArrayList<GitHubAuthRepoDto>();
  }

  @Nullable @Override public String serialize(@NonNull Collection<GitHubAuthRepoDto> from) {
    throw new UnsupportedOperationException();
  }
}
