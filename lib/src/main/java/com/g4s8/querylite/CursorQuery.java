package com.g4s8.querylite;

import android.database.CursorWrapper;
import android.support.annotation.NonNull;

/**
 * Cursor decorator for {@link Query}.
 *
 * @since 1.0
 */
public final class CursorQuery extends CursorWrapper {

    /**
     * Ctor.
     *
     * @param query query for cursor
     */
    public CursorQuery(
        @NonNull final Query query
    ) {
        super(
            query.cursor()
        );
    }
}
