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

package com.hrules.gitego.presentation.commons;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class StringUtils {
  private static Spannable colorFormattedVariation(@NonNull String formattedVariation, @NonNull String formattedVariationWithParenthesis,
      int variationValue, @ColorInt int positiveColor, @ColorInt int negativeColor) {

    Spannable spannable = new SpannableString(formattedVariation);
    if (formattedVariation.contains(formattedVariationWithParenthesis)) {
      int formattedVariationLastPosition = formattedVariation.lastIndexOf(formattedVariationWithParenthesis);
      if (variationValue > 0) {
        spannable.setSpan(new ForegroundColorSpan(positiveColor), formattedVariationLastPosition,
            formattedVariationLastPosition + formattedVariationWithParenthesis.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      } else if (variationValue < 0) {
        spannable.setSpan(new ForegroundColorSpan(negativeColor), formattedVariationLastPosition,
            formattedVariationLastPosition + formattedVariationWithParenthesis.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }
    return spannable;
  }

  public static Spannable createVariationSpannableString(@NonNull String targetString, int nowValue, int oldValue,
      @ColorInt int colorPositive, @ColorInt int colorNegative) {
    int variationValue = nowValue - oldValue;
    String formattedVariationWithParenthesis = variationValue == 0 ? "" : "(" + (variationValue > 0 ? "+" : "") + variationValue + ")";
    String formattedVariation = String.format(targetString, nowValue, formattedVariationWithParenthesis);
    return StringUtils.colorFormattedVariation(formattedVariation, formattedVariationWithParenthesis, variationValue, colorPositive,
        colorNegative);
  }
}
