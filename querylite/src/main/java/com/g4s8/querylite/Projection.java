package com.g4s8.querylite;

import android.database.Cursor;
import android.support.annotation.NonNull;
import java.util.List;

/**
 * Database table projection for {@link Cursor}.
 *
 * @since 1.0
 */
interface Projection {

    /**
     * Project table into cursor with provided arguments.
     *
     * @param where where filters
     * @param order order by's
     * @param limit limit
     * @return android {@link Cursor}
     */
    @NonNull
    Cursor cursor(
        @NonNull final List<WhereArg> where,
        @NonNull final List<String> order,
        final long limit
    );
}
