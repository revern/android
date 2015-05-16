package com.example.revernschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by Алмаз on 17.03.2015.
 */
public class DbSchedule extends SQLiteOpenHelper {
    public DbSchedule(Context context) {
        super(context, "DbSchedule", null, 1);
    }
    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table patterns (title text primary key, pattern text, color integer);");
        db.execSQL("create table calendar (date text primary key, title text, pattern text, color integer, note text);");
        Date date = new Date();
        GregorianCalendar toWeekday = new GregorianCalendar();
        toWeekday.setGregorianChange(date);
        int weekday = toWeekday.get(Calendar.DAY_OF_WEEK);
        date.setTime(date.getTime()-((weekday-1)*1000*60*60*24));
        for(int i=0;i<2147;i++){
            date.setTime(date.getTime()+(1000*60*60*24));
            String formattedDate=formatter.format(date);
            ContentValues cv = new ContentValues();
            cv.put("date", formattedDate);
            db.insert("calendar", null, cv);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
