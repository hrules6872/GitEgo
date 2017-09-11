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

package com.hrules.gitego.commons;

import android.os.Build;

/*
 * +info: https://gist.github.com/hrules6872/7f8ba91500b86bad987a1f70e91c7b05
 */

public class SupportVersion {
  private SupportVersion() {
  }

  public static boolean isLollipopOrAbove() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }

  public static boolean isMarshmallowOrAbove() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }

  public static boolean isOreoOrAbove() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
  }
}