package com.asa.how.rollingstick;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class FullscreenActivity extends AppCompatActivity implements View.OnClickListener,SensorEventListener{

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private Sensor sensor;
    private SensorManager sm;
    public  View[] led;
    public  LED shine;
    public int[][]  matrix;
    int i , j;
    float[] values;

    //                    0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f
    public int[] row_0 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
    public int[] row_1 = {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0};
    public int[] row_2 = {0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0};
    public int[] row_3 = {0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0};
    public int[] row_4 = {0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0};
    public int[] row_5 = {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1};
    public int[] row_6 = {0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0};
    public int[] row_7 = {0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0};
    public int[] row_8 = {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0};
    public int[] row_9 = {0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0};
    public int[] row_a = {0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0};
    public int[] row_b = {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0};
    public int[] row_c = {0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0};
    public int[] row_d = {0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0};
    public int[] row_e = {0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0};
    public int[] row_f = {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0};





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

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);//获取系统传感器服务
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//默认传感器设置为加速度传感器
        sm.registerListener(this,sensor,1000);//指定传感器监听接口及其参数

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

        values = new float[3];
        values[0] = 0;
        values[1] = 0;
        values[2] = 100;

        matrix = new int[16][16];
        matrix[0] = row_0;
        matrix[1] = row_1;
        matrix[2] = row_2;
        matrix[3] = row_3;
        matrix[4] = row_4;
        matrix[5] = row_5;
        matrix[6] = row_6;
        matrix[7] = row_7;
        matrix[8] = row_8;
        matrix[9] = row_9;
        matrix[10] = row_a;
        matrix[11] = row_b;
        matrix[12] = row_c;
        matrix[13] = row_d;
        matrix[14] = row_e;
        matrix[15] = row_f;

        i = 0;
        j = 0;

        shine = new LED(led);

    }
    //初始化咯，净化oncreat()方法

    @Override
    public void onClick(View v) {
        toggle();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        values[j] = event.values[0];
        j++;
        if(j>2)
            j = 0;

        if(values[2]!=100){
            if((values[2]-values[1])*(values[1]-values[0])<0){
                shine.setStatus(matrix[i]);
                shine.show();
                i++;
            }


        }



        if(i>15)
            i = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //设计成点击整个led条都会触发全屏或退出全屏
}