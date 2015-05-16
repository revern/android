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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SetPatternActivity extends ActionBarActivity {
    public static final String EXTRA_SET_PATTERN_DAY = "EXTRA SET PATTERN DAY";
    public static final String EXTRA_SET_PATTERN_T = "EXTRA SET PATTERN T";
    public static final String EXTRA_SET_PATTERN_P = "EXTRA SET PATTERN P";
    public static final String EXTRA_SET_PATTERN_C = "EXTRA SET PATTERN C";
    DbSchedule dbHelper;
    SQLiteDatabase db;
    ListView mPatternList;
    ArrayAdapter<String> adapter;
    ArrayList<String> patterns = new ArrayList<>();
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pattern);
        dbHelper = new DbSchedule(this);
        db = dbHelper.getWritableDatabase();
        mPatternList = (ListView)findViewById(R.id.set_pattern_list);
        updatePatterns();
        updateList();
        mPatternList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final Cursor c;
//                c = db.query("patterns", null, null, null, null, null, null);
//                c.moveToFirst();
//                c.move(position);
//                AlertDialog.Builder builder = new AlertDialog.Builder(SetPatternActivity.this);
//                builder.setTitle("Set day");
//                final DatePicker input = new DatePicker(SetPatternActivity.this);
//                builder.setView(input);
//                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent data = new Intent();
//                        String day = getDate(input.getYear(), input.getMonth(), input.getDayOfMonth());
//
//                        data.putExtra(EXTRA_SET_PATTERN_T, c.getString(0));
//                        data.putExtra(EXTRA_SET_PATTERN_P, c.getString(1));
//                        data.putExtra(EXTRA_SET_PATTERN_C, c.getInt(2));
//                        data.putExtra(EXTRA_SET_PATTERN_DAY, day);
//                        setResult(RESULT_OK, data);
//                        finish();
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
                pos=position;

                Intent i = new Intent(SetPatternActivity.this, ChooseDayActivity.class);
                i.putExtra(ChooseDayActivity.EXTRA_DAY, pos);
                startActivityForResult(i, 0);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent data1 = new Intent();
        setResult(RESULT_OK, data1);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_pattern, menu);
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
