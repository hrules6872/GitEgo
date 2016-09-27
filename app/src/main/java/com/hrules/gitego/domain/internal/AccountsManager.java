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
import com.hrules.gitego.domain.models.Account;
import com.hrules.gitego.domain.models.serializers.ListAccountSerializer;
import java.util.Collection;
import java.util.List;

public class AccountsManager extends BaseAccountsManager {
  public AccountsManager(Context context) {
    super(context);
  }

  public AccountsManager(Context context, boolean multipleAccounts) {
    super(context, multipleAccounts);
  }

  @Override public Collection<Account> getAccounts() {
    return new ListAccountSerializer().deserialize(
        preferences.getString(AppConstants.PREFS.ACCOUNTS, ""));
  }

  @NonNull @Override public Account getAccount(@NonNull Account whatAccount) {
    List<Account> accounts = (List<Account>) getAccounts();
    for (Account account : accounts) {
      if (account.getUser().equals(whatAccount.getUser())) {
        return account;
      }
    }
    return new Account();
  }

  @NonNull @Override public Account getDefaultAccount() {
    List<Account> accounts = (List<Account>) getAccounts();
    for (Account account : accounts) {
      if (account.isDefaultUser()) {
        return account;
      }
    }
    return new Account();
  }

  @Override public void setDefaultAccount(@NonNull Account whatAccount) {
    List<Account> accounts = (List<Account>) getAccounts();
    for (Account account : accounts) {
      account.setDefaultUser(false);
      if (account.getUser().equals(whatAccount.getUser())) {
        account.setDefaultUser(true);
        break;
      }
    }
  }

  @Override public boolean removeAccount(@NonNull Account whatAccount) {
    List<Account> accounts = (List<Account>) getAccounts();
    for (int position = 0; position < accounts.size(); position++) {
      if (accounts.get(position).getUser().equals(whatAccount.getUser())) {
        accounts.remove(position);
        putAccounts(accounts);
        return true;
      }
    }
    return false;
  }

  @Override public void addAccount(@NonNull Account account) {
    removeAccount(account);

    List<Account> accounts = (List<Account>) getAccounts();
    accounts.add(account);
    putAccounts(accounts);
  }

  @Override public boolean isMultipleAccountsAllowed() {
    return multipleAccounts;
  }

  @Override public void setMultipleAccountsAllowed(boolean newState) {
    multipleAccounts = newState;
  }

  private void putAccounts(List<Account> accounts) {
    preferences.save(AppConstants.PREFS.ACCOUNTS,
        new ListAccountSerializer().serialize(accounts));
  }
}
