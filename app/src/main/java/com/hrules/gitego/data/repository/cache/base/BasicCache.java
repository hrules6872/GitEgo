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

package com.hrules.gitego.data.repository.cache.base;

import android.support.annotation.NonNull;
import java.util.WeakHashMap;

public class BasicCache {
  private static final WeakHashMap<String, Long> cacheHashMap = new WeakHashMap<>();
  private final String cacheId;
  private final long expirationTime;

  protected BasicCache(@NonNull String cacheId, long expirationTime) {
    this.cacheId = cacheId;
    this.expirationTime = expirationTime;
  }

  public boolean isExpired() {
    if (cacheHashMap.isEmpty() || !cacheHashMap.containsKey(cacheId)) {
      return true;
    }

    long currentTime = System.currentTimeMillis();
    long lastTimeStamp = cacheHashMap.get(cacheId);
    return (currentTime - lastTimeStamp) >= expirationTime;
  }

  public void persist() {
    cacheHashMap.put(cacheId, System.currentTimeMillis());
  }
}
