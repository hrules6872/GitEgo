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

package com.hrules.gitego.domain.errors;

import android.support.annotation.NonNull;
import com.hrules.gitego.data.exceptions.NetworkUnauthorizedException;

public class NetworkUnauthorizedError implements com.hrules.imclean.domain.errors.Error {
  private static final String DEFAULT_MESSAGE = "NetworkUnauthorizedError";

  @NonNull @Override public Exception getException() {
    return new NetworkUnauthorizedException();
  }

  @NonNull @Override public String getMessage() {
    return DEFAULT_MESSAGE;
  }
}
