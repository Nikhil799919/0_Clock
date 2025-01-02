package com.example.a0_oclock;

import static android.content.Intent.getIntent;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Hero.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "bday";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name_title";
    private static final String COLUMN_DOB = "dob_title";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DOB + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(String name , String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_DOB,dob);

        long result = db.insert(TABLE_NAME,null,cv);
        Log.d("Nikhil", "Insert result: " + result);

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
          if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor getDOB(){

        String query2 = "SELECT * FROM " + TABLE_NAME;

        //String query2 = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " ==2";

        //String query2 = "SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " ==1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query2,null);
        }
        return cursor;
    }

    void updateData(String row_id,String name , String dob ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_NAME , name);
        cv.put(COLUMN_DOB, dob);

        long result = db.update(TABLE_NAME , cv , "_id=?" , new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update." , Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    void  deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?" , new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete." , Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
