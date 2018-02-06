package game.logic_game;

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
    int object_size_x;
    int object_size_y;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        object_size_x = view.getWidth();
        object_size_y = view.getHeight();

         Log.d("test123", motionEvent.getAction() + "");

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                animate(view).y(motionEvent.getRawY() - object_size_y / 2).x(motionEvent.getRawX() - object_size_x/2).setDuration(0);
                return true;
        }
        return false;
    }
}