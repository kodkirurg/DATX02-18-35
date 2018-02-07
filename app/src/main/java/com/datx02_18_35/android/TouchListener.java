package com.datx02_18_35.android;

import android.content.ClipData;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.RelativeLayout;

import static android.support.v4.view.ViewCompat.animate;

/**
 * Created by raxxor on 2018-02-02.
 */

public class TouchListener implements View.OnTouchListener {
    float pos_x,pos_y;
    float prev_pos_x,prev_pos_y;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

         Log.d("test123", motionEvent.getAction() + "");

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prev_pos_x = motionEvent.getRawX();
                prev_pos_y = motionEvent.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                pos_x = motionEvent.getRawX();
                pos_y = motionEvent.getRawY();
                animate(view)
                        .x(view.getX() + pos_x - prev_pos_x)
                        .y(view.getY() + pos_y - prev_pos_y)
                        .setDuration(0);
                prev_pos_x = pos_x;
                prev_pos_y = pos_y;
                return true;
        }
        return false;
    }
}