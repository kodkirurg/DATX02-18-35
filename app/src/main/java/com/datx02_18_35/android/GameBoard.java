package com.datx02_18_35.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.UnhandledActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;


import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshHypothesisAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshSymbolMap;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshScopeLevelAction;

import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestHypothesisAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestScopeLevelAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestStartNextLevelAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestSymbolMap;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.OpenSandboxAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshRulesAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestAbortSessionAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestApplyRuleAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestAssumptionAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestRulesAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.SaveUserDataAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.VictoryConditionMetAction;


import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Semaphore;

import game.logic_game.R;

public class GameBoard extends AppCompatActivity implements View.OnClickListener {
    TextView scoreView;
    Button nextLevel;
    Button mainMenu;
    Toolbar toolbar;
    TextView scopeLevel;
    RelativeLayout layout;
    RelativeLayout victoryScreen;
    private ArrayList<Expression> inventoryList = new ArrayList<Expression>();
    public static BoardCallback boardCallback;
    public static OpenSandboxAction sandboxAction=null;
    public final Semaphore gameChange = new Semaphore(1);
    public static boolean victory=false;
    public static Iterable<Expression> hypothesis;
    public static ArrayList<Expression> hypothesisList = new ArrayList<Expression>();
    public static Iterable<Iterable<Expression>> inventories;
    public static Iterable<Expression> assumptions;
    public static Map<String, String> symbolMap;
    public boolean infoWindowClicked=true;
    public PopupWindow popupWindow;


    //recyclerviews
    public RecyclerView recyclerViewLeft,recyclerViewRight,invRecyclerView;
    public GridLayoutManager gridLayoutManagerLeft, gridLayoutManagerRight,invRecLayoutManager;
    public GameRuleAdapter adapterRight;
    public GameCardAdapter adapterLeft;
    public InventoryAdapter invRecAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boardCallback = new BoardCallback();
        boardCallback.start();
        Intent myIntent= getIntent();
        try {
            gameChange.acquire();
            int levelInt=myIntent.getIntExtra("levelInt",1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Controller.getSingleton().sendAction(new RequestInventoryAction(GameBoard.boardCallback));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            Controller.getSingleton().sendAction(new RequestHypothesisAction(boardCallback));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        initLeftSide();
        initRightSide();
        initInventory();
        try {
            Controller.getSingleton().sendAction(new RequestGameboardAction(boardCallback));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Set up victory screen buttons and layout
        victoryScreen = (RelativeLayout) findViewById(R.id.victory_screen);
        victoryScreen.setVisibility(View.GONE);
        nextLevel = (Button) findViewById(R.id.next_level);
        nextLevel.setOnClickListener(this);
        mainMenu = (Button) findViewById(R.id.main_menu);
        mainMenu.setOnClickListener(this);
        scoreView = (TextView) findViewById(R.id.win_score);


        try {
            Controller.getSingleton().sendAction(new RequestSymbolMap(boardCallback));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Set toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scopeLevel = findViewById(R.id.toolbar_text);
        scopeLevel.setText("scope 0");


        //pop-up window for goal and description
        ImageView infoButton = findViewById(R.id.toolbar_goal);
        infoButton.setOnClickListener(this);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View popUpView = inflater.inflate(R.layout.pop_up_window,null);
        popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        gameChange.release();
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
        int widthDP=Math.round(Tools.getWidthDp(getApplication().getApplicationContext())) - (20+130*2);
        for (spanCount=0; 130*spanCount < widthDP ;spanCount++);

        recyclerViewLeft = (RecyclerView) findViewById(R.id.game_left_side);
        // use a grid layout manager
        gridLayoutManagerLeft = new GridLayoutManager(getApplication(), spanCount);
        recyclerViewLeft.setLayoutManager(gridLayoutManagerLeft);


        adapterLeft = new GameCardAdapter(new ArrayList<Expression>(),this);

        recyclerViewLeft.setAdapter(adapterLeft);

    }
    private void initInventory() {
        int spanCount=3;
        //int widthDP=Math.round(Tools.getWidthDp(getApplication().getApplicationContext())) - 130*2;
        //for (spanCount=0; 130*spanCount < widthDP ;spanCount++);


        invRecyclerView = (RecyclerView) findViewById(R.id.inv_recycler_view);
        invRecLayoutManager = new GridLayoutManager(getApplication(), spanCount);
        invRecyclerView.setLayoutManager(invRecLayoutManager);
        invRecAdapter = new InventoryAdapter(hypothesisList, this);
        invRecyclerView.setAdapter(invRecAdapter);



        View gameView = this.findViewById(android.R.id.content);
        layout = (RelativeLayout)findViewById(R.id.inventory_container);
        layout.setVisibility(View.GONE);

        gameView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                showInventory();
            }
            public void onSwipeLeft() {
                closeInventory();
            }

        });
    }

    public void closeInventory(){
        if (layout.isShown()) {
            Tools.slide_left(this, layout);
            layout.setVisibility(View.GONE);
            invRecyclerView.setVisibility(View.GONE);
            recyclerViewRight.setVisibility(View.VISIBLE);
            recyclerViewLeft.setVisibility(View.VISIBLE);


        }
    }
    public void showInventory(){
        ArrayList<Expression> newSet = new ArrayList<Expression>();

        for (Expression expr: GameBoard.hypothesis){
            newSet.add(expr);
        }
        for (Expression expr: GameBoard.assumptions){
            newSet.add(expr);
        }

        for (Iterable<Expression> iter: GameBoard.inventories){
            for (Expression expr :iter) {
                newSet.add(expr);
            }
        }

        if (!layout.isShown()){
            Tools.slide_right(this, layout);
            layout.setVisibility(View.VISIBLE);
            recyclerViewRight.setVisibility(View.GONE);
            recyclerViewLeft.setVisibility(View.GONE);
            invRecyclerView.setVisibility(View.VISIBLE);
            invRecAdapter.updateInventory(newSet);


        }
    }

