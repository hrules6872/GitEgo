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

package com.hrules.gitego.data.persistence.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.hrules.gitego.data.exceptions.LocalIOException;
import java.util.concurrent.atomic.AtomicInteger;

public final class Database {
  private static Database instance = null;

  private static DatabaseOpenHelper helper;
  private SQLiteDatabase database;

  private final AtomicInteger openCounter = new AtomicInteger();

  private Database(@NonNull Context context) {
    helper = new DatabaseOpenHelper(context);
  }

  public static synchronized Database with(@NonNull Context context) {
    if (instance == null) {
      instance = new Database(context);
    }
    return instance;
  }

  public synchronized void open() throws SQLException {
    if (openCounter.incrementAndGet() == 1) {
      database = helper.getWritableDatabase();
    }
  }

  public synchronized void close() {
    if (openCounter.decrementAndGet() == 0) {
      database.close();
    }
  }

  public SQLiteDatabase getDatabase() {
    return database;
  }

  public Cursor get(@NonNull String query) {
    return database.rawQuery(query, null);
  }

  public void exec(@NonNull String sql) throws LocalIOException {
    try {
      open();
      database.execSQL(sql);
    } catch (SQLException e) {
      throw new LocalIOException(e.getMessage());
    } finally {
      close();
    }
  }

  public void execList(@NonNull String[] sqlList) throws Exception {
    try {
      open();
      database.beginTransaction();
      for (String sql : sqlList) {
        database.execSQL(sql);
      }
      database.setTransactionSuccessful();
    } catch (Exception e) {
      throw new LocalIOException(e.getMessage());
    } finally {
      database.endTransaction();
      close();
    }
  }
}
