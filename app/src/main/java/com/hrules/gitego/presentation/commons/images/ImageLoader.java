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

package com.hrules.gitego.presentation.commons.images;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class ImageLoader {
  public static void load(@NonNull Context context, @NonNull String url, @NonNull ImageView target) {
    Picasso.with(context).load(url).into(target);
  }
}
