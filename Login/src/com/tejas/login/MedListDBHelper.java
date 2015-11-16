package com.tejas.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by u0923408 on 11/4/2015.
 */
public class MedListDBHelper extends SQLiteOpenHelper {
    //If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MedicationsInfo.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MedListContract.MedList.TABLE_NAME + " (" +
                    MedListContract.MedList._ID + " INTEGER PRIMARY KEY," +
                    MedListContract.MedList.COLUMN_MED_NAME + TEXT_TYPE + COMMA_SEP +
                    MedListContract.MedList.COLUMN_AMOUNT + " INTEGER," +
                    MedListContract.MedList.COLUMN_UNIT + TEXT_TYPE + COMMA_SEP +
                    MedListContract.MedList.COLUMN_ROUTE + TEXT_TYPE + COMMA_SEP +
                    MedListContract.MedList.COLUMN_FREQUENCY + TEXT_TYPE + COMMA_SEP +
                    MedListContract.MedList.COLUMN_INSTRUCTIONS + TEXT_TYPE + COMMA_SEP +
                    MedListContract.MedList.COLUMN_UPDATED + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MedListContract.MedList.TABLE_NAME;

    public MedListDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
