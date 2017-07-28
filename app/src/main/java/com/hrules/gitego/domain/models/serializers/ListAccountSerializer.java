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

package com.hrules.gitego.domain.models.serializers;

import android.support.annotation.NonNull;
import com.google.gson.reflect.TypeToken;
import com.hrules.gitego.data.commons.Json;
import com.hrules.gitego.domain.models.Account;
import com.hrules.gitego.domain.models.serializers.base.Serializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public final class ListAccountSerializer implements Serializer<Collection<Account>, String> {
  public Collection<Account> deserialize(@NonNull String from) {
    Type type = new TypeToken<Collection<Account>>() {
    }.getType();
    Collection<Account> accountsDeserialize = new Json().fromJson(from, type);
    return accountsDeserialize != null ? accountsDeserialize : new ArrayList<Account>();
  }

  public String serialize(@NonNull Collection<Account> from) {
    return new Json().toJson(from);
  }
}
