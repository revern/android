package com.example.revernschedule;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ColorChooseActivity extends ActionBarActivity {
    public static final String EXTRA_COLOR_CHOOSE = "EXTRA COLOR CHOOSE";
    Button red;
    Button pink;
    Button purple;
    Button deepPurple;
    Button blueGrey;
    Button cyan;
    Button blue;
    Button indigo;
    Button teal;
    Button lime;
    Button lightGreen;
    Button green;
    Button yellow;
    Button amber;
    Button orange;
    Button deerOrange;
    Button grey100;
    Button grey500;
    Button grey700;
    Button grey900;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_choose);
        red = (Button) findViewById(R.id.redB);
        pink = (Button) findViewById(R.id.pinkB);
        purple = (Button) findViewById(R.id.purpleB);
        deepPurple = (Button) findViewById(R.id.deepPurpleB);
        blueGrey = (Button) findViewById(R.id.blueGreyB);
        cyan = (Button) findViewById(R.id.cyanB);
        blue = (Button) findViewById(R.id.blueB);
        indigo = (Button) findViewById(R.id.indigoB);
        teal = (Button) findViewById(R.id.tealB);
        lime = (Button) findViewById(R.id.limeB);
        lightGreen = (Button) findViewById(R.id.lightGreenB);
        green = (Button) findViewById(R.id.greenB);
        yellow = (Button) findViewById(R.id.yellowB);
        amber = (Button) findViewById(R.id.amberB);
        orange = (Button) findViewById(R.id.orangeB);
        deerOrange = (Button) findViewById(R.id.deepOrangeB);
        grey100 = (Button) findViewById(R.id.grey100B);
        grey500 = (Button) findViewById(R.id.grey500B);
        grey700 = (Button) findViewById(R.id.grey700B);
        grey900 = (Button) findViewById(R.id.grey900B);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_red_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_pink_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_purple_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        deepPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_deep_purple_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        blueGrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_blue_grey_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        cyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_cyan_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_blue_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        indigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_indigo_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        teal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_teal_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        lime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_lime_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        lightGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_light_green_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_green_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_yellow_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        amber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_amber_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_orange_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        deerOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_deep_orange_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        grey100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.grey_100);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        grey500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_grey_500);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        grey700.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.grey_700);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        grey900.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR_CHOOSE, R.color.fab_material_grey_900);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_color_choose, menu);
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
