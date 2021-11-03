package com.cs477.project2_areyes24;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "workout_list";
    public static final String COL_NAME = "name";
    public static final String COL_REPS = "reps";
    public static final String COL_SETS = "sets";
    public static final String COL_WEIGHT = "weight";
    public static final String COL_NOTES = "notes";
    public static final int DB_Version = 1;
    private final Context context;

    private static final String CREATE_CMD =
            "CREATE TABLE " + TABLE_NAME + " ("
            + "_id " + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_REPS + " INTEGER, "
            + COL_SETS + " INTEGER, "
            + COL_WEIGHT + " INTEGER, "
            + COL_NOTES + " TEXT )";

    // constructor
    public DatabaseOpenHelper(Context context){
        super(context, "workout_list_db", null, 1);
        this.context = context;
    }

    // On create runs once the app is first started.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the database table
        sqLiteDatabase.execSQL(CREATE_CMD);

        // Load initial values into the database.
        ContentValues cv = new ContentValues(3);
        cv.put(COL_NAME, "Sit Ups");
        cv.put(COL_REPS, 10);
        cv.put(COL_SETS, 4);
        cv.put(COL_NOTES, "Good form");
        sqLiteDatabase.insert(TABLE_NAME, null, cv);
        cv.clear();
        cv.put(COL_NAME, "Push Ups");
        cv.put(COL_REPS, 10);
        cv.put(COL_SETS, 4);
        cv.put(COL_NOTES, "Good form");
        sqLiteDatabase.insert(TABLE_NAME, null, cv);
        cv.clear();
        cv.put(COL_NAME, "Pull Ups");
        cv.put(COL_REPS, 10);
        cv.put(COL_SETS, 4);
        cv.put(COL_NOTES, "Good form");
        sqLiteDatabase.insert(TABLE_NAME, null, cv);
        cv.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
