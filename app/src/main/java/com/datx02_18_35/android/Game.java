package com.datx02_18_35.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.UnhandledActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.controller.dispatch.actions.RefreshGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.RequestGameboardAction;
import com.datx02_18_35.model.expression.Expression;

import java.util.Iterator;
import java.util.concurrent.Semaphore;

import game.logic_game.R;

public class Game extends AppCompatActivity  {

    Toolbar toolbar;
    public Semaphore ready=new Semaphore(1);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            ready.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Controller.singleton.start();
        BoardCallback boardCallback = new BoardCallback();
        boardCallback.start();
        try {
            Controller.singleton.sendAction(new RequestGameboardAction(boardCallback));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();

                ft.replace(R.id.game_right_side, new FragmentBoardActions());
                ft.replace(R.id.game_left_side , new FragmentBoardCards()).commit();

                
                //Set toolbar
                toolbar = findViewById(R.id.toolbar);
                toolbar.setTitle("");
                setSupportActionBar(toolbar);

            }
        }).start();
        ready.release();



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
                i = new Intent(this,Scope.class); //change to scope/assumption class
                break;
        }
        startActivity(i);
        return false;
    }


    public class BoardCallback extends ActionConsumer {
        @Override
        public void handleAction(Action action) throws UnhandledActionException, InterruptedException {
            ready.tryAcquire();
            Log.d("test123","test");
            if (action instanceof RefreshGameboardAction){
                Iterable<Expression> data =  ((RefreshGameboardAction) action).boardExpressions;
                FragmentManager fm = getFragmentManager();
                FragmentBoardCards frag = (FragmentBoardCards) fm.findFragmentById(R.id.game_left_side);
                frag.updateBoard(data);
            }
        }
    }

}
