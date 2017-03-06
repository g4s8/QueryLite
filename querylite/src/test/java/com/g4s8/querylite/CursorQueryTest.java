package com.g4s8.querylite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
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
 * Robolectric test for cursor query {@link CursorQuery}.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public final class CursorQueryTest {

    private static final String TABLE = "persons";
    private static final String COL_NAME = "name";
    private static final String COL_ID = "id";

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
     * Test cursor for query.
     */
    @Test
    public void queryCursorTest() {
        final SQLiteDatabase db = this.sqlite.getWritableDatabase();
        final String name = "name";
        final ContentValues cv = new ContentValues(1);
        cv.put(COL_NAME, name);
        final long id = db.insert(TABLE, null, cv);
        final CursorQuery cursor = new CursorQuery(
            new Select(COL_NAME)
                .from(new SqliteTable(TABLE).in(db))
                .where("id = ?", id)
        );
        cursor.moveToFirst();
        MatcherAssert.assertThat(
            cursor.getString(0),
            Matchers.equalTo(name)
        );
    }
}
