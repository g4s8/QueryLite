package com.g4s8.querylite;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * Sqlite database table.
 *
 * @since 1.0
 */
public final class SqliteTable implements Table {

    private final String name;

    /**
     * Ctor.
     *
     * @param name table name
     */
    public SqliteTable(@NonNull final String name) {
        this.name = name;
    }

    @NonNull
    @Override
    @SuppressWarnings("PMD.ShortMethodName")
    public TableSource in(@NonNull final SQLiteDatabase db) {
        return new TsSqlite(this.name, db);
    }
}
