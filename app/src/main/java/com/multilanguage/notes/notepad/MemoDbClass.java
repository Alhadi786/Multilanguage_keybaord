package com.multilanguage.notes.notepad;

/**
 * Created by UDNA on 8/4/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.multilanguage.notes.notepad.model.MemoInfo;

import java.util.ArrayList;
import java.util.List;


public class MemoDbClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db;
    private  final String TAG = MemoDbClass.class.getSimpleName();
    // Database Name
    private static final String DATABASE_NAME = "MemoDatabase";
    private final String TABLE_NAME="MemoTable";
    public static final String ID = "ID";
    private final String MEMO_TEXT="Description";
    private final String MEMO_DATE="Date";
    private final String MEMO_TIME="Time";
    private final String MEMO_LANGUAGE="Language";



    public MemoDbClass(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MEMO_TEXT + " TEXT,"
                + MEMO_DATE + " TEXT,"
                + MEMO_TIME +" TEXT,"
                + MEMO_LANGUAGE +" INTEGER"
                + ")";


        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }



    public Boolean InsertMemo(String txt,String date,String time,String language) {
        Boolean is_inserted = false;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(MEMO_TEXT,txt);
            values.put(MEMO_DATE,date);
            values.put(MEMO_TIME,time);
            values.put(MEMO_LANGUAGE,language);
            db.insertOrThrow(TABLE_NAME, null, values);
            db.close();
            is_inserted = true;
        } catch (SQLiteConstraintException e) {
            Log.e(TAG, "Error: " + e);
            is_inserted = false;
        }
        return is_inserted;
    }

    public List<MemoInfo> getAllDBMemo() {
        List<MemoInfo> memoInfoList = new ArrayList<MemoInfo>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                MemoInfo memoInfo = new MemoInfo();
                memoInfo.setMemo_id(cursor.getInt(0));
                memoInfo.setMemo_text(cursor.getString(1));
                memoInfo.setDate(cursor.getString(2));
                memoInfo.setMemo_time(cursor.getString(3));
                memoInfo.setMemo_language(cursor.getInt(4));

                memoInfoList.add(memoInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return memoInfoList;
    }

    public String getSingleMemo(int user_id) {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                ID + " = '" + user_id + "'";
        db = this.getReadableDatabase();

        Cursor cursor=null;
        String memo_text = null;


            cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                memo_text = cursor.getString(cursor.getColumnIndex(MEMO_TEXT));
            }
        Log.d(TAG, "getSingleMemo: "+user_id+" text"+memo_text);
        cursor.close();
        db.close();
            return memo_text;




    }
    public boolean updateMemo(int position,String txt){
        boolean updated=false;
        db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d(TAG, "before_update : "+position+" "+txt);
        try {
            values.put(MEMO_TEXT,txt);
           // db.update(TABLE_NAME, values,ID+"="+position,null);
            db.update(TABLE_NAME,values,ID + "=?",new String[]{String.valueOf(position+1)} );
            db.close();
            updated = true;
            Log.i(TAG, "updateMemo: "+"position "+txt+"posi "+position);
        } catch (SQLiteConstraintException e) {
            Log.e(TAG, "Error: " + e);
            updated = false;
        }
        return updated;


    }

    public boolean Delete(int id){
        boolean deleted=false;
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
        deleted=true;
        return  deleted;
    }

    public  boolean DeleteAll(){
        boolean deleted = false;
        String deletequery="delete from "+TABLE_NAME;
        db=this.getWritableDatabase();
        try {
            db.execSQL(deletequery);
            db.close();
            deleted = true;
        }catch (SQLiteConstraintException e){
            e.printStackTrace();
        }

        return  deleted;
    }
}
