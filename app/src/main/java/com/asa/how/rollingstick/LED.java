package com.asa.how.rollingstick;

import android.graphics.Color;
import android.view.View;

/**
 * Created by how on 16-10-10.
 */
public class LED {

    private View[] leds;
    private int[] status;

    public LED(View[] views){
        leds = views;
    }

    public void setStatus(int[] ints){
        status = ints;
    }

    void show(){
        for(int i=0;i<leds.length;i++)
        {
            if(status[i]==1)
                leds[i].setBackgroundColor(Color.WHITE);
            else
                leds[i].setBackgroundColor(Color.BLACK);
        }
    }

}
