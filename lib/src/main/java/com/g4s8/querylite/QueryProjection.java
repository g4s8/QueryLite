package com.g4s8.querylite;

import android.database.Cursor;
import android.support.annotation.NonNull;
import java.util.LinkedList;
import java.util.List;

/**
 * Query for projection.
 *
 * @since 1.0
 */
final class QueryProjection implements Query {

    private final Projection projection;
    private final List<String> order = new LinkedList<>();
    private final List<Long> limits = new LinkedList<>();
    private final List<WhereArg> filters = new LinkedList<>();

    QueryProjection(final Projection projection) {
        this.projection = projection;
    }

    @Override
    @NonNull
    public Cursor cursor() {
        return projection.cursor(this.filters, this.order, limitMin());
    }

    @Override
    @NonNull
    public Query where(
        @NonNull final String expr,
        final Object... arg
    ) {
        this.filters.add(new WhereArg(expr, arg));
        return this;
    }

    @Override
    @NonNull
    public Query orderBy(@NonNull final String column) {
        this.order.add(column);
        return this;
    }

    @Override
    @NonNull
    public Query limit(final long limit) {
        this.limits.add(limit);
        return this;
    }

    private long limitMin() {
        if (this.limits.size() == 0) {
            return -1;
        }
        long min = Long.MAX_VALUE;
        for (Long lmt : this.limits) {
            min = Math.min(min, lmt);
        }
        return min;
    }
}
