package com.datx02_18_35.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class Levels extends AppCompatActivity {
    //callback
    LevelsCallback callback;

    //views
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LevelsAdapter adapter;
    public int x=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        recyclerView = (RecyclerView) findViewById(R.id.levels_recyclerview);


        //dynamic span count later
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new LevelsAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callback = new LevelsCallback();
        try {
            Controller.getSingleton().handleAction(new RequestLevelsAction(callback));
        } catch (GameException e) {
            e.printStackTrace();
        }
    }

    public void startLevel(Level level){
        try {
            Controller.getSingleton().handleAction(new RequestStartNewSessionAction(callback,level));
            Intent intent = new Intent(this, GameBoard.class); //create intent
            startActivity(intent); //start intent
        } catch (GameException e) {
            e.printStackTrace();
        }
    }
    public class LevelsCallback extends ActionConsumer {
        @Override
        public void handleAction(Action action){
            if(action instanceof RefreshLevelsAction){
                RefreshLevelsAction refreshLevelsAction = (RefreshLevelsAction)action;
                LevelCategory levelCategory =  refreshLevelsAction.levelCollection.getCategories().get(x);
                adapter.updateLevels(levelCategory,refreshLevelsAction.levelProgressionMap, refreshLevelsAction.categoryProgressionMap.get(levelCategory));
            }
        }
    }
}