package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.model.expression.Expression;

/**
 * Created by raxxor on 2018-03-07.
 */


/*
Model requests view to update gameboard Expressions
*/
public class RefreshGameboardAction extends Action {
    public final Iterable<Expression> boardExpressions;

    public RefreshGameboardAction(Iterable<Expression> boardExpressions){
        if (boardExpressions == null) {
            throw new IllegalArgumentException("boardExpressions can't be null");
        }
        this.boardExpressions=boardExpressions;
    }
}
