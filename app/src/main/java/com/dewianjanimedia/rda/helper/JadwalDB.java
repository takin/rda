package com.dewianjanimedia.rda.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dewianjanimedia.rda.model.JadwalAcara;

/**
 * Created by syamsul on 7/27/15.
 */
public class JadwalDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "jadwal.db";
    private static final int DB_VERSION = 1;

    public static final String PROGRAM_TABLE = "program";

    private static final String SQL_CREATE_PROGRAMS_TABLE = "CREATE TABLE " + PROGRAM_TABLE + "(" +
            JadwalAcara.ID + " INTEGER PRIMARY KEY AUTO_INCREMENT," +
            JadwalAcara.NAMA + " TEXT," +
            JadwalAcara.TIPE + " TEXT," +
            JadwalAcara.JAM + " TEXT)";

//    private static final String SQL_CREATE_WEEKLY_PROGRAMS_TABLE = "CREATE TABLE " + WEEKLY_PROGRAM_TABLE + "(" +
//            JadwalAcara.ID + " INTEGER PRIMARY KEY AUTO_INCREMENT," +
//            JadwalAcara.NAMA + "," +
//            JadwalAcara.JAM + "," +
//            JadwalAcara.HARI + ")";

    public JadwalDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_PROGRAMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PROGRAM_TABLE);

        onCreate(sqLiteDatabase);
    }
}
