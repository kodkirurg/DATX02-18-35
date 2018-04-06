package com.datx02_18_35.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.viewActions.ClosedSandboxAction;
import com.datx02_18_35.model.GameException;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.OperatorType;

import java.util.ArrayList;

import game.logic_game.R;

public class Sandbox extends AppCompatActivity implements View.OnClickListener {


    public boolean maySelectOperator=false;
    public OperatorType operatorSelcted;
    public Button button;
    public String reason;


    //Recyclerviews, gridlayouts and adapters
    RecyclerView recyclerViewLeft,recyclerViewRight;
    GridLayoutManager gridLayoutManagerLeft,gridLayoutManagerRight;
    SandboxCardsAdapter adapterLeft;
    SandboxOperatorAdapter adapterRight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);


        Tools.SandboxScreenInfo screenInfo = new Tools.SandboxScreenInfo();


        //init sandbox board
        initRightSide(screenInfo.cardWidth,screenInfo.cardHeight);
        initLeftSide(screenInfo.cardWidth,screenInfo.cardHeight, screenInfo.spanCounts);


        button = findViewById(R.id.sandbox_button);
        findViewById(R.id.sandbox_button).setOnClickListener(this);
        reason = getIntent().getStringExtra("reason");
        button.setText("No " + reason + "(exit)");

    }


    public void initLeftSide(float cardWidth, float cardHeight, int spanCounts){

        recyclerViewLeft = (RecyclerView) findViewById(R.id.sandboxLeft_recycler_view);
        gridLayoutManagerLeft = new GridLayoutManager(getApplication(), spanCounts-1);
        recyclerViewLeft.setLayoutManager(gridLayoutManagerLeft);

        ArrayList<Expression> list = new ArrayList<>(GameBoard.level.usedSymbols);
        adapterLeft = new SandboxCardsAdapter(list,this,cardWidth, cardHeight);
        recyclerViewLeft.setAdapter(adapterLeft);
    }


    public void initRightSide(float cardWidth, float cardHeight){

        recyclerViewRight = (RecyclerView) findViewById(R.id.sandboxRight_recycler_view);
        gridLayoutManagerRight = new GridLayoutManager(getApplication(),1);
        recyclerViewRight.setLayoutManager(gridLayoutManagerRight);

        ArrayList<OperatorType> list = new ArrayList<>();
        list.add(OperatorType.IMPLICATION);
        list.add(OperatorType.DISJUNCTION);
        list.add(OperatorType.CONJUNCTION);

        adapterRight = new SandboxOperatorAdapter(list,this,cardWidth, cardHeight);
        recyclerViewRight.setAdapter(adapterRight);
    }



    @Override
    public void onClick(View view) {
        switch ( view.getId()){
            case R.id.sandbox_button :
                if(operatorSelcted==null & maySelectOperator ){
                    Expression expression = adapterLeft.selected.get(0);
                    try {
                        Controller.getSingleton().handleAction(new ClosedSandboxAction(GameBoard.boardCallback, GameBoard.sandboxAction,expression));

                    } catch (GameException e) {
                        e.printStackTrace();
                    }
                }
                finish();
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Needed
        maySelectOperator=false;
        operatorSelcted=null;
        adapterLeft.selected=new ArrayList<Expression>();
        adapterRight.previousSelectedOperatorHolder=null;
    }
}
