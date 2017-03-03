package com.g4s8.querylite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * In memory sqlite.
 */
public final class Sqlite extends SQLiteOpenHelper {

    private static final String IN_MEMORY = null;

    /**
     * Ctor.
     *
     * @param context android context
     */
    public Sqlite(final Context context) {
        super(context, IN_MEMORY, null, 1);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `persons` (`id` INTEGER PRIMARY KEY, `name` TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    }

    /**
     * Reset database: remove all data.
     */
    public void reset() {
        final SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("DELETE FROM `persons`;");
        } finally {
            db.close();
        }
    }
}
