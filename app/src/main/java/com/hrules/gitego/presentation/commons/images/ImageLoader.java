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

package com.hrules.gitego.presentation.commons.images;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.widget.ImageView;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.commons.images.transforms.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;

public final class ImageLoader {
  private ImageLoader() {
  }

  public static void loadRounded(@NonNull Context context, @NonNull String url, @NonNull ImageView target) {
    Resources res = context.getResources();
    int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, res.getDimension(R.dimen.avatar_cornerRadius),
        res.getDisplayMetrics());
    Picasso.with(context).load(url).error(R.drawable.noconnection).transform(new RoundedCornersTransformation(radius)).into(target);
  }
}
