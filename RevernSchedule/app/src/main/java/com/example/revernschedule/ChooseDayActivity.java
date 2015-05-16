package com.example.revernschedule;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ChooseDayActivity extends ActionBarActivity {

    public static final String EXTRA_DAY = "EXTRA DAY";

    int pos;
    TextView mOneDay;
    TextView mWeekDay;
    TextView mMonthDay;
    TextView mYearDay;
    TextView mEveryday;
    DbSchedule dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_day);
        pos = getIntent().getIntExtra(EXTRA_DAY, 0);

        mOneDay = (TextView)findViewById(R.id.one_day_button);
        mWeekDay = (TextView)findViewById(R.id.week_day_button);
        mMonthDay = (TextView)findViewById(R.id.month_day_button);
        mYearDay = (TextView)findViewById(R.id.year_day_button);
        mEveryday = (TextView)findViewById(R.id.every_day_button);

        dbHelper = new DbSchedule(this);
        db = dbHelper.getWritableDatabase();

        mOneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Cursor c;
                c = db.query("patterns", null, null, null, null, null, null);
                c.moveToFirst();
                c.move(pos);
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseDayActivity.this);
                builder.setTitle("Set day");
                final DatePicker input = new DatePicker(ChooseDayActivity.this);
//                input.setFirstDayOfWeek(2);
                builder.setView(input);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent();
                        ContentValues cv = new ContentValues();
                        String day = getDate(input.getYear(), input.getMonth(), input.getDayOfMonth());

                        cv.put("date", day);
                        cv.put("title", c.getString(0));
                        cv.put("pattern", c.getString(1));
                        cv.put("color", c.getInt(2));
                        db.update("calendar", cv, "date = ?", new String[]{day});
                        setResult(RESULT_OK, data);
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        mWeekDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                        ChooseDayActivity.this);
                builderSingle.setTitle("Select day of week");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        ChooseDayActivity.this,
                        android.R.layout.simple_list_item_1);
                arrayAdapter.add("Monday");
                arrayAdapter.add("Tuesday");
                arrayAdapter.add("Wednesday");
                arrayAdapter.add("Thursday");
                arrayAdapter.add("Friday");
                arrayAdapter.add("Saturday");
                arrayAdapter.add("Sunday");
                builderSingle.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builderSingle.setAdapter(arrayAdapter,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Cursor c;
                                Cursor allCal;
                                c = db.query("patterns", null, null, null, null, null, null);
                                allCal = db.query("calendar", null, null, null, null, null, null);
                                c.moveToFirst();
                                c.move(pos);
                                allCal.moveToFirst();
                                Intent data = new Intent();
                                ContentValues cv = new ContentValues();
                                allCal.moveToPosition(which);
                                do {
                                    cv.put("date", allCal.getString(0));
                                    cv.put("title", c.getString(0));
                                    cv.put("pattern", c.getString(1));
                                    cv.put("color", c.getInt(2));
                                    db.update("calendar", cv, "date = ?", new String[]{allCal.getString(0)});
                                    if(!allCal.moveToNext()){break;}
                                    if(!allCal.moveToNext()){break;}
                                    if(!allCal.moveToNext()){break;}
                                    if(!allCal.moveToNext()){break;}
                                    if(!allCal.moveToNext()){break;}
                                    if(!allCal.moveToNext()){break;}
                                }while(allCal.moveToNext());
                                setResult(RESULT_OK, data);
                                finish();
                            }
                        });
                builderSingle.show();
            }
        });
        mMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Cursor c;
                final Cursor allCal;
                c = db.query("patterns", null, null, null, null, null, null);
                allCal = db.query("calendar", null, null, null, null, null, null);
                c.moveToFirst();
                c.move(pos);
                allCal.moveToFirst();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseDayActivity.this);
                builder.setTitle("Set day");
                final NumberPicker input = new NumberPicker(ChooseDayActivity.this);
                input.setMinValue(1);
                input.setMaxValue(31);
                builder.setView(input);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent();
                        ContentValues cv = new ContentValues();
                        String dayOfMonth;
                        if(input.getValue()>=10) {
                            dayOfMonth = input.getValue()+"";
                        }else{
                            dayOfMonth = "0"+input.getValue();
                        }

                        do {
                            if(allCal.getString(0).substring(0,2).equals(dayOfMonth)) {
                                cv.put("date", allCal.getString(0));
                                cv.put("title", c.getString(0));
                                cv.put("pattern", c.getString(1));
                                cv.put("color", c.getInt(2));
                                db.update("calendar", cv, "date = ?", new String[]{allCal.getString(0)});
                            }
                        }while(allCal.moveToNext());
                        setResult(RESULT_OK, data);
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        mYearDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Cursor c;
//                final Cursor allCal;
//                c = db.query("patterns", null, null, null, null, null, null);
//                allCal = db.query("calendar", null, null, null, null, null, null);
//                c.moveToFirst();
//                c.move(pos);
//                allCal.moveToFirst();
//                AlertDialog.Builder builderMonth = new AlertDialog.Builder(ChooseDayActivity.this);
//                builderMonth.setTitle("Set month");
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                        ChooseDayActivity.this,
//                        android.R.layout.simple_list_item_1);
//                arrayAdapter.add("January");
//                arrayAdapter.add("February");
//                arrayAdapter.add("March");
//                arrayAdapter.add("April");
//                arrayAdapter.add("May");
//                arrayAdapter.add("June");
//                arrayAdapter.add("July");
//                arrayAdapter.add("August");
//                arrayAdapter.add("September");
//                arrayAdapter.add("October");
//                arrayAdapter.add("November");
//                arrayAdapter.add("December");
//                builderMonth.setNegativeButton("cancel",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builderMonth.setAdapter(arrayAdapter,
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                final String month = arrayAdapter.getItem(which);
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseDayActivity.this);
//                                builder.setTitle(month+". Set day");
//                                final NumberPicker input = new NumberPicker(ChooseDayActivity.this);
//                                input.setMinValue(1);
//                                input.setMaxValue(31);
//                                builder.setView(input);
//                                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent data = new Intent();
//                                        ContentValues cv = new ContentValues();
//                                        String dayOfMonth;
//                                        if(input.getValue()>=10) {
//                                            dayOfMonth = input.getValue()+"";
//                                        }else{
//                                            dayOfMonth = "0"+input.getValue();
//                                        }
//                                        do {
//                                            if(allCal.getString(0).substring(0,6).equals(dayOfMonth+" "+month.substring(0,3))) {
//                                                cv.put("date", allCal.getString(0));
//                                                cv.put("title", c.getString(0));
//                                                cv.put("pattern", c.getString(1));
//                                                cv.put("color", c.getInt(2));
//                                                db.update("calendar", cv, "date = ?", new String[]{allCal.getString(0)});
//                                            }
//                                        }while(allCal.moveToNext());
//                                        setResult(RESULT_OK, data);
//                                        finish();
//                                    }
//                                });
//                                AlertDialog alert = builder.create();
//                                alert.show();
//                            }
//                        });
//                builderMonth.show();
                final Cursor c;
                c = db.query("patterns", null, null, null, null, null, null);
                c.moveToFirst();
                c.move(pos);
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseDayActivity.this);
                builder.setTitle("Set day");
                final DatePicker input = new DatePicker(ChooseDayActivity.this);
