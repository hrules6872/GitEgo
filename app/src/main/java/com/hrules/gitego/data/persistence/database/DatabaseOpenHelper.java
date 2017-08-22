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

package com.hrules.gitego.data.persistence.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

class DatabaseOpenHelper extends SQLiteOpenHelper {
  DatabaseOpenHelper(@NonNull Context context) {
    super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
  }

  @Override public void onCreate(@NonNull SQLiteDatabase db) {
    db.execSQL(DatabaseConstants.SQL_CREATE_TABLE_USER);
    db.execSQL(DatabaseConstants.SQL_CREATE_TABLE_REPO);
  }

  @Override public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion == 1 && newVersion == 2) {
      db.beginTransaction();
      try {
        db.execSQL(DatabaseConstants.SQL_CREATE_TABLE_REPO_BACKUP_V1);
        db.execSQL("INSERT INTO " + DatabaseConstants.TABLE_REPO_BACKUP + " SELECT * FROM " + DatabaseConstants.TABLE_REPO + ";");
        db.execSQL("DROP TABLE " + DatabaseConstants.TABLE_REPO + ";");
        db.execSQL(DatabaseConstants.SQL_CREATE_TABLE_REPO);
        db.execSQL("INSERT INTO " + DatabaseConstants.TABLE_REPO + " SELECT * FROM " + DatabaseConstants.TABLE_REPO_BACKUP + ";");
        db.execSQL("DROP TABLE " + DatabaseConstants.TABLE_REPO_BACKUP + ";");
        db.setTransactionSuccessful();
      } finally {
        db.endTransaction();
      }
    }
  }
}