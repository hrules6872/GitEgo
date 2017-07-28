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

package com.hrules.gitego.presentation.views.utils;

import android.support.annotation.NonNull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class DRMVPUtils {
  @SuppressWarnings({ "unchecked", "TryWithIdenticalCatches" }) public static <P> P getDeclaredPresenter(@NonNull Class clazz) {
    Type genericSuperclass;
    for (; ; ) {
      genericSuperclass = clazz.getGenericSuperclass();
      if (genericSuperclass instanceof ParameterizedType) {
        break;
      }
      clazz = clazz.getSuperclass();
    }
    Type presenterClass = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    try {
      return (P) Class.forName(presenterClass.toString().split(" ")[1]).newInstance();
    } catch (InstantiationException e) {
      throw new IllegalArgumentException();
    } catch (IllegalAccessException e) {
      throw new IllegalArgumentException();
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException();
    }
  }
}
