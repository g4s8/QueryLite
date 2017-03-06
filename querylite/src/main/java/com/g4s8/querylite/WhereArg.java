package com.g4s8.querylite;

import android.support.annotation.NonNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Argument for where clause.
 *
 * @since 1.0
 */
final class WhereArg {

    private final String expression;
    private final Collection<String> arguments;

    WhereArg(
        @NonNull final String expression,
        final Object... arguments
    ) {
        this(
            expression,
            stringArgs(Arrays.asList(arguments))
        );
    }

    WhereArg(
        @NonNull final String expression,
        @NonNull final Collection<String> arguments
    ) {
        this.expression = expression;
        this.arguments = arguments;
    }

    @NonNull
    String expression() {
        return this.expression;
    }

    @NonNull
    String[] arguments() {
        return this.arguments.toArray(new String[0]);
    }

    private static Collection<String> stringArgs(Collection<Object> args) {
        List<String> res = new LinkedList<>();
        for (Object arg : args) {
            res.add(arg.toString());
        }
        return res;
    }
}
