# QueryLite

[![Build Status](https://img.shields.io/travis/g4s8/QueryLite.svg?style=flat-square)](https://travis-ci.org/g4s8/QueryLite)
[![License](https://img.shields.io/github/license/g4s8/QueryLite.svg?style=flat-square)](https://github.com/g4s8/QueryLite/blob/master/LICENSE)
[![Test Coverage](https://img.shields.io/codecov/c/github/g4s8/QueryLite.svg?style=flat-square)](https://codecov.io/github/g4s8/QueryLite?branch=master)

QueryLite is a fluent query API for android sqlite database.

## Classes

### Table, TableSqlite and Table.Wrap
`Table` represents a database table. `Table` used by `Select.from(table)` to create new `Query`. `TableSqlite` - sqlite table (takes android `SQLiteDatabase` as argument),
`Table.Wrap` - default `Table` decorator. `TableSqlite` can be wrapped with `Table.Wrap`:
```java
public final class CarTable extends Table.Wrap {
  
  /**
   * Table name.
   */
  private static final String NAME = "cars";
  
  public CarTable(final SQLiteDatabase database) {
    super(
      new TableSqlite(
        CarTable.NAME,
        database
      )
    );
  }
}
```

### Select
`Select` shoud specify columns to select from `Table`, `Select.from` can be wrapped with `Query.Wrap` decorator:
```java
public final class CarEngineQuery extends Query.Wrap {
  
  public CarEngineQuery(final SQLiteDatabase database) {
    super(
      new Select("engine")
        .from(new CarTable(database))
    );
  }
}
```

### Query and Query.Wrap
`Query` - fluent sqlite query that supports `where`, `orderBy` and `limit` yet. (`distinct`, `having` and `groupBy` will be supported later).


`where` - produces [sqlite WHERE clause](https://www.sqlite.org/lang_select.html#whereclause). 
> If a WHERE clause is specified, the WHERE expression is evaluated for each row in the input data as a boolean expression. Only rows for which the WHERE clause expression evaluates to true are included from the dataset before continuing. Rows are excluded from the result if the WHERE clause evaluates to either false or NULL. 

Syntax is similar to android [SQLiteDatabase.query()](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#query%28boolean,%20java.lang.String,%20java.lang.String[],%20java.lang.String,%20java.lang.String[],%20java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String%29) 'where' parameters.

_Example:_

where id = 42: `query.where("id = ?", 42)`

where name is 'David' and year greater than 1950: `query.where("name = ? AND year > ?", "David", 1950)`

`orderBy` - produces [sqlite ORDER BY clause](https://www.sqlite.org/lang_select.html#orderby).
> If a SELECT statement that returns more than one row does not have an ORDER BY clause, the order in which the rows are returned is undefined. Or, if a SELECT statement does have an ORDER BY clause, then the list of expressions attached to the ORDER BY determine the order in which rows are returned to the user. 

_Example:_

order by name: `query.orderBy("name")`

`limit` - produces [sqlite LIMIT clause](https://www.sqlite.org/lang_select.html#limitoffset).
> The LIMIT clause is used to place an upper bound on the number of rows returned by the entire SELECT statement. 

`where` and `orderBy` are accumulating values, i.e. this query:
```java
query
  .where("id = ?", 42)
  .where("name = ?", "David")
```
is equal to this:
```java
query
  .where("id = ? AND name = ?", 42, "David")
```


### CursorQuery
It's a decorator for android [Cursor](https://developer.android.com/reference/android/database/Cursor.html), that take `Query` as argument:
```java
import android.database.CursorWrapper;

public final class OrderedCarEnginesCursor extends CursorWrapper {
  public OrderedCarEnginesCursor(final SQLiteDatabase database) {
    super(
      new CursorQuery(
        new CarEngineQuery(database)
          .orderBy("engine")
      )
    );
  }
}
```
