package com.example.jose.newsapp.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import static com.example.jose.newsapp.utilities.Contract.TABLE_ARTICLES.*;


public class DatabaseUtils {

    //grabs all the available items in the database using the cursor object and making accessible
    public static Cursor getAll(SQLiteDatabase db){
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null, COLUMN_NAME_PUBLISHED_DATE + " DESC");
        return cursor;
    }

    //making a record insertion grabbing the database and taking in an array list of articles
    public static void insert(SQLiteDatabase db, ArrayList<Article> articles){

        db.beginTransaction();

        try {
            for (Article a : articles){
                ContentValues value = new ContentValues();
                value.put(COLUMN_NAME_TITLE, a.getTitle());
                value.put(COLUMN_NAME_ABSTRACT, a.getAbstr());
                value.put(COLUMN_NAME_PUBLISHED_DATE, a.getPublished_date());
                value.put(COLUMN_NAME_THUMBURL, a.getThumbURL());
                value.put(COLUMN_NAME_URL, a.getURL());
                db.insert(TABLE_NAME, null, value);
            }
            db.setTransactionSuccessful();
        }finally {
                db.endTransaction();
                db.close();
        }
    }

    //deletes all records drops table in other words
    public static void deleteAll(SQLiteDatabase db){
        db.delete(TABLE_NAME, null, null);
    }
}
