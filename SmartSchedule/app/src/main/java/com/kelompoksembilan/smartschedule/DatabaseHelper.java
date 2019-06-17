package com.kelompoksembilan.smartschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "schedule.db";
    public static final String TABLE_NAME = "schedule_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "TITLE";
    public static final String COL3 = "PLACE";
    public static final String COL4 = "DATE";
    public static final String COL5 = "TIME";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_NAME +
                        "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, "+ COL5 + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean CardAdd(String title, String place, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,title);
        contentValues.put(COL3,place);
        contentValues.put(COL4,date);
        contentValues.put(COL5,time);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor showData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public boolean CardEdit(String title, String place, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL1,id);
        contentValues.put(COL2,title);
        contentValues.put(COL3,place);
        contentValues.put(COL4,date);
        contentValues.put(COL5,time);
        db.update(TABLE_NAME, contentValues, COL2 + " = ?",
                new String[]{String.valueOf(DatabaseHelper.Schedule.getID())} );
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public static class Schedule{
        private static int ID;
        private String TITLE;
        private String DATE;
        private String TIME;
        private String PLACE;

        public Schedule(){
            this.ID = ID;
            this.TITLE = TITLE;
            this.DATE = DATE;
            this.TIME = TIME;
            this.PLACE = PLACE;
        }

        public static int getID(){
            return ID;
        }
        public void setID(int ID){
            this.ID = ID;
        }
        public String getTITLE(){
            return TITLE;
        }
        public void setTITLE(String TITLE){
            this.TITLE = TITLE;
        }
        public String getDATE(){
            return DATE;
        }
        public void setDATE(String DATE){
            this.DATE = DATE;
        }
        public String getTIME(){
            return TIME;
        }
        public void setTIME(String TIME){
            this.TIME = TIME;
        }
        public String getPLACE(){
            return PLACE;
        }
        public void setPLACE(String PLACE){
            this.PLACE = PLACE;
        }
    }

    public List<Schedule> getAllNotes() {
        List<Schedule> schedules = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                //schedule.setID(cursor.getInt(cursor.getColumnIndex(COL1)));
                schedule.setTITLE(cursor.getString(cursor.getColumnIndex(COL2)));
                schedule.setDATE(cursor.getString(cursor.getColumnIndex(COL3)));
                schedule.setTIME(cursor.getString(cursor.getColumnIndex(COL4)));
                schedule.setPLACE(cursor.getString(cursor.getColumnIndex(COL5)));

                schedules.add(schedule);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return schedules;
    }
}
