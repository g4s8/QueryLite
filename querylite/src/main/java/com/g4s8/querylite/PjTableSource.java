package com.g4s8.querylite;

import android.database.Cursor;
import android.support.annotation.NonNull;
import java.util.List;

/**
 * Projection for table source.
 *
 * @since 1.0
 */
final class PjTableSource implements Projection {

    private final List<String> columns;
    private final Table table;

    /**
     * Ctor.
     *
     * @param columns table columns
     * @param table   table
     */
    PjTableSource(
        @NonNull final List<String> columns,
        @NonNull final Table table
    ) {
        this.columns = columns;
        this.table = table;
    }

    @Override
    @NonNull
    public Cursor cursor(
        @NonNull final List<WhereArg> where,
        @NonNull final List<String> order,
        final long limit
    ) {
        return this.table.cursor(
            this.columns,
            where,
            order,
            limit
        );
    }
}
