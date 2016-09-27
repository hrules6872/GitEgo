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

package com.hrules.gitego.domain.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import com.hrules.gitego.AppConstants;
import com.hrules.gitego.data.persistence.preferences.Preferences;
import com.hrules.gitego.domain.models.Account;
import java.util.Collection;

abstract class BaseAccountsManager {
  boolean multipleAccounts;
  final Preferences preferences;

  BaseAccountsManager(@NonNull Context context) {
    this(context, AppConstants.DEFAULTS.ACCOUNTS_MULTIPLE_DEFAULT);
  }

  BaseAccountsManager(@NonNull Context context, boolean multipleAccounts) {
    this.multipleAccounts = multipleAccounts;

    preferences = new Preferences(context);
  }

  public abstract Collection<Account> getAccounts();

  public abstract @NonNull Account getAccount(@NonNull Account whatAccount);

  public abstract @NonNull Account getDefaultAccount();

  public abstract void setDefaultAccount(@NonNull Account whatAccount);

  public abstract boolean removeAccount(@NonNull Account whatAccount);

  public abstract void addAccount(@NonNull Account account);

  public abstract boolean isMultipleAccountsAllowed();

  public abstract void setMultipleAccountsAllowed(boolean newState);
}
