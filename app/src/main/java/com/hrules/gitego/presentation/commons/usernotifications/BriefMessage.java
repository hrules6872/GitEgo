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

package com.hrules.gitego.presentation.commons.usernotifications;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import java.util.LinkedList;
import java.util.Queue;

public enum BriefMessage {
  INSTANCE;

  private final Queue<String> queue = new LinkedList<>();

  public interface BriefMessageListener {
    void onClick();
  }

  private void removeDuplicates(@NonNull String message) {
    if (message.equals(queue.peek())) {
      queue.remove();
      removeDuplicates(message);
    }
  }

  public void showLong(@NonNull View view, @NonNull String message) {
    queue.offer(message);

    if (queue.size() == 1) {
      internalShowLong(view);
    }
  }

  @SuppressWarnings("deprecation") private void internalShowLong(@NonNull final View view) {
    final String message = queue.peek();
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).setCallback(new Snackbar.Callback() {
      @Override public void onDismissed(Snackbar transientBottomBar, int event) {
        removeDuplicates(message);
        if (queue.size() > 0) {
          internalShowLong(view);
        }
      }
    }).show();
  }

  public void showActionIndefinite(@NonNull View view, @NonNull String message, @NonNull String action,
      @NonNull final BriefMessageListener listener) {
    Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction(action, new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onClick();
      }
    }).show();
  }
}
