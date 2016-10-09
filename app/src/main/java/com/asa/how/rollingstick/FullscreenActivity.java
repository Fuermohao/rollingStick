package com.asa.how.rollingstick;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class FullscreenActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    public  View[] led;



    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };

    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }
    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    //以上代码不关我卵事，除非我要添加控件，需要隐藏，我会加入一些语句来干这些事，当然也会指明改动的地点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        init();
    }


    public void init(){
        mVisible = true;
        mContentView = findViewById(R.id.leds);
        mContentView.setOnClickListener(this);

        //以下初始化led灯的view

        led = new View[16];
        led[0] = findViewById(R.id.led_0);
        led[1] = findViewById(R.id.led_1);
        led[2] = findViewById(R.id.led_2);
        led[3] = findViewById(R.id.led_3);
        led[4] = findViewById(R.id.led_4);
        led[5] = findViewById(R.id.led_5);
        led[6] = findViewById(R.id.led_6);
        led[7] = findViewById(R.id.led_7);
        led[8] = findViewById(R.id.led_8);
        led[9] = findViewById(R.id.led_9);
        led[10] = findViewById(R.id.led_a);
        led[11] = findViewById(R.id.led_b);
        led[12] = findViewById(R.id.led_c);
        led[13] = findViewById(R.id.led_d);
        led[14] = findViewById(R.id.led_e);
        led[15] = findViewById(R.id.led_f);
    }
    //初始化咯，净化oncreat()方法

    @Override
    public void onClick(View v) {
        toggle();
    }
    //设计成点击整个led条都会触发全屏或退出全屏
}