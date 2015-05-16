package com.example.revernschedule;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;


public class ColorActivity extends ActionBarActivity {

    public static final String EXTRA_COLOR = "EXTRA COLOR";
    ColorPicker picker;
    SVBar svBar;
    OpacityBar opacityBar;
    Button mColorOkButton;
//    SaturationBar saturationBar;
//    ValueBar valueBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        int color=getIntent().getIntExtra(EXTRA_COLOR, 0);

        picker = (ColorPicker) findViewById(R.id.picker);
        svBar = (SVBar) findViewById(R.id.svbar);
        opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
//        saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
//        valueBar = (ValueBar) findViewById(R.id.valuebar);
        mColorOkButton = (Button)findViewById(R.id.color_ok_button);
        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
//        picker.addSaturationBar(saturationBar);
//        picker.addValueBar(valueBar);
        picker.setOldCenterColor(color);

        mColorOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(EXTRA_COLOR, picker.getColor());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_color, menu);
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
