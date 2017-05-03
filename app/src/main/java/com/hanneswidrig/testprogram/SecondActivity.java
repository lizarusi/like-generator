package com.hanneswidrig.testprogram;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    Button secondActivityButton = null;
    Button levelone = null;
    Button leveltwo = null;
    Button levelthree = null;
    private int levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        SharedPreferences preferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        final int level = preferences.getInt(getString(R.string.level), levels);

        secondActivityButton = (Button) findViewById(R.id.button);
        levelone = (Button) findViewById(R.id.button3);
        leveltwo = (Button) findViewById(R.id.button4);
        levelthree = (Button) findViewById(R.id.button5);

        secondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SecondActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        if(level > 0 && level < 50) {
            levelone.setVisibility(View.VISIBLE);
        }
        if (level > 50) {
            levelone.setVisibility(View.VISIBLE);
            leveltwo.setVisibility(View.VISIBLE);
        }
        if(level > 100) {
            levelone.setVisibility(View.VISIBLE);
            leveltwo.setVisibility(View.VISIBLE);
            levelthree.setVisibility(View.VISIBLE);
        }
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        final int level = preferences.getInt(getString(R.string.level), levels);
        if(level > 0 && level < 50) {
            levelone.setVisibility(View.VISIBLE);
        }
        if (level > 50) {
            levelone.setVisibility(View.VISIBLE);
            leveltwo.setVisibility(View.VISIBLE);
        }
        if(level > 100) {
            levelone.setVisibility(View.VISIBLE);
            leveltwo.setVisibility(View.VISIBLE);
            levelthree.setVisibility(View.VISIBLE);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
//        Toast.makeText(getApplicationContext(),"Activity Destroyed",Toast.LENGTH_LONG).show();
    }
}
