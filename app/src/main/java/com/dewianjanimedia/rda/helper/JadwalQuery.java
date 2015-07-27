package com.dewianjanimedia.rda.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dewianjanimedia.rda.model.JadwalAcara;

import java.util.List;

/**
 * Created by syamsul on 7/27/15.
 */
public class JadwalQuery {

    private JadwalDB dbHelper;
    private List<JadwalAcara> acara;
    private ContentValues cp;

    public JadwalQuery(JadwalDB dbHelper, List<JadwalAcara> acara){
        this.acara = acara;
        this.dbHelper = dbHelper;
        cp = new ContentValues();
    }

    public JadwalQuery(JadwalDB dbHelper){
        this(dbHelper, null);
    }

    public boolean insertAcara(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (JadwalAcara a : acara) {
            cp.put(JadwalAcara.NAMA, a.getProgramName());
            cp.put(JadwalAcara.JAM, a.getTime());
            cp.put(JadwalAcara.TIPE, a.getProgramType());
            if (db.insert(JadwalDB.PROGRAM_TABLE, null, cp) <= 0) {
                return false;
            }
        }
        return true;
    }

    public Cursor getAcara(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + JadwalDB.PROGRAM_TABLE, null);
    }

    public boolean isEmpty(String table){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + table,null);
        c.moveToFirst();

        return c.getCount() > 0;
    }
}
