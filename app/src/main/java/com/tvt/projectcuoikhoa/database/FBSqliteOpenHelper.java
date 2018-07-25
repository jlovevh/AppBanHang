package com.tvt.projectcuoikhoa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tvt.projectcuoikhoa.model.User;
import com.tvt.projectcuoikhoa.model.UserFB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FBSqliteOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "facebook";
    public static final String TABLE_NAME = "fb";
    public static final String ID = "id";
    public static final String FB_ID = "fb_id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String URL = "url";


    public FBSqliteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + FB_ID + " TEXT ," + NAME + " TEXT," + EMAIL + " TEXT," + URL + " TEXT)  ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + " ";
        db.execSQL(sql);
    }

    public void insert(UserFB userFB) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(FB_ID, userFB.getFb_id());
        values.put(NAME, userFB.getName());
        values.put(EMAIL, userFB.getEmail());
        values.put(URL, userFB.getUrl());
        db.insert(TABLE_NAME, null, values);

    }

    public List<UserFB> getFBUser() {

        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + "";
        Cursor cursor = db.rawQuery(sql, null);

        List<UserFB> arr = null;
        if (cursor != null && cursor.moveToFirst()) {


            do {
                arr = new ArrayList<>();
                UserFB userFB = new UserFB();
                userFB.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                userFB.setFb_id(cursor.getString(cursor.getColumnIndex(FB_ID)));
                userFB.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                userFB.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                userFB.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
                arr.add(userFB);
            } while (cursor.moveToNext());


        }

        db.close();

        return arr;

    }

    public void deleteTable(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});

    }
}
