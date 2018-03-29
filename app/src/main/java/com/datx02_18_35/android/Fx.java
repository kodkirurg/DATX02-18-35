package com.datx02_18_35.android;

/**
 * Created by Magnus on 2018-03-01.
 */

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import game.logic_game.R;

public class Fx {
    View view;
//...
    /**
     *
     * @param ctx
     * @param v
     */



    public static void deleteAnimation(Context ctx, View v){
        Animation a =AnimationUtils.loadAnimation(ctx, R.anim.delete);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void selectAnimation(Context ctx, View v){
        Animation a =AnimationUtils.loadAnimation(ctx, R.anim.select);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void deselectAnimation(Context ctx, View v){
        Animation a =AnimationUtils.loadAnimation(ctx, R.anim.deselect);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void slide_left(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_left);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void slide_right(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_right);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void blink_animation(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.blink);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
}
