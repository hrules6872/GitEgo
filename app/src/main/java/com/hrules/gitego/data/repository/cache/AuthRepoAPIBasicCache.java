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

package com.hrules.gitego.data.repository.cache;

import com.hrules.gitego.data.repository.cache.base.BasicCache;
import java.util.concurrent.TimeUnit;

public final class AuthRepoAPIBasicCache extends BasicCache {
  private static final String CACHE_ID = "AuthRepoAPIBasicCache";
  private static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(2);

  public AuthRepoAPIBasicCache() {
    super(CACHE_ID, EXPIRATION_TIME);
  }
}
