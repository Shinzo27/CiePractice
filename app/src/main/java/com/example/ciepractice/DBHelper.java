package com.example.ciepractice;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "CieDb",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table User(username text,password text)");

        db.execSQL("create table leave(username text, description text, fromdate text, todate text, time text, status text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists User");
        db.execSQL("drop Table if exists leave");
    }

    public boolean insert(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("password",password);
        long result = db.insert("User",null,cv);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkDetail(String username,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from User where username=? and password=?",new String[]{username,password});
        int i = cursor.getCount();
        if(i <= 0){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateLeave(String username, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        Cursor cursor = db.rawQuery("select * from leave where username=?",new String[]{username});
        if(cursor.getCount() > 0) {
            long result = db.update("leave", contentValues, "username=?", new String[]{username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public boolean leave(String username,String desc, String fromdate, String todate, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("description",desc);
        cv.put("fromdate",fromdate);
        cv.put("todate",todate);
        cv.put("time",time);
        cv.put("status","Not Approved");
        long result = db.insert("leave",null,cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUsers(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT username,description,fromdate,todate,time,status FROM leave WHERE username=?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("desc",cursor.getString(cursor.getColumnIndex("description")));
            user.put("fromdate",cursor.getString(cursor.getColumnIndex("fromdate")));
            user.put("todate",cursor.getString(cursor.getColumnIndex("todate")));
            user.put("time",cursor.getString(cursor.getColumnIndex("time")));
            user.put("status",cursor.getString(cursor.getColumnIndex("status")));
            userList.add(user);
        }
        return  userList;
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT username,description,fromdate,todate,time,status FROM leave";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("username",cursor.getString(cursor.getColumnIndex("username")));
            user.put("desc",cursor.getString(cursor.getColumnIndex("description")));
            user.put("fromdate",cursor.getString(cursor.getColumnIndex("fromdate")));
            user.put("todate",cursor.getString(cursor.getColumnIndex("todate")));
            user.put("time",cursor.getString(cursor.getColumnIndex("time")));
            user.put("status",cursor.getString(cursor.getColumnIndex("status")));
            userList.add(user);
        }
        return  userList;
    }
}
