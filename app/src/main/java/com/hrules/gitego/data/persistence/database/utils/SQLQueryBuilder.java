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

package com.hrules.gitego.data.persistence.database.utils;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// +info: https://gist.github.com/hrules6872/a85edc751076258f2aa95d5070be461b

public class SQLQueryBuilder {
  private static final String STATEMENT_SELECT = "SELECT";
  private static final String STATEMENT_DISTINCT_SELECT = "SELECT DISTINCT";
  private static final String STATEMENT_UPDATE = "UPDATE";
  private static final String STATEMENT_INSERT_INTO = "INSERT INTO";
  private static final String STATEMENT_DELETE = "DELETE FROM";

  private static final String WHERE = "WHERE";
  private static final String FROM = "FROM";

  private static final String OPERATOR_OR = "OR";
  private static final String OPERATOR_AND = "AND";
  private static final String OPERATOR_LIKE = "LIKE";
  private static final String OPERATOR_GLOB = "GLOB";
  private static final String OPERATOR_BETWEEN = "BETWEEN";
  private static final String OPERATOR_NOT = "NOT";
  private static final String OPERATOR_EQUALS_TO = "=";
  private static final String OPERATOR_NOT_EQUALS_TO = "!=";
  private static final String OPERATOR_GREATER_OR_EQUALS_THAN = ">=";
  private static final String OPERATOR_LESS_OR_EQUALS_THAN = "<=";
  private static final String OPERATOR_GREATER_THAN = ">";
  private static final String OPERATOR_LESS_THAN = "<";
  private static final String OPERATOR_IS_NULL = "IS NULL";
  private static final String OPERATOR_IS_NOT_NULL = "IS NOT NULL";

  private static final String KEYWORD_ORDER_BY = "ORDER BY";
  private static final String KEYWORD_GROUP_BY = "GROUP BY";

  public static final String ALL = "*";
  private static final String COMMA = ",";
  private static final String WHITESPACE = " ";
  private static final String PARENTHESIS_OPEN = "(";
  private static final String PARENTHESIS_CLOSE = ")";
  public static final String NULL = "NULL";

  private static final String SET = "SET";
  private static final String VALUES = "VALUES";
  private static final String HAVING = "HAVING";

  private static final String COUNT = "COUNT";
  private static final String SUM = "SUM";
  private static final String AS = "AS";
  private static final String LIMIT = "LIMIT";
  private static final String OFFSET = "OFFSET";

  public static final String ASC = "ASC";
  public static final String DESC = "DESC";

  private final List<String> elements = new ArrayList<>();

  public String build() {
    StringBuilder stringBuilder = new StringBuilder();
    for (String element : elements) {
      if (COMMA.equals(element) || PARENTHESIS_CLOSE.equals(element)) {
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
      }
      stringBuilder.append(element);
      if (!PARENTHESIS_OPEN.equals(element)) {
        stringBuilder.append(WHITESPACE);
      }
    }
    return stringBuilder.toString().trim();
  }

  private String formatObj(Object value) {
    if (value instanceof String) {
      return "'" + value + "'";
    }
    return String.valueOf(value);
  }

  public SQLQueryBuilder select() {
    return select(ALL);
  }

