package com.datx02_18_35.android;

import android.content.Intent;
import android.graphics.Point;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshLevelsAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestLevelsAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestStartNewSessionAction;
import com.datx02_18_35.model.GameException;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelCategory;


import game.logic_game.R;

public class Levels extends AppCompatActivity implements View.OnClickListener {
    //callback
    LevelsCallback callback;

    //views
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LevelsAdapter adapter;
    boolean levelbeenstarted=false;
    public int categoryIndex = 0;
    public int categorySize = -1;
    private float xDown, xUp;
    private static float percentMovedIfSwipe = (float) 0.25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        recyclerView = (RecyclerView) findViewById(R.id.levels_recyclerview);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new LevelsAdapter(this);
        recyclerView.setAdapter(adapter);

        //right and left arrow listeners
        findViewById(R.id.level_left_arrow).setOnClickListener(this);
        findViewById(R.id.level_right_arrow).setOnClickListener(this);

    }


    //all events called here
    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {

        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = e.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                xUp=e.getRawX();

                //get screenSize in absolute size
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int x = size.x;

                //Swipe
                if(Math.abs(xDown - xUp) > x * percentMovedIfSwipe){
                    //Right swipe
                    if(xDown - xUp < 0){
                        findViewById(R.id.level_left_arrow).performClick();
                    }
                    //Left Swipe
                    else{
                        findViewById(R.id.level_right_arrow).performClick();
                    }
                }

                break;
        }


        //Don't consume the event
        return super.dispatchTouchEvent(e);
    }

    @Override
    protected void onResume() {
        super.onResume();
        levelbeenstarted=false;
        callback = new LevelsCallback();
        try {
            Controller.getSingleton().handleAction(new RequestLevelsAction(callback));
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

    public void startLevel(Level level){
        try {
            if(!levelbeenstarted) {
                Controller.getSingleton().handleAction(new RequestStartNewSessionAction(callback, level));
                Intent intent = new Intent(this, GameBoard.class); //create intent
                startActivity(intent); //start intent
                levelbeenstarted=true;
            }
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

    private void refreshLevels(){
        try {
            Controller.getSingleton().handleAction(new RequestLevelsAction(callback));
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(categorySize==-1){
            try {
                throw new Exception("Levels class: Onclick: Category size not init");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        switch (v.getId()){
            case R.id.level_left_arrow:
                if(categoryIndex == 0 || (categoryIndex+1) > categorySize){
                    //Do nothing
                    break;
                }
                else {
                    categoryIndex-=1;
                    refreshLevels();
                    break;
                }
            case R.id.level_right_arrow:
                if((categoryIndex+1) >= categorySize){
                    //Do nothing
                    break;
                }
                else{
                    categoryIndex+=1;
                    refreshLevels();
                    break;
                }
        }
    }

    public class LevelsCallback extends ActionConsumer {
        @Override
        public void handleAction(Action action){
            if(action instanceof RefreshLevelsAction){
                RefreshLevelsAction refreshLevelsAction = (RefreshLevelsAction)action;

                categorySize = refreshLevelsAction.levelCollection.getCategories().size();

                LevelCategory levelCategory =  refreshLevelsAction.levelCollection.getCategories().get(categoryIndex);
                ((TextView)findViewById(R.id.level_top)).setText(levelCategory.getName());
                adapter.updateLevels(levelCategory,refreshLevelsAction.levelProgressionMap, refreshLevelsAction.categoryProgressionMap.get(levelCategory));

                //arrows remove or add
                findViewById(R.id.level_left_arrow).setVisibility(categoryIndex == 0 ? View.GONE : View.VISIBLE);
                findViewById(R.id.level_right_arrow).setVisibility(categoryIndex == categorySize-1 ? View.GONE : View.VISIBLE);
            }
        }
    }
    //if app has been down for a while memory will be trimmed and the app will crash
    //unless we release memory, i.e lets kill it in a controlled way
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if(TRIM_MEMORY_COMPLETE==level){
            finish();
        }
    }

}