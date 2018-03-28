package com.datx02_18_35.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.viewActions.ClosedSandboxAction;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.OperatorType;

import java.util.ArrayList;

import game.logic_game.R;

public class Sandbox extends AppCompatActivity implements View.OnClickListener {


    public static boolean maySelectOperator=false;
    public static OperatorType operatorSelcted;
    public static Button button;
    public static String reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.sandbox_right_side, new FragmentSandboxOperators());
        ft.replace(R.id.sandbox_left_side, new FragmentSandboxCards()).commit();
        button = findViewById(R.id.sandbox_button);
        findViewById(R.id.sandbox_button).setOnClickListener(this);
        reason = getIntent().getStringExtra("reason");
        button.setText("No " + reason + "(exit)");

    }

    @Override
    public void onClick(View view) {
        switch ( view.getId()){
            case R.id.sandbox_button :
                if(operatorSelcted==null & maySelectOperator ){
                    Expression expression = SandboxCardsAdapter.selected.get(0);
                    try {
                        Controller.getSingleton().sendAction(new ClosedSandboxAction(GameBoard.boardCallback, GameBoard.sandboxAction,expression));

                    } catch (InterruptedException e) {
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
        SandboxCardsAdapter.selected=new ArrayList<Expression>();
        SandboxOperatorAdapter.previousSelectedOperatorHolder=null;
    }
}
