package com.g4s8.querylite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Sqlite table source.
 *
 * @since 1.0
 */
@Keep
public final class TableSqlite implements Table {

    private final String table;
    private final SQLiteDatabase database;

    public TableSqlite(
        @NonNull final String table,
        @NonNull final SQLiteDatabase database
    ) {
        this.table = table;
        this.database = database;
    }

    @Override
    @NonNull
    public Cursor cursor(
        @NonNull final List<String> columns,
        @NonNull final List<WhereArg> where,
        @NonNull final List<String> orderBy,
        final long limit
    ) {
        final String whereString;
        final String[] whereArgs;
        if (where.size() > 0) {
            final StringBuilder whereBuilder = new StringBuilder();
            final List<String> args = new LinkedList<>();
            for (int i = 0; i < where.size(); i++) {
                whereBuilder.append(where.get(i).expression());
                if (i < where.size() - 1) {
                    whereBuilder.append(" AND ");
                }
                args.addAll(Arrays.asList(where.get(i).arguments()));
            }
            whereString = whereBuilder.toString();
            whereArgs = args.toArray(new String[0]);
        } else {
            whereString = null;
            whereArgs = null;
        }
        return this.database.query(
            this.table,
            columns.toArray(new String[0]),
            whereString,
            whereArgs,
            null,
            null,
            TableSqlite.orderBy(orderBy),
            TableSqlite.limit(limit)
        );
    }

    @Nullable
    private static String limit(final long source) {
        // `null` expected by sqlite lib value
        return source >= 0 ? Long.toString(source) : null;
    }

    @Nullable
    private static String orderBy(@NonNull final List<String> source) {
        // `null` expected by sqlite lib value
        if (source.size() > 0) {
            final StringBuilder orderBuilder = new StringBuilder();
            for (int i = 0; i < source.size(); i++) {
                orderBuilder.append(source.get(i));
                if (i < source.size() - 1) {
                    orderBuilder.append(", ");
                }
            }
            return orderBuilder.toString();
        } else {
            return null;
        }
    }
}
