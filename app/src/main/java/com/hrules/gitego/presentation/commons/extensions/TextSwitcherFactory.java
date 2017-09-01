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

package com.hrules.gitego.presentation.commons.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public final class TextSwitcherFactory implements ViewSwitcher.ViewFactory {
  private final TextSwitcher textSwitcher;
  private final String text;
  private final int style;

  public TextSwitcherFactory(@NonNull TextSwitcher textSwitcher, @NonNull String text, @StyleRes int style) {
    textSwitcher.setInAnimation(AnimationUtils.loadAnimation(textSwitcher.getContext(), android.R.anim.fade_in));
    textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(textSwitcher.getContext(), android.R.anim.fade_out));

    this.textSwitcher = textSwitcher;
    this.text = text;
    this.style = style;
  }

  @Override public View makeView() {
    TextView textView = new TextView(new ContextThemeWrapper(textSwitcher.getContext(), style), null, 0);
    textView.setText(text);
    return textView;
  }
}
