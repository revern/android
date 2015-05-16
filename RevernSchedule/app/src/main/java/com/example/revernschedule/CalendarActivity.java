package com.example.revernschedule;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@SuppressLint("SimpleDateFormat")
public class CalendarActivity extends FragmentActivity {
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    DbSchedule dbHelper;
    SQLiteDatabase db;
    int test;
    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dbHelper = new DbSchedule(this);
        db = dbHelper.getWritableDatabase();
        caldroidFragment = new CaldroidFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, 2);
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            caldroidFragment.setArguments(args);
        }

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Cursor c = db.query("calendar", null, null, null, null, null, null);
                c.moveToFirst();
                do{
                    if(c.getString(0).equals(formatter.format(date))){
                        break;
                    }
                }while(c.moveToNext());
                Intent i = new Intent(CalendarActivity.this, DayViewActivity.class);
                i.putExtra(DayViewActivity.EXTRA_DAY_VIEW, c.getString(2));
                i.putExtra(DayViewActivity.EXTRA_DAY_NOTE, c.getString(4));
                startActivity(i);
            }

            @Override
            public void onChangeMonth(int month, int year) {

            }

            @Override
            public void onLongClickDate(Date date, View view) {
                final Cursor c = db.query("calendar", null, null, null, null, null, null);
                c.moveToFirst();
                do{
                    if(c.getString(0).equals(formatter.format(date))){
                        break;
                    }
                }while(c.moveToNext());
                final ContentValues cv = new ContentValues();
                cv.put("date", c.getString(0));
                cv.put("title", c.getString(1));
                cv.put("pattern", c.getString(2));
                cv.put("color", c.getString(3));
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                        CalendarActivity.this);
                builderSingle.setTitle("Note");
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View promptsView = li.inflate(R.layout.list_item, null);
                builderSingle.setView(promptsView);
                final EditText et = (EditText) promptsView.findViewById(R.id.edit_text_example);
                builderSingle.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cv.put("note", et.getText().toString());
                        db.update("calendar", cv, "date = ?", new String[]{c.getString(0)});
                    }
                });
                builderSingle.show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        final TextView textView = (TextView) findViewById(R.id.textview);

        final Button customizeButton = (Button) findViewById(R.id.customize_button);

        // Customize the calendar
        customizeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalendarActivity.this, SetPatternActivity.class);
                startActivityForResult(i,0);
            }
        });

        final Bundle state = savedInstanceState;
        updateCalendar();
    }
    public void updateCalendar(){
        Cursor c = db.query("calendar", null, null, null, null, null, null);
        Calendar cal = Calendar.getInstance();
        c.moveToFirst();
        while(!formatter.format(cal.getTime()).equals(c.getString(0))){
            cal.setTimeInMillis(cal.getTimeInMillis()-(1000*60*60*24));
        }

        if(c.moveToFirst()) {
            do {
               if(c.getInt(3)!=0){
                   if (caldroidFragment != null) {

                       caldroidFragment.setBackgroundResourceForDate(c.getInt(3), cal.getTime());
                       caldroidFragment.setTextColorForDate(R.color.white, cal.getTime());
                   }
               }
                cal.setTimeInMillis(cal.getTimeInMillis()+(1000*60*60*24));
            } while (c.moveToNext());
        }
        caldroidFragment.refreshView();
     }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            updateCalendar();
            return;
        }
        updateCalendar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
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