    public synchronized void newSelection(Object object, View v) {
        try {
            gameChange.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (object instanceof Expression){
            Expression expression = (Expression) object;
            //already selected
            if (adapterLeft.selected.contains((int)v.getTag())){
                adapterLeft.resetSelection(expression, (CardView) v);
            }
            //not selected
            else if(!adapterLeft.selected.contains((int)v.getTag())){
                adapterLeft.setSelection(expression, (CardView) v);
            }
            //update rightside
            try {
                ArrayList<Expression> sendList = new ArrayList<>();
                for (Integer i : adapterLeft.selected){
                    sendList.add(adapterLeft.dataSet.get(i));
                }
                Controller.getSingleton().sendAction(new RequestRulesAction(boardCallback, sendList));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(object instanceof Rule){
            try {
                Controller.getSingleton().sendAction(new RequestApplyRuleAction(boardCallback,(Rule)object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        gameChange.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //shutdown session
        try {
            if(!victory){
                Controller.getSingleton().sendAction(new RequestAbortSessionAction());
            }
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
        switch(menu.getItemId()){
            case R.id.item_assumption:
                try {
                    Controller.getSingleton().sendAction((new RequestAssumptionAction(boardCallback)));
                    scopeLevel.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }


    public class BoardCallback extends ActionConsumer {
        @Override
        public void handleAction(final Action action) throws UnhandledActionException, InterruptedException {
            gameChange.acquire();
            if (action instanceof RefreshGameboardAction){
                Iterable<Expression> data =  ((RefreshGameboardAction) action).boardExpressions;
                adapterLeft.updateBoard(data);

            }
            else if (action instanceof SaveUserDataAction){
                Tools.writeUserData( ((SaveUserDataAction) action).userData, getApplicationContext());
            }
            else if (action instanceof RefreshRulesAction){
                Collection<Rule> data = ((RefreshRulesAction) action).rules;
                adapterRight.updateBoard(data);
            }
            else if(action instanceof SaveUserDataAction){
                Tools.writeUserData(((SaveUserDataAction)action).userData,getApplicationContext());
            }
            else if(action instanceof RefreshSymbolMap){
                symbolMap=((RefreshSymbolMap) action).symbolMap;
            }
            else if (action instanceof OpenSandboxAction){
                String reason="";
                sandboxAction =(OpenSandboxAction) action;
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
                //sandboxAction=(OpenSandboxAction) action;
                //i.putExtra("STRING_I_NEED", reason);
                startActivity(i);
            }
            else if (action instanceof RefreshInventoryAction){
                inventories = ((RefreshInventoryAction) action).inventories;
                assumptions = ((RefreshInventoryAction) action).assumptions;
            }
            else if (action instanceof RefreshHypothesisAction){
                hypothesis = ((RefreshHypothesisAction) action).hypothesis;
            }
            else if(action instanceof RefreshScopeLevelAction){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scopeLevel.setText("Scope: " + ((RefreshScopeLevelAction) action).scopeLevel);
                    }
                });
            }
            else if(action instanceof VictoryConditionMetAction){
                victory=true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"You are winner!",Toast.LENGTH_LONG).show();
                        if(!((VictoryConditionMetAction) action).hasNextLevel){
                            nextLevel.setVisibility(View.GONE); 
                        }
                        victoryScreen.setVisibility(View.VISIBLE);
                        int currentScore = ((VictoryConditionMetAction) action).currentScore;
                        int previousScore= ((VictoryConditionMetAction) action).previousScore;
                        if(previousScore<0) {
                            scoreView.setText("You finished in: " + currentScore + "steps" +"\n" + "No previous finish");
                        }
                        else {
                            scoreView.setText("You finished in: " + currentScore + " steps" + "\n" + "Your previous best finish was: " + previousScore + " steps");
                        }

                    }
                });
            }
            gameChange.release();
        }
    }
    @Override
    public void onBackPressed(){
        if(layout.isShown()){
            closeInventory();
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        try {
            Controller.getSingleton().sendAction(new RequestScopeLevelAction(GameBoard.boardCallback));
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }



    public void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_goal :
                if(!infoWindowClicked){
                    infoWindowClicked=true;
                    popupWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER,0,0);
                    Log.d(Tools.debug, "onClick: " + "show pop-up");

                }
                else if(infoWindowClicked){
                    infoWindowClicked=false;
                    popupWindow.dismiss();
                    Log.d(Tools.debug, "onClick: " + "dismiss pop-up");
                }
                break;
            case R.id.next_level:{
                try {
                    Controller.getSingleton().sendAction(new RequestStartNextLevelAction(GameBoard.boardCallback));
                    Controller.getSingleton().sendAction(new RequestRulesAction(GameBoard.boardCallback,new ArrayList<Expression>()));
                    victoryScreen.setVisibility(View.GONE);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }
            case R.id.main_menu: {
                try {
                    Controller.getSingleton().sendAction(new RequestAbortSessionAction());
                    finish();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}


