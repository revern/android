package com.example.revernschedule;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;


public class ScheduleActivity extends ActionBarActivity {

    ActionButton mCreateButton;
    ListView mPatternList;
    String dayName;
    String schedule;
    int patternColor;
    ArrayAdapter<String> adapter;
    ArrayList<String> patterns = new ArrayList<>();
    DbSchedule dbHelper;
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        dbHelper = new DbSchedule(this);
        db = dbHelper.getWritableDatabase();

        mCreateButton = (ActionButton)findViewById(R.id.create_button);
        mPatternList = (ListView)findViewById(R.id.pattern_list);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScheduleActivity.this, CreateActivity.class);
                i.putExtra(CreateActivity.EXTRA_CREATE_UPDATE, "creating from start");
                startActivityForResult(i, 0);
            }
        });
        mPatternList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c = db.query("patterns", null, null, null, null, null, null);
                Intent intent = new Intent(ScheduleActivity.this, CreateActivity.class);
                c.moveToFirst();
                c.move(position);
                intent.putExtra(CreateActivity.EXTRA_CREATE_UPDATE, c.getString(1));
                startActivityForResult(intent,0);
            }
        });
        mPatternList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder deleting = new AlertDialog.Builder(ScheduleActivity.this);
                deleting.setTitle("Pattern deleting").setMessage("Are you sure?");
                final int pos=position;
                deleting.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("patterns", "title = '" + patterns.get(pos) + "'", null);
                        updatePatterns();
                        updateList();
                    }
                });
                deleting.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog delete=deleting.create();
                delete.show();
                return true;
            }
        });
        try {
            updatePatterns();
            updateList();
        }catch (Exception e){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        ContentValues cv = new ContentValues();

        dayName = data.getStringExtra(CreateActivity.EXTRA_CREATE_D);
        patternColor=data.getIntExtra(CreateActivity.EXTRA_CREATE_C, 0);
        schedule = data.getStringExtra(CreateActivity.EXTRA_CREATE_S);
        cv.put("title", dayName);
        cv.put("pattern", schedule);
        cv.put("color", patternColor);

        db.insert("patterns", null, cv);
        db.update("patterns", cv, "title = ?", new String[]{dayName});

        Cursor c = db.query("calendar", null, null, null, null, null, null);
        c.moveToFirst();
        do{
            try {
                if (c.getString(1).equals(dayName)) {
                    ContentValues cv1 = new ContentValues();
                    cv1.put("date", c.getString(0));
                    cv1.put("title", dayName);
                    cv1.put("pattern", schedule);
                    cv1.put("color", patternColor);
                    db.update("calendar", cv1, "date = ?", new String[]{c.getString(0)});
                }
            }catch(Exception e){

            }
        }while(c.moveToNext());
        updatePatterns();
        updateList();
    }
    public void updatePatterns(){
        Cursor cus = db.query("patterns", null, null, null, null, null, null);

        patterns.clear();
        if(cus.moveToFirst()) {
            do {
                patterns.add(cus.getString(0));
            } while (cus.moveToNext());
        }
    }
    public void updateList(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patterns);
        mPatternList.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
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
