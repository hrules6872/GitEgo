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

package com.hrules.gitego.domain.models.mappers.base;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public final class ListMapper<M1, M2> implements InverseMapper<List<M1>, List<M2>> {
  private final InverseMapper<M1, M2> mapper;

  public ListMapper(@NonNull InverseMapper<M1, M2> mapper) {
    this.mapper = mapper;
  }

  @Override public List<M2> map(@NonNull List<M1> from) {
    ArrayList<M2> listMapped = new ArrayList<>();
    if (!from.isEmpty()) {
      for (M1 model : from) {
        listMapped.add(mapper.map(model));
      }
    }
    return listMapped;
  }

  @Override public List<M1> inverseMap(@NonNull List<M2> from) {
    ArrayList<M1> listMapped = new ArrayList<>();
    if (!from.isEmpty()) {
      for (M2 model : from) {
        listMapped.add(mapper.inverseMap(model));
      }
    }
    return listMapped;
  }
}
