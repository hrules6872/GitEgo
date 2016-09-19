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

package com.hrules.gitego.presentation.commons.components;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.TextView;

/*
 * +info: https://gist.github.com/hrules6872/516ea7e04c8be4c55d26
 */
public class UnderlineTextView extends TextView {
  private int textColor;

  public UnderlineTextView(@NonNull Context context) {
    this(context, null);
  }

  public UnderlineTextView(@NonNull Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public UnderlineTextView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public UnderlineTextView(@NonNull Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override public void setText(CharSequence text, BufferType type) {
    SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
    super.setText(spannableString, type);
  }

  @SuppressLint("InlinedApi") @Override public boolean onTouchEvent(MotionEvent event) {
    if (isClickable()) {
      if (event.getAction() == MotionEvent.ACTION_DOWN) {
        textColor = getCurrentTextColor();
        setTextColor(getColorFromTheme(android.R.attr.colorAccent, lightenColor(textColor, 0.5f)));
      } else if (event.getAction() == MotionEvent.ACTION_UP) {
        setTextColor(textColor);
      }
    }
    return super.onTouchEvent(event);
  }

  private int getColorFromTheme(int id, int defaultValue) {
    TypedValue value = new TypedValue();
    try {
      Resources.Theme theme = getContext().getTheme();
      if (theme != null && theme.resolveAttribute(id, value, true)) {
        if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
          return value.data;
        } else if (value.type == TypedValue.TYPE_STRING) {
          return ContextCompat.getColor(getContext(), value.resourceId);
        }
      }
    } catch (Exception ignored) {
    }
    return defaultValue;
  }

  private int lightenColor(int color, float factor) {
    int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
    int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
    int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
    return Color.argb(Color.alpha(color), red, green, blue);
  }
}