//                input.setFirstDayOfWeek(2);
                builder.setView(input);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent();
                        ContentValues cv = new ContentValues();
                        int year = input.getYear();
                        int month = input.getMonth();
                        int day = input.getDayOfMonth();
                        String dayZ = getDate(year, month, day);
                         for(int i=0;i<15;i++){
                            cv.put("date", dayZ);
                            cv.put("title", c.getString(0));
                            cv.put("pattern", c.getString(1));
                            cv.put("color", c.getInt(2));
                            db.update("calendar", cv, "date = ?", new String[]{dayZ});
                            year++;
                            dayZ=getDate(year, month, day);
                        }
                        setResult(RESULT_OK, data);
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        mEveryday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Cursor c;
                Cursor allCal;
                c = db.query("patterns", null, null, null, null, null, null);
                allCal = db.query("calendar", null, null, null, null, null, null);
                c.moveToFirst();
                c.move(pos);
                allCal.moveToFirst();
                Intent data = new Intent();
                ContentValues cv = new ContentValues();
                do {
                    cv.put("date", allCal.getString(0));
                    cv.put("title", c.getString(0));
                    cv.put("pattern", c.getString(1));
                    cv.put("color", c.getInt(2));
                    db.update("calendar", cv, "date = ?", new String[]{allCal.getString(0)});
                }while(allCal.moveToNext());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    public String getDate(int year,int month, int day){
        Date date = new Date();
        date.setYear(year-1900);
        date.setMonth(month);
        date.setDate(day);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_day, menu);
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
