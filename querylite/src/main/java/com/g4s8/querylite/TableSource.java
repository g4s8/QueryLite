package com.g4s8.querylite;

import android.database.Cursor;
import android.support.annotation.NonNull;
import java.util.List;

/**
 * A table source.
 * <p>
 * It's a bridge to android sqlite api.
 *
 * @since 1.0
 */
public interface TableSource {

    /**
     * Cursor from this source.
     *
     * @param columns columns to select
     * @param where   where clause
     * @param orderBy ordering
     * @param limit   select result
     * @return android cursor
     */
    @NonNull
    Cursor cursor(
        @NonNull final List<String> columns,
        @NonNull final List<WhereArg> where,
        @NonNull final List<String> orderBy,
        final long limit
    );
}