  public SQLQueryBuilder select(@NonNull String... columns) {
    if (columns.length > 0) {
      elements.add(STATEMENT_SELECT);
      for (String column : columns) {
        elements.add(column);
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
    }
    return this;
  }

  public SQLQueryBuilder update(@NonNull String table) {
    elements.add(STATEMENT_UPDATE);
    elements.add(table);
    return this;
  }

  public SQLQueryBuilder insertInto(@NonNull String table) {
    elements.add(STATEMENT_INSERT_INTO);
    elements.add(table);
    return this;
  }

  public SQLQueryBuilder deleteFrom(@NonNull String table) {
    elements.add(STATEMENT_DELETE);
    elements.add(table);
    return this;
  }

  public SQLQueryBuilder selectDistinct(@NonNull String... columns) {
    if (columns.length > 0) {
      elements.add(STATEMENT_DISTINCT_SELECT);
      for (String column : columns) {
        elements.add(column);
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
    }
    return this;
  }

  public SQLQueryBuilder from(@NonNull String... tables) {
    if (tables.length > 0) {
      elements.add(FROM);
      for (String table : tables) {
        elements.add(table);
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
    }
    return this;
  }

  public SQLQueryBuilder where(@NonNull String where) {
    elements.add(WHERE);
    elements.add(where);
    return this;
  }

  public SQLQueryBuilder equalsTo(@NonNull Object value) {
    elements.add(OPERATOR_EQUALS_TO);
    elements.add(formatObj(value));
    return this;
  }

  public SQLQueryBuilder notEqualsTo(@NonNull Object value) {
    elements.add(OPERATOR_NOT_EQUALS_TO);
    elements.add(formatObj(value));
    return this;
  }

  public SQLQueryBuilder greaterOrEqualsThan(@NonNull Object value) {
    elements.add(OPERATOR_GREATER_OR_EQUALS_THAN);
    elements.add(formatObj(value));
    return this;
  }

  public SQLQueryBuilder greaterThan(@NonNull Object value) {
    elements.add(OPERATOR_GREATER_THAN);
    elements.add(formatObj(value));
    return this;
  }

  public SQLQueryBuilder lessOrEqualsThan(@NonNull Object value) {
    elements.add(OPERATOR_LESS_OR_EQUALS_THAN);
    elements.add(formatObj(value));
    return this;
  }

  public SQLQueryBuilder lessThan(@NonNull Object value) {
    elements.add(OPERATOR_LESS_THAN);
    elements.add(formatObj(value));
    return this;
  }

  public SQLQueryBuilder and() {
    elements.add(OPERATOR_AND);
    return this;
  }

  public SQLQueryBuilder and(@NonNull String and) {
    elements.add(OPERATOR_AND);
    elements.add(and);
    return this;
  }

  public SQLQueryBuilder or() {
    elements.add(OPERATOR_OR);
    return this;
  }

  public SQLQueryBuilder or(@NonNull String or) {
    elements.add(OPERATOR_OR);
    elements.add(or);
    return this;
  }

  public SQLQueryBuilder like(@NonNull String like) {
    elements.add(OPERATOR_LIKE);
    elements.add(formatObj(like));
    return this;
  }

  public SQLQueryBuilder glob(@NonNull String glob) {
    elements.add(OPERATOR_GLOB);
    elements.add(formatObj(glob));
    return this;
  }

  public SQLQueryBuilder between(@NonNull Object value1, @NonNull Object value2) {
    elements.add(OPERATOR_BETWEEN);
    elements.add(formatObj(value1));
    elements.add(OPERATOR_AND);
    elements.add(formatObj(value2));
    return this;
  }

  public SQLQueryBuilder not() {
    elements.add(OPERATOR_NOT);
    return this;
  }

  public SQLQueryBuilder isNull() {
    elements.add(OPERATOR_IS_NULL);
    return this;
  }

  public SQLQueryBuilder isNotNull() {
    elements.add(OPERATOR_IS_NOT_NULL);
    return this;
  }

  public SQLQueryBuilder orderBy(@NonNull String... orderBys) {
    if (orderBys.length > 0) {
      elements.add(KEYWORD_ORDER_BY);
      for (String orderBy : orderBys) {
        elements.add(orderBy);
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
    }
    return this;
  }

  public SQLQueryBuilder orderBy(@NonNull Map<String, String> map) {
    if (map.size() > 0) {
      elements.add(KEYWORD_ORDER_BY);
      for (Map.Entry<String, String> entry : map.entrySet()) {
        elements.add(entry.getKey());
        elements.add(entry.getValue());
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
    }
    return this;
  }

  public SQLQueryBuilder orderByAscending(@NonNull String... orderBys) {
    if (orderBys.length > 0) {
      elements.add(KEYWORD_ORDER_BY);
      for (String orderBy : orderBys) {
        elements.add(orderBy);
        elements.add(ASC);
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
    }
    return this;
  }

  public SQLQueryBuilder orderByDescending(@NonNull String... orderBys) {
    if (orderBys.length > 0) {
      elements.add(KEYWORD_ORDER_BY);
      for (String orderBy : orderBys) {
        elements.add(orderBy);
        elements.add(DESC);
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
    }
    return this;
  }

  public SQLQueryBuilder groupBy(@NonNull String... groupBys) {
    if (groupBys.length > 0) {
      elements.add(KEYWORD_GROUP_BY);
      for (String groupBy : groupBys) {
        elements.add(groupBy);
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
    }
    return this;
  }

  public SQLQueryBuilder set(@NonNull Map<String, Object> map) {
    if (map.size() > 0) {
      elements.add(SET);
      for (Map.Entry<String, Object> entry : map.entrySet()) {
        elements.add(entry.getKey());
        elements.add(OPERATOR_EQUALS_TO);
        elements.add(formatObj(entry.getValue()));
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
    }
    return this;
  }

  public SQLQueryBuilder values(@NonNull Map<String, Object> map) {
    if (map.size() > 0) {
      elements.add(PARENTHESIS_OPEN);
      for (Map.Entry<String, Object> entry : map.entrySet()) {
        elements.add(entry.getKey());
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
      elements.add(PARENTHESIS_CLOSE);

      elements.add(VALUES);

      elements.add(PARENTHESIS_OPEN);
      for (Map.Entry<String, Object> entry : map.entrySet()) {
        elements.add(formatObj(entry.getValue()));
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
      elements.add(PARENTHESIS_CLOSE);
    }
    return this;
  }

  public SQLQueryBuilder values(@NonNull Object... values) {
    if (values.length > 0) {
      elements.add(VALUES);

      elements.add(PARENTHESIS_OPEN);
      for (Object value : values) {
        elements.add(formatObj(value));
        elements.add(COMMA);
      }
      elements.remove(elements.size() - 1);
      elements.add(PARENTHESIS_CLOSE);
    }
    return this;
  }

  public SQLQueryBuilder statement(@NonNull Object whatever) {
    elements.add(formatObj(whatever));
    return this;
  }

  public SQLQueryBuilder count() {
    return count(ALL);
  }

  public SQLQueryBuilder count(@NonNull String expression) {
    elements.add(COUNT + PARENTHESIS_OPEN + expression + PARENTHESIS_CLOSE);
    return this;
  }

  public SQLQueryBuilder sum() {
    return sum(ALL);
  }

  public SQLQueryBuilder sum(@NonNull String expression) {
    elements.add(SUM + PARENTHESIS_OPEN + expression + PARENTHESIS_CLOSE);
    return this;
  }

  public SQLQueryBuilder as() {
    elements.add(AS);
    return this;
  }

  public SQLQueryBuilder as(@NonNull String as) {
    return as(as, true);
  }

  public SQLQueryBuilder as(@NonNull String as, boolean addQuotesAround) {
    elements.add(AS);
    elements.add(addQuotesAround ? "'" + as + "'" : as);
    return this;
  }

  public SQLQueryBuilder limit(int limit) {
    elements.add(LIMIT);
    elements.add(String.valueOf(limit));
    return this;
  }

  public SQLQueryBuilder offset(int offset) {
    elements.add(OFFSET);
    elements.add(String.valueOf(offset));
    return this;
  }

  public SQLQueryBuilder having() {
    elements.add(HAVING);
    return this;
  }
}