package com.g4s8.querylite;

import android.database.CursorWrapper;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;

/**
 * Cursor decorator for {@link Query}.
 *
 * @since 1.0
 */
@Keep
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
