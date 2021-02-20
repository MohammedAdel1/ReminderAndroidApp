package com.example.reminder.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class EventDatabase extends SQLiteOpenHelper {


    public static final String DB_NAME = "event_db";
    public static final int DB_VERSION =1 ;

    public static final String EVENT_TABLE_NAME = "event";
    public static final String EVENT_COLUMN_ID = "id";
    public static final String EVENT_COLUMN_TITLE = "title";
    public static final String EVENT_COLUMN_DATE = "date";
    public static final String EVENT_COLUMN_TIME = "time";
    public static final String EVENT_COLUMN_BUDGET = "budget";




    public EventDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " +EVENT_TABLE_NAME+ " ( " + EVENT_COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EVENT_COLUMN_TITLE + " TEXT, " +EVENT_COLUMN_DATE+ " TEXT, " + EVENT_COLUMN_TIME+ " TEXT, " + EVENT_COLUMN_BUDGET + " REAL) " );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+ EVENT_TABLE_NAME );
        onCreate(sqLiteDatabase);

    }

    public boolean addEvent (Event event){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_COLUMN_TITLE, event.getTitle());
        values.put(EVENT_COLUMN_DATE, event.getDate());
        values.put(EVENT_COLUMN_TIME,event.getTime());
        values.put(EVENT_COLUMN_BUDGET, event.getBudget());
          long result = db.insert(EVENT_TABLE_NAME, null, values);
        return result != -1;

    }// end addEvent ()

    public boolean updateEvent (Event event){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_COLUMN_TITLE, event.getTitle());
        values.put(EVENT_COLUMN_DATE, event.getDate());
        values.put(EVENT_COLUMN_TIME,event.getTime());
        values.put(EVENT_COLUMN_BUDGET, event.getBudget());
        String args [] = {String.valueOf(event.getId())};
        int result = db.update(EVENT_TABLE_NAME, values, EVENT_COLUMN_ID + " =?",args );
        return result!=0;

    }

    public long getEventCount (){

        SQLiteDatabase db = getReadableDatabase();
        return  DatabaseUtils.queryNumEntries(db, EVENT_COLUMN_ID);

    }

    public boolean deleteEvent (Event event){
        SQLiteDatabase db = getWritableDatabase();
        String args [] = {String.valueOf(event.getId())};
        int result = db.delete(EVENT_TABLE_NAME, EVENT_COLUMN_ID + "=?", args);
        return result > 0;
    }

    public ArrayList<Event> getAllEvent (){
        ArrayList<Event> events = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + EVENT_TABLE_NAME , null );
        if (cursor.moveToFirst()){

            do {
                int id = cursor.getInt(cursor.getColumnIndex(EVENT_COLUMN_ID));
                String title =cursor.getString(cursor.getColumnIndex(EVENT_COLUMN_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(EVENT_COLUMN_DATE));
                String time = cursor.getString(cursor.getColumnIndex(EVENT_COLUMN_TIME));
                float budget = cursor.getFloat(cursor.getColumnIndex(EVENT_COLUMN_BUDGET));
                    Event event = new Event(id, title, date,time,budget);
                    events.add(event);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return events;
    }// end getAllEvent ()


}// end EventDatabase class
