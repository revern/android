package com.example.revernschedule;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MenuActivity extends ActionBarActivity {
    Button mTodayButton;
    Button mCalendarButton;
    Button mScheduleButton;
    DbSchedule dbHelper;
    SQLiteDatabase db;
    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dbHelper = new DbSchedule(this);
        db = dbHelper.getWritableDatabase();
        mTodayButton = (Button)findViewById(R.id.today_button);
        mCalendarButton = (Button)findViewById(R.id.calendar_button);
        mScheduleButton = (Button)findViewById(R.id.schedule_button);
        mTodayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, DayViewActivity.class);
                Date date = new Date();
                Cursor c = db.query("calendar", null, null, null, null, null, null);
                c.moveToFirst();
                do{
                    if(c.getString(0).equals(formatter.format(date))){
                        break;
                    }
                }while(c.moveToNext());
                i.putExtra(DayViewActivity.EXTRA_DAY_VIEW, c.getString(2));
                i.putExtra(DayViewActivity.EXTRA_DAY_NOTE, c.getString(4));
                startActivity(i);
            }
        });
        mCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, CalendarActivity.class);
                startActivity(i);
            }
        });
        mScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, ScheduleActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
