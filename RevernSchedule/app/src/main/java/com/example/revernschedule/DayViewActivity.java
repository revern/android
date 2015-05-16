package com.example.revernschedule;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DayViewActivity extends ActionBarActivity {

    public static final String EXTRA_DAY_VIEW = "EXTRA DAY VIEW";
    public static final String EXTRA_DAY_NOTE = "EXTRA DAY NOTE";

    TextView mDayName;
    View mDayColorButton;
    ListView mDayList;
    DayPattern day;
    TextView mDayNoteView;
    final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    ArrayAdapter<String> adapter;
    ArrayList<String> actions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        mDayName = (TextView)findViewById(R.id.day_name_view);
        mDayColorButton = (View)findViewById(R.id.day_color_button);
        mDayNoteView = (TextView)findViewById(R.id.day_note_view);
        mDayList = (ListView)findViewById(R.id.day_action_list);
        String json=getIntent().getStringExtra(EXTRA_DAY_VIEW);
        String note = getIntent().getStringExtra(EXTRA_DAY_NOTE);
        mDayName.setText("Not attached");
        if(json!=null){
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actions);
            day = new Gson().fromJson(json, DayPattern.class);
            mDayName.setText(day.getName());
            actionsUpdate();
            updateList();
            mDayColorButton.setBackgroundResource(day.getColor());
        }
        mDayNoteView.setText(note);
    }

    public void actionsUpdate(){
        actions=new ArrayList<>();
        for(int i=0;i<day.size();i++){
            Date startDate = new Date();
            startDate.setHours(day.get(i).getStartHour());
            startDate.setMinutes(day.get(i).getStartMinute());
            Date finishDate = new Date();
            finishDate.setHours(day.get(i).getFinishHour());
            finishDate.setMinutes(day.get(i).getFinishMinute());
            if(day.get(i).getSubAction().equals("") || day.get(i).getSubAction()==null){
                actions.add(formatter.format(startDate) + " - " + formatter.format(finishDate) + "  " + day.get(i).getMainAction());
            }else {
                actions.add(formatter.format(startDate) + " - " + formatter.format(finishDate) + "  " + day.get(i).getMainAction() + "  (" + day.get(i).getSubAction() + ")");
            }
            //actions.add(day.get(i).getMainAction() + " " + day.get(i).getStartHour() + ":" + day.get(i).getStartMinute() + "-" + day.get(i).getFinishHour() + ":" + day.get(i).getFinishMinute() + " " + day.get(i).getSubAction());
        }
    }
    public void updateList(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actions);
        mDayList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day_view, menu);
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
