package com.g4s8.querylite;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Fluent query for sqlite.
 *
 * @since 1.0
 */
public interface Query {

    /**
     * Cursor for this query.
     *
     * @return android sqlite cursor.
     */
    @NonNull
    Cursor cursor();

    /**
     * Add where clause.
     *
     * @param expr expression
     * @param arg  expression arguments
     * @return updated query
     */
    @NonNull
    Query where(@NonNull final String expr, final Object... arg);

    /**
     * Order results by column.
     *
     * @param column order column
     * @return updated query
     */
    @NonNull
    Query orderBy(@NonNull final String column);

    /**
     * Limit results.
     *
     * @param limit value
     * @return updated query
     */
    @NonNull
    Query limit(final long limit);

    /**
     * Base {@link Query} decorator.
     */
    @SuppressWarnings("unused")
    abstract class Wrap implements Query {

        private final Query origin;

        protected Wrap(@NonNull final Query origin) {
            this.origin = origin;
        }

        @Override
        @NonNull
        public final Cursor cursor() {
            return origin.cursor();
        }

        @Override
        @NonNull
        public final Query where(@NonNull final String expr, final Object... arg) {
            return origin.where(expr, arg);
        }

        @Override
        @NonNull
        public final Query orderBy(@NonNull final String column) {
            return origin.orderBy(column);
        }

        @Override
        @NonNull
        public final Query limit(final long limit) {
            return origin.limit(limit);
        }
    }
}
