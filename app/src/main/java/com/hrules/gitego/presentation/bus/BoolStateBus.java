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

package com.hrules.gitego.presentation.bus;

import android.support.annotation.NonNull;
import com.hrules.gitego.presentation.bus.base.BusModel;

public class BoolStateBus extends BusModel {
  private final boolean state;

  public BoolStateBus(@NonNull String action, boolean state) {
    super(action);
    this.state = state;
  }

  public boolean isState() {
    return state;
  }
}
