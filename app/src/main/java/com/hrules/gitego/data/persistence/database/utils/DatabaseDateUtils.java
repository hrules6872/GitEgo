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

package com.hrules.gitego.data.persistence.database.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat") public final class DatabaseDateUtils {
  private final static String DATE_SQL_FORMAT = "yyyy-MM-dd";

  private DatabaseDateUtils() {
  }

  public static String formatDateToSQLShort(long millis) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_SQL_FORMAT);
    Date now = new Date(millis);
    return simpleDateFormat.format(now);
  }
}
