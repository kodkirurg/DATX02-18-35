package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.model.expression.Expression;

import java.util.List;

/**
 * Created by raxxor on 2018-03-11.
 */

public class RequestExpressionSelectionAction extends Action {

    public final List<Expression> list;

    public RequestExpressionSelectionAction(List<Expression> list){
        this.list=list;
    }
}
