package com.datx02_18_35.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.UnhandledActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.actions.OpenSandboxAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RefreshRulesAction;
import com.datx02_18_35.controller.dispatch.actions.RequestAbortSessionAction;
import com.datx02_18_35.controller.dispatch.actions.RequestGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RequestStartNewSessionAction;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Semaphore;

import game.logic_game.R;

public class GameBoard extends AppCompatActivity  {

    Toolbar toolbar;
    public static BoardCallback boardCallback;
    public static OpenSandboxAction sandboxAction=null;
    public final static Semaphore gameChange = new Semaphore(1);
    public ArrayList<Expression> selectedExpressions=new ArrayList<>();
    private int pointer = 0;


    //recyclerviews
    public RecyclerView recyclerViewLeft,recyclerViewRight;
    public GridLayoutManager gridLayoutManagerLeft, gridLayoutManagerRight;
    public GameRuleAdapter adapterRight;
    public GameCardAdapter adapterLeft;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boardCallback = new BoardCallback();
        boardCallback.start();
        try {
            Controller.getSingleton().sendAction(new RequestStartNewSessionAction(boardCallback,Controller.getSingleton().getLevels().get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        initLeftSide();
        initRightSide();

        try {
            Controller.getSingleton().sendAction(new RequestGameboardAction(boardCallback));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
                
        //Set toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    private void initRightSide() {

        recyclerViewRight = (RecyclerView) findViewById(R.id.game_right_side);
        // use a grid layout manager
        gridLayoutManagerRight = new GridLayoutManager(getApplication(), 1);
        recyclerViewRight.setLayoutManager(gridLayoutManagerRight);

        ArrayList<Rule> list = new ArrayList<>();
        list.add(null);
        //attach list to adapter
        adapterRight = new GameRuleAdapter(list,this);

        //attach adapter
        recyclerViewRight.setAdapter(adapterRight);
    }

    private void initLeftSide() {
        //"screen" re-size
        int spanCount;
        int widthDP=Math.round(Tools.getWidthDp(getApplication().getApplicationContext())) - 130*2;
        for (spanCount=0; 130*spanCount < widthDP ;spanCount++);

        recyclerViewLeft = (RecyclerView) findViewById(R.id.game_left_side);
        // use a grid layout manager
        gridLayoutManagerLeft = new GridLayoutManager(getApplication(), spanCount);
        recyclerViewLeft.setLayoutManager(gridLayoutManagerLeft);


        adapterLeft = new GameCardAdapter(new ArrayList<Expression>(),this);

        recyclerViewLeft.setAdapter(adapterLeft);

    }

    //return true if selected card is supposed to highlighted
    public synchronized boolean newSelection(Object object){
        if (object instanceof Expression){

        }
        else if(object instanceof Rule){

        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //shutdown session
        try {
            Controller.getSingleton().sendAction(new RequestAbortSessionAction());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu); //add more items under dir menu -> toolbar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        Intent i = null;
        switch(menu.getItemId()){
            case R.id.item_assumption:
                i = new Intent(this,Sandbox.class);
                break;
        }
        startActivity(i);
        return false;
    }


    public class BoardCallback extends ActionConsumer {
        @Override
        public void handleAction(Action action) throws UnhandledActionException, InterruptedException {
            gameChange.acquire();
            if (action instanceof RefreshGameboardAction){
                Iterable<Expression> data =  ((RefreshGameboardAction) action).boardExpressions;
                adapterLeft.updateBoard(data);

            }
            else if (action instanceof RefreshRulesAction){
                Collection<Rule> data = ((RefreshRulesAction) action).rules;
                adapterRight.updateBoard(data);

            }
            else if (action instanceof OpenSandboxAction){
                String reason="";
                switch(((OpenSandboxAction) action).reason){
                    case ASSUMPTION:{
                        reason = "Assumption";
                        break;
                    }
                    case ABSURDITY_ELIMINATION: {
                        reason = "Absurdity elimination";
                        break;
                    }
                    case DISJUNCTION_INTRODUCTION: {
                        reason = "Disjunction elimination";
                        break;
                    }

                }
                Intent i = new Intent(getApplicationContext(),Sandbox.class);
                sandboxAction=(OpenSandboxAction) action;
                i.putExtra("STRING_I_NEED", reason);
                startActivity(i);
            }
            gameChange.release();
        }
    }

}


