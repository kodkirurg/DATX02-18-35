package com.datx02_18_35.android;

import android.view.View;

import com.datx02_18_35.lib.logicmodel.expression.Absurdity;
import com.datx02_18_35.lib.logicmodel.expression.Disjunction;
import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.Operator;
import com.datx02_18_35.lib.logicmodel.expression.Proposition;

/**
 * Created by raxxor on 2018-02-19.
 */

public class Card_generator {
    private static Expression expr;
    private View final_view;

    public View getView(){
        return final_view;
    }

    public Card_generator(Expression expr) {
        Card_generator.expr = expr;
    }


    // call with depth 0 always
    public void generate(int depth, View view, Expression expr1, Expression expr2){

        if(depth > 2){
            //set final ... and set info symb
            final_view = view;
        }

        else if (expr1 instanceof Proposition & !(expr2 instanceof  Proposition)){


            final_view = view;

        }
        else if (expr2 instanceof Proposition & !(expr1 instanceof  Proposition)){

        }
        else if (expr1 instanceof Proposition & expr2 instanceof  Proposition){

        }
        else if (expr instanceof Operator) {


            Operator op = (Operator) expr;

            Expression op1 = op.getOperand1();
            Expression op2 = op.getOperand2();

            generate((depth+1), view,op1,op2);
        }
    }
}
