package com.g4s8.querylite;

import android.support.annotation.NonNull;
import java.util.Arrays;
import java.util.List;

/**
 * A select from the database.
 *
 * @since 1.0
 */
public final class Select {

    private final List<String> columns;

    /**
     * Ctor.
     *
     * @param columns columns to select.
     */
    public Select(final String... columns) {
        this(Arrays.asList(columns));
    }

    /**
     * Ctor.
     *
     * @param columns columns to select.
     */
    public Select(@NonNull final List<String> columns) {
        this.columns = columns;
    }

    /**
     * Make query for table source.
     *
     * @param table A table to select from
     * @return new query
     */
    @NonNull
    public Query from(@NonNull final Table table) {
        return new QueryProjection(
            new PjTableSource(
                this.columns,
                table
            )
        );
    }
}
