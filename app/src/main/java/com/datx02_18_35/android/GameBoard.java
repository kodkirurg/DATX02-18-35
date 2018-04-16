package com.datx02_18_35.android;

import android.animation.Animator;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.IllegalActionException;
import com.datx02_18_35.controller.dispatch.actions.Action;


import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshCurrentLevelAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshHypothesisAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.controllerAction.RefreshScopeLevelAction;

import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestCloseScopeAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestCurrentLevelAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestDeleteFromGameboardAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestHypothesisAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestInventoryAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestScopeLevelAction;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestStartNextLevelAction;
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


import com.datx02_18_35.model.GameException;
import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.game.VictoryInformation;
import com.datx02_18_35.model.rules.Rule;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.rules.RuleType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import game.logic_game.R;

public class GameBoard extends AppCompatActivity implements View.OnClickListener,Animation.AnimationListener {
    TextView scoreView;
    Button nextLevel;
    Button mainMenu;
    Toolbar toolbar;
    TextView scopeLevel;
    RelativeLayout inventoryLayout;
    ConstraintLayout victoryScreen;
    Animation slide_left,delete,slide_right;
    boolean sandboxOpened = false;
    public static BoardCallback boardCallback;
    public static OpenSandboxAction sandboxAction=null;
    public boolean victory=false;
    public Iterable<Expression> hypothesis;
    public Iterable<Iterable<Expression>> inventories;
    public Iterable<Expression> assumptions;
    public int scopeLevelInt;
    public static Level level;
    public boolean infoWindowClicked=true;
    public PopupWindow popupWindow;
    public View popUpView;
    public View.OnClickListener clickListener;

    public ArrayList<Expression> newSet = new ArrayList<Expression>();

    //recyclerviews
    public RecyclerView recyclerViewLeft,recyclerViewRight,parentInvRecyclerView;
    public GridLayoutManager gridLayoutManagerLeft, gridLayoutManagerRight;
    public LinearLayoutManager parentInvRecLayoutManager;
    public ScopeHolderAdapter parentHolderAdapter;
    public GameRuleAdapter adapterRight;
    public GameCardAdapter adapterLeft;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //inflate so pop-up window can be added.
        LayoutInflater inflater = this.getLayoutInflater();
        final View contentView = inflater.inflate(R.layout.activity_game, null);
        setContentView(contentView);

        boardCallback = new BoardCallback();
        Intent myIntent= getIntent();

        int levelInt=myIntent.getIntExtra("levelInt",1);

        try {
            Controller.getSingleton().handleAction(new RequestInventoryAction(GameBoard.boardCallback));
        }
        catch (GameException e){
            e.printStackTrace();
        }
        try {
            Controller.getSingleton().handleAction(new RequestHypothesisAction(boardCallback));
        } catch (GameException e) {
            e.printStackTrace();
        }



        //screen size
        Tools.GameBoardScreenInfo gameBoardScreenInfo = new Tools.GameBoardScreenInfo();



        initLeftSide(gameBoardScreenInfo.cardWidth, gameBoardScreenInfo.cardHeight, gameBoardScreenInfo.spanCounts);
        initRightSide(gameBoardScreenInfo.cardWidth, gameBoardScreenInfo.cardHeight);
        initInventory();
        try {
            Controller.getSingleton().handleAction(new RequestGameboardAction(boardCallback));
            Controller.getSingleton().handleAction(new RequestCurrentLevelAction(boardCallback));
        } catch (GameException e) {
            e.printStackTrace();
        }


        //Set up victory screen buttons and layout
        victoryScreen = (ConstraintLayout) findViewById(R.id.victory_screen);
        victoryScreen.setVisibility(View.GONE);
        nextLevel = (Button) findViewById(R.id.victory_screen_next_level_button);
        nextLevel.setOnClickListener(this);
        mainMenu = (Button) findViewById(R.id.victory_screen_main_menu_button);
        mainMenu.setOnClickListener(this);
        scoreView = (TextView) findViewById(R.id.win_score);


