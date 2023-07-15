package com.setiyo.projectakhirsetiyo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME="project.db";

    public DBHelper(Context context) { super(context,"project.db" , null, 1);}


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table majalah(judulmajalah TEXT primary key, jenis TEXT, bahasa TEXT, tahun TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists majalah");
    }
    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result ==1) return false;
        else
            return true;
    }
    //check username function
    public Boolean checkusername(String username){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    //check username password function
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean insertDatamajalah (String judulmajalah,String jenis, String bahasa, String tahun){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("judulmajalah",judulmajalah);
        values.put("jenis",jenis);
        values.put("bahasa", bahasa);
        values.put("tahun", tahun);
        long result = db.insert("majalah", null,values);
        if (result==-1) return false;
        else
            return true;
    }
    public Boolean checkmajalah (String judulmajalah){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from majalah where judulmajalah=?", new String[] {judulmajalah});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor tampilDatamajalah(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from majalah", null);
        return cursor;
    }

    public boolean hapusDatamajalah(String judulmajalah){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from majalah where judulmajalah=?", new String[]{judulmajalah});
        if (cursor.getCount()>0){
            long result = db.delete("majalah", "judulmajalah=?", new String[]{judulmajalah});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean editDatamajalah (String judulmajalah,String jenis, String bahasa, String tahun){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("judulmajalah",judulmajalah);
        values.put("jenis",jenis);
        values.put("bahasa", bahasa);
        values.put("tahun", tahun);

        Cursor cursor = db.rawQuery("Select * from majalah where judulmajalah=?", new String[]{judulmajalah});
        if (cursor.getCount()>0) {
            long result = db.update("majalah", values, "judulmajalah=?", new String[]{judulmajalah});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}