package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.lib.logicmodel.expression.Absurdity;
import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.Operator;
import com.datx02_18_35.lib.logicmodel.expression.Proposition;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-02-19.
 */

public class CardGenerator {
    private static Expression expr;
    private CardView final_view;
    private Expression g1_expr,g2_expr;
    private ViewGroup parent;

    public View getView(){
        return final_view;
    }

    public CardGenerator(Expression expr, ViewGroup parent) {
        CardGenerator.expr = expr;
        if(expr instanceof Proposition | expr instanceof Absurdity){
            Log.d("test123", "CardGenerator: ");
            final_view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_basic, parent,false);
        }
    }


    // call with depth 0 always
    private void generateOperator(int depth, View view, Expression expr1, Expression expr2){

        if(depth > 2){
            //set final ... and set info symb

        }

        else if (expr1 instanceof Proposition & !(expr2 instanceof  Proposition)){




        }
        else if (expr2 instanceof Proposition & !(expr1 instanceof  Proposition)){

        }
        else if (expr1 instanceof Proposition & expr2 instanceof  Proposition){

        }
        else if (expr instanceof Operator) {


            Operator op = (Operator) expr;

            Expression op1 = op.getOperand1();
            Expression op2 = op.getOperand2();

        }
    }
}
