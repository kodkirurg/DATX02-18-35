package com.datx02_18_35.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.OperatorType;

import java.util.ArrayList;

import game.logic_game.R;

public class Sandbox extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    public static boolean maySelectOperator=false;
    public static OperatorType operatorSelcted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);


        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.sandbox_right_side, new FragmentSandboxOperators());
        ft.replace(R.id.sandbox_left_side, new FragmentSandboxCards()).commit();
        findViewById(R.id.sandbox_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId()){
            case R.id.sandbox_button :
                if(operatorSelcted==null & maySelectOperator ){
                    Expression expression = SandboxCardsAdapter.selected.get(0);
                    Log.d("test123","exit : " + expression.toString());
                }
                else{
                    finish();
                }
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //just in case
        maySelectOperator=false;
        operatorSelcted=null;
        SandboxCardsAdapter.selected=new ArrayList<Expression>();
        SandboxOperatorAdapter.previousSelectedOperatorHolder=null;
    }
}
