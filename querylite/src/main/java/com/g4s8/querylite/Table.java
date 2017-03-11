package com.g4s8.querylite;

import android.database.Cursor;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import java.util.List;

/**
 * Database table.
 *
 * @since 1.0
 */
@Keep
public interface Table {

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

    /**
     * Default decorator.
     */
    @Keep
    abstract class Wrap implements Table {

        private final Table origin;

        /**
         * Ctor.
         *
         * @param origin decorating object.
         */
        public Wrap(final Table origin) {
            this.origin = origin;
        }

        @NonNull
        @Override
        public final Cursor cursor(
            @NonNull final List<String> columns,
            @NonNull final List<WhereArg> where,
            @NonNull final List<String> orderBy,
            final long limit
        ) {
            return origin.cursor(
                columns,
                where,
                orderBy,
                limit
            );
        }
    }
}
