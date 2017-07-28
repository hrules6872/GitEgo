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

package com.hrules.gitego.data.repository.base;

import android.support.annotation.NonNull;
import com.hrules.gitego.domain.specifications.base.Specification;
import java.util.List;

public interface RepositoryInterface<T> {
  interface QueryCallback<T> {
    void onSuccess(@NonNull List<T> response);

    void onFailure(@NonNull Exception exception);

    void onFinish();
  }

  void addOrUpdate(@NonNull T item) throws Exception;

  void addOrUpdate(@NonNull Iterable<T> items) throws Exception;

  void addOrUpdate(@NonNull Specification specification) throws Exception;

  void remove(@NonNull T item) throws Exception;

  void remove(@NonNull Iterable<T> items) throws Exception;

  void remove(@NonNull Specification specification) throws Exception;

  void query(@NonNull Specification specification, @NonNull QueryCallback callback);
}
