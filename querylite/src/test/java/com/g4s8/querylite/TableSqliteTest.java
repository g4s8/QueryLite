package com.g4s8.querylite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Robolectric test for sqlite table source {@link TableSqlite}.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public final class TableSqliteTest {

    private static final String COL_NAME = "name";
    private static final String COL_ID = "id";
    private static final String TABLE = "persons";

    private Sqlite sqlite;

    /**
     * Before test.
     */
    @Before
    public void setUp() {
        this.sqlite = new Sqlite(RuntimeEnvironment.application);
        this.sqlite.reset();
    }

    /**
     * After test.
     */
    @After
    public void tearDown() {
        this.sqlite.close();
    }

    /**
     * Test columns order.
     */
    @Test
    public void columnsTest() {
        final SQLiteDatabase db = this.sqlite.getWritableDatabase();
        try {
            db.insert(TABLE, null, values("Jimmy"));
            final Cursor cursor = new TableSqlite(TABLE, db).cursor(
                Arrays.asList(COL_NAME, COL_ID),
                Collections.<WhereArg>emptyList(),
                Collections.<String>emptyList(),
                -1
            );
            try {
                MatcherAssert.assertThat(
                    COL_NAME,
                    cursor.getColumnName(0),
                    Matchers.equalTo(COL_NAME)
                );
                MatcherAssert.assertThat(
                    COL_ID,
                    cursor.getColumnName(1),
                    Matchers.equalTo(COL_ID)
                );
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    /**
     * Test 'where' filter.
     */
    @Test
    public void whereTest() {
        final SQLiteDatabase db = this.sqlite.getWritableDatabase();
        final String john = "John";
        try {
            db.insert(TABLE, null, values(john));
            final Cursor cursor = new TableSqlite(TABLE, db).cursor(
                Collections.singletonList(COL_NAME),
                Collections.singletonList(new WhereArg("name = ?", john)),
                Collections.<String>emptyList(),
                -1
            );
            try {
                MatcherAssert.assertThat(
                    "count",
                    cursor.getCount(),
                    Matchers.is(1)
                );
                cursor.moveToFirst();
                MatcherAssert.assertThat(
                    "name",
                    cursor.getString(0),
                    Matchers.equalTo(john)
                );
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    /**
     * Test ordering.
     */
    @Test
    public void orderTest() {
        final SQLiteDatabase db = this.sqlite.getWritableDatabase();
        try {
            db.insert(TABLE, null, values("666"));
            db.insert(TABLE, null, values("444"));
            db.insert(TABLE, null, values("999"));
            db.insert(TABLE, null, values("111"));
            final Cursor cursor = new TableSqlite(TABLE, db).cursor(
                Collections.singletonList(COL_NAME),
                Collections.<WhereArg>emptyList(),
                Collections.singletonList("name"),
                -1
            );
            try {
                final List<String> expected = Arrays.asList("111", "444", "666", "999");
                for (int i = 0; i < expected.size(); i++) {
                    if (i == 0) {
                        cursor.moveToFirst();
                    } else {
                        cursor.moveToNext();
                    }
                    final String ex = expected.get(i);
                    MatcherAssert.assertThat(
                        String.format("expected: %s", ex),
                        cursor.getString(0),
                        Matchers.equalTo(ex)
                    );
                }
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    /**
     * Test limit.
     */
    @Test
    public void limit() {
        final SQLiteDatabase db = this.sqlite.getWritableDatabase();
        try {
            db.insert(TABLE, null, values("one"));
            db.insert(TABLE, null, values("two"));
            db.insert(TABLE, null, values("three"));
            db.insert(TABLE, null, values("four"));
            db.insert(TABLE, null, values("six"));
            db.insert(TABLE, null, values("five"));
            final Cursor cursor = new TableSqlite(TABLE, db).cursor(
                Collections.singletonList(COL_NAME),
                Collections.<WhereArg>emptyList(),
                Collections.<String>emptyList(),
                3
            );
            try {
                MatcherAssert.assertThat(
                    "count",
                    cursor.getCount(),
                    Matchers.is(3)
                );
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
    }

    private static ContentValues values(@NonNull final String name) {
        final ContentValues cv = new ContentValues(1);
        cv.put(COL_NAME, name);
        return cv;
    }
}