        ((ImageView)findViewById(R.id.inventory_button)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.open_inventory)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.close_inventory)).setOnClickListener(this);

        //Set toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scopeLevel = findViewById(R.id.toolbar_text);
        scopeLevel.setText("scope 0");

        //pop-up window for goal and description
        ImageView infoButton = findViewById(R.id.toolbar_goal);
        infoButton.setOnClickListener(this);
        ImageView trashCan = findViewById(R.id.trash_can);
        trashCan.setOnClickListener(this);
        ImageView assumption = findViewById(R.id.item_assumption);
        assumption.setOnClickListener(this);


        //Animations
        slide_left = AnimationUtils.loadAnimation(this, R.anim.slide_left);
        slide_left.setAnimationListener(this);
        delete = AnimationUtils.loadAnimation(this, R.anim.delete);
        delete.setAnimationListener(this);

        slide_right = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        slide_right.setAnimationListener(this);



        //load pop-up window with goal
        loadPopUpWindow(contentView);
        clickListener=this;

    }

    private void loadPopUpWindow(final View contentView){
        contentView.post(new Runnable() {
            @Override
            public void run() {
                if(popupWindow==null){
                    // Inflate the custom layout/view
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    popUpView = inflater.inflate(R.layout.pop_up_window,null);
                    popUpView.findViewById(R.id.popup_exit_button).setOnClickListener(clickListener);
                    popupWindow = new PopupWindow(popUpView);

                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if(infoWindowClicked){
                                infoWindowClicked=false;
                                popupWindow.dismiss();
                            }

                        }
                    });
                    popupWindow.setOutsideTouchable(true);

                    View bigView = findViewById(R.id.game_board_bottom);
                    int height = bigView.getHeight() * 4 / 5;
                    int width = bigView.getWidth()  - bigView.getWidth() / 15;
                    popupWindow.setWidth(width);
                    popupWindow.setHeight(height);
                    CardInflator.inflate((CardView) popUpView.findViewById(R.id.popup_goalCard),level.goal,120,170,false);
                    ((TextView)popUpView.findViewById(R.id.popup_level_description)).setText(level.description);
                }
                popupWindow.showAtLocation(contentView, Gravity.CENTER,0,0);
            }
        });



    }



    //only use one spanCount as rules don't need 2 columns
    private void initRightSide(float cardWidth, float cardHeight) {

        recyclerViewRight = (RecyclerView) findViewById(R.id.game_right_side);
        // use a grid layout manager
        gridLayoutManagerRight = new GridLayoutManager(getApplication(), 1);
        recyclerViewRight.setLayoutManager(gridLayoutManagerRight);

        ArrayList<Rule> list = new ArrayList<>();
        //attach list to adapter
        adapterRight = new GameRuleAdapter(list,this,cardWidth,cardHeight);

        //attach adapter
        recyclerViewRight.setAdapter(adapterRight);
    }

    //use remaining spanCounts, spanCount-1
    private void initLeftSide(float cardWidth, float cardHeight, int spanCount) {

        recyclerViewLeft = (RecyclerView) findViewById(R.id.game_left_side);
        // use a grid layout manager
        gridLayoutManagerLeft = new GridLayoutManager(getApplication(), spanCount-1);
        recyclerViewLeft.setLayoutManager(gridLayoutManagerLeft);


        adapterLeft = new GameCardAdapter(new ArrayList<Expression>(),this, cardWidth, cardHeight);

        recyclerViewLeft.setAdapter(adapterLeft);

    }
    private void initInventory() {
        parentInvRecyclerView = (RecyclerView) findViewById(R.id.inv_recycler_view);

        parentInvRecLayoutManager = new LinearLayoutManager(this);
        parentInvRecyclerView.setLayoutManager(parentInvRecLayoutManager);
        parentInvRecyclerView.setHasFixedSize(true);
        parentHolderAdapter = new ScopeHolderAdapter(this);
        parentInvRecyclerView.setAdapter(parentHolderAdapter);



        View gameView = this.findViewById(android.R.id.content);
        inventoryLayout = (RelativeLayout)findViewById(R.id.inventory_container);
        inventoryLayout.setVisibility(View.GONE);

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
        if (inventoryLayout.isShown()) {
            startAnimation(slide_left, inventoryLayout);
        }
    }
    public void showInventory(){
        ArrayList<ArrayList<Expression>> totInventory = new ArrayList<ArrayList<Expression>>();
        ArrayList<String> tempSection = new ArrayList<String>();
        try {
            Controller.getSingleton().handleAction(new RequestScopeLevelAction(boardCallback));
            Controller.getSingleton().handleAction(new RequestInventoryAction(boardCallback));
            Controller.getSingleton().handleAction(new RequestHypothesisAction(boardCallback));
        } catch (GameException e) {
            e.printStackTrace();
        }
        newSet.clear();

        ArrayList<Expression> tempHypothesis = new ArrayList<Expression>();
        for (Expression expr: hypothesis){
            newSet.add(expr);
            tempHypothesis.add(expr);
        }
        totInventory.add(tempHypothesis);


        tempSection.add("Hypothesis");
        int i=0;
        for (Expression expr: assumptions){
            i++;
            ArrayList<Expression> tempAssumptions = new ArrayList<Expression>();
            tempSection.add("Assumption "+i);
            tempAssumptions.add(expr);
            totInventory.add(tempAssumptions);
        }

        i =0;
        for (Iterable<Expression> iter: inventories){
            ArrayList<Expression> tempList =new ArrayList<Expression>();
            for (Expression expr :iter) {
                tempList.add(expr);
            }
            i++;
            tempSection.add("Scope "+i);
            totInventory.add(tempList);

        }
        if (!inventoryLayout.isShown()){
            parentHolderAdapter.updateInventory(totInventory,tempSection);
            startAnimation(slide_right, inventoryLayout);
        }
    }
    public void deleteSelection(){
        for(CardView view : adapterLeft.selectedView.values()){
            startAnimation(delete, view);
        }
    }
    public void startAnimation(Animation a, View v ){
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public synchronized void newSelection(Object object, View v) {
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
                Controller.getSingleton().handleAction(new RequestRulesAction(boardCallback, sendList));
            } catch (GameException e) {
                e.printStackTrace();
            }

        }
        else if(object instanceof Rule){
            try {
                Controller.getSingleton().handleAction(new RequestApplyRuleAction(boardCallback,(Rule)object));
            } catch (GameException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //shutdown session
        try {
            if(!victory){
                Controller.getSingleton().handleAction(new RequestAbortSessionAction());
            }
            if(infoWindowClicked){
                popupWindow.dismiss();
            }
        } catch (GameException e) {
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
                if (inventoryLayout.isShown()){
                    sandboxOpened=true;
                    closeInventory();
                }
                else {
                    try {
                        Controller.getSingleton().handleAction((new RequestAssumptionAction(boardCallback)));
                        scopeLevel.setText("");
                    } catch (GameException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(animation==slide_left) {
            recyclerViewRight.setVisibility(View.VISIBLE);
            recyclerViewLeft.setVisibility(View.VISIBLE);
        }
        else if(animation==slide_right){
            inventoryLayout.setVisibility(View.VISIBLE);
            parentInvRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation==slide_left) {
            inventoryLayout.setVisibility(View.GONE);
            parentInvRecyclerView.setVisibility(View.GONE);
            findViewById(R.id.close_inventory).setClickable(false);
            if(sandboxOpened){
                try {
                    Controller.getSingleton().handleAction((new RequestAssumptionAction(boardCallback)));
                    scopeLevel.setText("");
                } catch (GameException e) {
                    e.printStackTrace();
                }
                sandboxOpened=false;
            }
        }
        else if(animation==slide_right){
            recyclerViewRight.setVisibility(View.GONE);
            recyclerViewLeft.setVisibility(View.GONE);
            findViewById(R.id.close_inventory).setClickable(true);
        }
        else if(animation==delete){
            ArrayList<Expression> sendList = new ArrayList<>();
            for (Integer i : adapterLeft.selected){
                sendList.add(adapterLeft.dataSet.get(i));
            }
            for(CardView view : adapterLeft.selectedView.values()){
                view.setVisibility(View.GONE);
                view.setClickable(false);
            }
            adapterLeft.selected.clear();
            adapterLeft.selectedView.clear();
            try {

                Controller.getSingleton().handleAction(new RequestDeleteFromGameboardAction(boardCallback,sendList));
            } catch (GameException e) {
                e.printStackTrace();
            }
            sendList.clear();
            try {
                Controller.getSingleton().handleAction(new RequestRulesAction(boardCallback, sendList));
            } catch (GameException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public class BoardCallback extends ActionConsumer {
        @Override
        public void handleAction(final Action action) throws GameException {
            if (action instanceof RefreshGameboardAction){
                Iterable<Expression> data =  ((RefreshGameboardAction) action).boardExpressions;
                adapterLeft.updateBoard(data);

            }
            else if (action instanceof SaveUserDataAction){
                Tools.writeUserData( ((SaveUserDataAction) action).userData, getApplicationContext());
            }
            else if (action instanceof RefreshCurrentLevelAction){
                level = ((RefreshCurrentLevelAction) action).level;
                findViewById(R.id.item_assumption).setVisibility(
                        level.ruleSet.contains(RuleType.IMPLICATION_INTRODUCTION) ? View.VISIBLE : View.INVISIBLE);
                adapterLeft.updateGoal(level.goal);
            }
            else if (action instanceof RefreshRulesAction){
                Collection<Rule> data = ((RefreshRulesAction) action).rules;
                adapterRight.updateBoard(data);
            }
            else if (action instanceof OpenSandboxAction){
                String reason="";
                sandboxAction =(OpenSandboxAction) action;
                switch(sandboxAction.reason){
                    case ASSUMPTION:{
                        reason = "assumption";
                        break;
                    }
                    case ABSURDITY_ELIMINATION: {
                        reason = "absurdity elimination";
                        break;
                    }
                    case DISJUNCTION_INTRODUCTION: {
                        reason = "disjunction introduction";
                        break;
                    }
                    case LAW_OF_EXCLUDING_MIDDLE: {
                        reason = "law of excluding middle";
                        break;
                    }
                    default: {
                        throw new IllegalActionException(action, "Unknown reason: " + sandboxAction.reason);
                    }

                }
                Intent i = new Intent(getApplicationContext(),Sandbox.class);
                //sandboxAction=(OpenSandboxAction) action;
                i.putExtra("reason", reason);
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
                scopeLevelInt=((RefreshScopeLevelAction) action).scopeLevel;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scopeLevel.setText("Scope: " + ((RefreshScopeLevelAction) action).scopeLevel);
                    }
                });
            }
            else if(action instanceof VictoryConditionMetAction){
                victory=true;
                adapterLeft.setUnclickable();
                adapterRight.setUnclickable();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final VictoryInformation victoryInformation = ((VictoryConditionMetAction) action).victoryInformation;
                        Expression goal= victoryInformation.goal;
                        int position=adapterLeft.dataSet.indexOf(goal);
                        adapterLeft.dataSet.remove(position);
                        adapterLeft.notifyItemRemoved(position);
                        final CardView cardView = (CardView) LayoutInflater.from(
                                getCurrentFocus().getContext()).inflate
                                (R.layout.card_expression,(ViewGroup) getCurrentFocus().getParent(),false);
                        CardInflator.inflate(cardView,victoryInformation.goal,120,170,false);
                        ((ViewGroup)findViewById(android.R.id.content)).addView(cardView);
                        CardInflator.inflate((CardView) findViewById(R.id.victoryScreen_goalCard),victoryInformation.goal,120,170,false);
                        cardView.animate().setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }
                            private String returnStepOrSteps(int step) {
                                if(step==1) return "step";
                                return "steps";
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                ((ViewGroup)cardView.getParent()).removeView(cardView);
                                if(!victoryInformation.hasNextLevel){
                                    nextLevel.setVisibility(View.GONE);
                                }
                                victoryScreen.setVisibility(View.VISIBLE);

                                final String s = "You've completed the goal! Good job! \n";
                                if(victoryInformation.previousScore<0) {
                                    scoreView.setText(s + "You finished in " + victoryInformation.newScore + " " + returnStepOrSteps(victoryInformation.newScore) + "." +"\n" +
                                            "No previous finish.");
                                }
                                else {
                                    scoreView.setText(s + "You finished in " + victoryInformation.newScore + " " + returnStepOrSteps(victoryInformation.newScore) + "." +"\n" +
                                            "Your previous best finish was " + victoryInformation.previousScore + " " + returnStepOrSteps(victoryInformation.newScore) + ".");
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int cardHeight=cardView.getLayoutParams().height;
                        int cardWidth=cardView.getLayoutParams().width;
                        cardView.setX((float) displayMetrics.widthPixels/2-cardWidth/2 );
                        cardView.setY((float) displayMetrics.heightPixels/2-cardHeight/2);
                        cardView.animate().setDuration(5000).scaleY(2).scaleX(2).start();
                    }
                });
            }
        }
    }
    @Override
    public void onBackPressed(){
        if(infoWindowClicked){
            popupWindow.dismiss();
            infoWindowClicked=false;
        }
        else if(inventoryLayout.isShown()){
            closeInventory();
        }
        else if(scopeLevelInt>1){
            try{
                Controller.getSingleton().handleAction(new RequestCloseScopeAction(GameBoard.boardCallback));
            }
            catch (GameException e) {
                e.printStackTrace();
            }
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        adapterLeft.restoreSelections();
        try {
            Controller.getSingleton().handleAction(new RequestScopeLevelAction(GameBoard.boardCallback));
        }
        catch (GameException e){
            e.printStackTrace();
        }
    }



    public void onClick(View view){
        switch (view.getId()){
            case R.id.popup_exit_button :
                if(infoWindowClicked){
                    this.popupWindow.dismiss();
                }
                break;
            case R.id.toolbar_goal :
                if(!infoWindowClicked){
                    infoWindowClicked=true;
                    View rootView = getCurrentFocus().getRootView();
                    if(rootView!=null){
                        loadPopUpWindow(getCurrentFocus().getRootView());
                    }

                }
                break;
            case R.id.victory_screen_next_level_button:{
                try {
                    Controller.getSingleton().handleAction(new RequestStartNextLevelAction(GameBoard.boardCallback));
                    Controller.getSingleton().handleAction(new RequestRulesAction(GameBoard.boardCallback,new ArrayList<Expression>()));
                    Intent intent = new Intent(this, GameBoard.class); //create intent
                    startActivity(intent); //start intent
                    finish();
                }
                catch (GameException e){
                    e.printStackTrace();
                }
                break;
            }
            case R.id.victory_screen_main_menu_button: {
                try {
                    Controller.getSingleton().handleAction(new RequestAbortSessionAction());
                    finish();
                }
                catch (GameException e){
                    e.printStackTrace();
                }
                break;
            }
            case R.id.inventory_button:{
                if(inventoryLayout.isShown()){
                    closeInventory();
                }
                else {
                    showInventory();
                }
                break;
            }
            case R.id.open_inventory:{
                if(inventoryLayout.isShown()){
                    closeInventory();
                }
                else {
                    showInventory();
                }
                break;
            }
            case R.id.close_inventory:{
                if(inventoryLayout.isShown()){
                    closeInventory();
                }
                else {
                    showInventory();
                }
                break;
            }
            case R.id.trash_can:{
                deleteSelection();
                break;
            }
            case R.id.item_assumption: {
                if (inventoryLayout.isShown()) {
                    sandboxOpened = true;
                    closeInventory();
                } else {
                    try {
                        Controller.getSingleton().handleAction((new RequestAssumptionAction(boardCallback)));
                        scopeLevel.setText("");
                    } catch (GameException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

}


