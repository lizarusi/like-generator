package com.hanneswidrig.testprogram;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int mProgressStatus;
    private ProgressBar mProgress;
    private TextView textView;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private String progressText;
    private ImageView img;
    private int[] imagesRes;
    private int selectedImg;
    private int levels;

    public void backgroundColor() {
        SharedPreferences preferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        final boolean bool = preferences.getBoolean(getString(R.string.switch_key), false);
        final int level = preferences.getInt(getString(R.string.level),levels);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.content_main);
        if(bool) {
            relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                    android.R.color.holo_blue_light));
        } else {
            relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                    android.R.color.background_light));
        }
    }

    public int[] images() {
        //Stack<Integer> imageStack = new Stack<>();
        int[] imageArray = new int[10];
        imageArray[0] = R.drawable.image1;
        imageArray[1] = R.drawable.image2;
        imageArray[2] = R.drawable.image3;
        imageArray[3] = R.drawable.image4;
        imageArray[4] = R.drawable.image5;
        imageArray[5] = R.drawable.image6;
        imageArray[6] = R.drawable.image7;
        imageArray[7] = R.drawable.image8;
        return imageArray;
    }

    public void switchImg(int selectedImg, ImageView img) {
        switch(selectedImg) {
            case 0:
                setScaledImage(img,R.drawable.image1);
                break;
            case 1:
                setScaledImage(img,R.drawable.image2);
                break;
            case 2:
                setScaledImage(img,R.drawable.image3);
                break;
            case 3:
                setScaledImage(img,R.drawable.image4);
                break;
            case 4:
                setScaledImage(img,R.drawable.image5);
                break;
            case 5:
                setScaledImage(img,R.drawable.image6);
                break;
            case 6:
                setScaledImage(img,R.drawable.image7);
                break;
            case 7:
                setScaledImage(img,R.drawable.image8);
                break;
            default:
                setScaledImage(img,R.drawable.image1);
                this.selectedImg = 0;
        }
    }

    private void setScaledImage(ImageView imageView, final int resId) {
        final ImageView iv = imageView;
        ViewTreeObserver viewTreeObserver = iv.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                iv.getViewTreeObserver().removeOnPreDrawListener(this);
                int imageViewHeight = iv.getMeasuredHeight();
                int imageViewWidth = iv.getMeasuredWidth();
                iv.setImageBitmap(
                        decodeSampledBitmapFromResource(getResources(),
                                resId, imageViewWidth, imageViewHeight));
                return true;
            }
        });
    }

    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                          int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        final int level = preferences.getInt(getString(R.string.level), levels);
        final int pickey = preferences.getInt(getString(R.string.pictureskey), selectedImg);
        mProgressStatus = level;
        selectedImg = pickey;

        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.ImageView2);
        backgroundColor();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setProgress(mProgressStatus);
        mProgress.setMax(100);

        textView  = (TextView) findViewById(R.id.textView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        textView.setText(String.format(Locale.ENGLISH,Integer.toString(mProgressStatus)));
        switchImg(selectedImg-1,img);
        img.setVisibility(View.VISIBLE);


        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                mProgressStatus++;
                switchImg(selectedImg,img);
                progressText = Integer.toString(mProgressStatus);
                mProgress.setProgress(mProgressStatus);
                mProgress.incrementProgressBy(1);
                textView.setText(progressText);
                selectedImg++;
                if(mProgressStatus == 100) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.progresslove),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        backgroundColor();
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
        SharedPreferences.Editor editor = settings.edit();
        levels = mProgressStatus;
        editor.putInt(getString(R.string.level), levels);
        editor.putInt(getString(R.string.pictureskey), selectedImg);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent myIntent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.reset:
                mProgressStatus = 0;
                selectedImg = 0;
                switchImg(selectedImg,img);
                selectedImg++;
                mProgress.setProgress(mProgressStatus);
                textView.setText("0");
                return true;
            case R.id.second_act:
                Intent myIntent2 = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(myIntent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
//        Toast.makeText(getApplicationContext(),"Activity Destroyed",Toast.LENGTH_LONG).show();
    }
}
