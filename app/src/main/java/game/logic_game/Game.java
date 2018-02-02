package game.logic_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Game extends AppCompatActivity {
    private ViewGroup root;
    private int _xDelta;
    private int _yDelta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        //add listeners
        CardView card_1 = (CardView) findViewById(R.id.card_1);
        CardView card_2 = (CardView) findViewById(R.id.card_2);
        card_1.setOnTouchListener(new TouchListener());
        card_2.setOnTouchListener(new TouchListener());
    }

}
