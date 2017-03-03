package com.g4s8.querylite;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * Database table.
 *
 * @since 1.0
 */
public interface Table {

    /**
     * Table source in sqlite database.
     *
     * @param db database
     * @return table source
     */
    @NonNull
    @SuppressWarnings("PMD.ShortMethodName")
    TableSource in(@NonNull final SQLiteDatabase db);

    /**
     * Default decorator.
     */
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
        @SuppressWarnings("PMD.ShortMethodName")
        public final TableSource in(@NonNull final SQLiteDatabase db) {
            return origin.in(db);
        }
    }
}
