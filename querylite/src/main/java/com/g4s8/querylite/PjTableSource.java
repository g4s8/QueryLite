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
    private final TableSource source;

    /**
     * Ctor.
     *
     * @param columns table columns
     * @param source  table source
     */
    PjTableSource(
        @NonNull final List<String> columns,
        @NonNull final TableSource source
    ) {
        this.columns = columns;
        this.source = source;
    }

    @Override
    @NonNull
    public Cursor cursor(
        @NonNull final List<WhereArg> where,
        @NonNull final List<String> order,
        final long limit
    ) {
        return this.source.cursor(
            this.columns,
            where,
            order,
            limit
        );
    }
}
