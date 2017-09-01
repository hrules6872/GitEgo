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

package com.hrules.gitego.presentation.commons.resources;

import android.support.annotation.NonNull;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.commons.resources.base.ResWrapper;

public final class ResString {
  private ResString() {
  }

  public @NonNull static String getLoginFail() {
    return ResWrapper.getString(R.string.error_loginFail);
  }

  public @NonNull static String getNetworkFail() {
    return ResWrapper.getString(R.string.error_networkFail);
  }

  public @NonNull static String getActionLogin() {
    return ResWrapper.getString(R.string.action_login);
  }
}
