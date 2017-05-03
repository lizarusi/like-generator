package com.hanneswidrig.testprogram;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private Switch aSwitch;
    private Toolbar toolbar;
    private Button aButton;
    public static final String PREFS_NAME = "MyPrefsFile";
    private int colorVar;
    private int[] colors;
    private boolean switchVar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        aSwitch = (Switch) findViewById(R.id.switch_background_color);
        aButton = (Button) findViewById(R.id.button2);
        colorVar = R.color.grey;
        colors = ColorList();
        colorVar = colors[4];

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final boolean switchPref = settings.getBoolean(getString(R.string.switch_key), false);
        aSwitch.setChecked(switchPref);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                if(b) {
                    colorVar = Color.RED;
                    switchVar = true;
                }
                if(!b) {
                    colorVar = Color.WHITE;
                    switchVar = false;
                }
                editor.putBoolean(getString(R.string.switch_key), switchVar);
                editor.apply();
            }
        });
    }

    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(getString(R.string.switch_key), switchVar);
        editor.apply();
    }

    public int[] ColorList() {
        int colors[] = new int[19];
        colors[0] = R.color.red;
        colors[1] = R.color.pink;
        colors[2] = R.color.purple;
        colors[3] = R.color.deep_purple;
        colors[4] = R.color.indigo;
        colors[5] = R.color.blue;
        colors[6] = R.color.light_blue;
        colors[7] = R.color.cyan;
        colors[8] = R.color.teal;
        colors[9] = R.color.green;
        colors[10] = R.color.light_green;
        colors[11] = R.color.lime;
        colors[12] = R.color.yellow;
        colors[13] = R.color.amber;
        colors[14] = R.color.orange;
        colors[15] = R.color.deep_orange;
        colors[16] = R.color.brown;
        colors[17] = R.color.grey;
        colors[18] = R.color.blue_grey;
        return colors;
    }
}
