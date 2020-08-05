package com.example.database;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String FLASHCARDS_COLUMN_QUESTION = "question";
    public static final String FLASHCARDS_COLUMN_ANSWER = "answer";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table flashcards " +
                        "(id integer primary key, question text,answer text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS flashcards");
        onCreate(db);
    }

    public boolean insertFlashcard(String q, String a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", q);
        contentValues.put("answer", a);
        db.insert("flashcards", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from flashcards where id="+id+"", null );
        return res;
    }


    public boolean updateFlashcard(Integer id, String q, String a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", q);
        contentValues.put("answer", a);
        db.update("flashcards", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public ArrayList<String> getAllFlashcards() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from flashcards", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(FLASHCARDS_COLUMN_QUESTION)));
            res.moveToNext();
        }
        return array_list;
    }
}