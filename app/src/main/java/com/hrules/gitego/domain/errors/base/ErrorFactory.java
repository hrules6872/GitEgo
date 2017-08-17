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

package com.hrules.gitego.domain.errors.base;

import android.support.annotation.NonNull;
import com.hrules.gitego.data.exceptions.NetworkIOException;
import com.hrules.gitego.data.exceptions.NetworkUnauthorizedException;
import com.hrules.gitego.domain.errors.NetworkIOError;
import com.hrules.gitego.domain.errors.NetworkUnauthorizedError;
import com.hrules.gitego.domain.errors.NotCheckedError;

public final class ErrorFactory {
  private ErrorFactory() {
  }

  public static @NonNull Error create(@NonNull Exception exception) {
    if (exception instanceof NetworkUnauthorizedException) {
      return new NetworkUnauthorizedError();
    } else if (exception instanceof NetworkIOException) {
      return new NetworkIOError(exception.getMessage());
    }
    return new NotCheckedError(exception);
  }
}
