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

package com.hrules.gitego.commons;

import android.util.Log;
import com.hrules.gitego.BuildConfig;

/*
 * +info: https://gist.github.com/hrules6872/3e047ef1a7ed2a525104
 */

public class DebugLog {
  private static final int LOG_TAG_MAX_LENGTH = 23;

  private DebugLog() {
  }

  private static boolean isDebuggable() {
    return BuildConfig.DEBUG;
  }

  private static String formatMessage(String message) {
    StackTraceElement[] stackTrace = (new Throwable().getStackTrace());
    if (stackTrace.length >= 2) {
      return String.format("[%s:%s] -> %s(): %s", stackTrace[2].getFileName(), stackTrace[2].getLineNumber(),
          stackTrace[2].getMethodName(), message);
    } else {
      return message;
    }
  }

  private static String getApplicationId() {
    String applicationId = BuildConfig.APPLICATION_ID;
    if (applicationId.length() > LOG_TAG_MAX_LENGTH) {
      applicationId = applicationId.substring(0, LOG_TAG_MAX_LENGTH);
    }
    return applicationId;
  }

  public static void d(Object message) {
    if (isDebuggable()) {
      Log.d(getApplicationId(), formatMessage(String.valueOf(message)));
    }
  }

  public static void d(Object message, Throwable tr) {
    if (isDebuggable()) {
      Log.d(getApplicationId(), formatMessage(String.valueOf(message)), tr);
    }
  }

  public static void e(Object message) {
    if (isDebuggable()) {
      Log.e(getApplicationId(), formatMessage(String.valueOf(message)));
    }
  }

  public static void e(Object message, Throwable tr) {
    if (isDebuggable()) {
      Log.e(getApplicationId(), formatMessage(String.valueOf(message)), tr);
    }
  }

  public static void i(Object message) {
    if (isDebuggable()) {
      Log.i(getApplicationId(), formatMessage(String.valueOf(message)));
    }
  }

  public static void i(Object message, Throwable tr) {
    if (isDebuggable()) {
      Log.i(getApplicationId(), formatMessage(String.valueOf(message)), tr);
    }
  }

  public static void v(Object message) {
    if (isDebuggable()) {
      Log.v(getApplicationId(), formatMessage(String.valueOf(message)));
    }
  }

  public static void v(Object message, Throwable tr) {
    if (isDebuggable()) {
      Log.v(getApplicationId(), formatMessage(String.valueOf(message)), tr);
    }
  }

  public static void w(Object message) {
    if (isDebuggable()) {
      Log.w(getApplicationId(), formatMessage(String.valueOf(message)));
    }
  }

  public static void w(Object message, Throwable tr) {
    if (isDebuggable()) {
      Log.w(getApplicationId(), formatMessage(String.valueOf(message)), tr);
    }
  }
}
