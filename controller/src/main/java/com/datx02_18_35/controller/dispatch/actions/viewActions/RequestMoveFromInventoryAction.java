package com.datx02_18_35.controller.dispatch.actions.viewActions;

import com.datx02_18_35.controller.dispatch.ActionConsumer;
import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.expression.Expression;

/**
 * Created by Jonatan on 2018-03-27.
 */

public class RequestMoveFromInventoryAction extends Action {
    public final Expression expression;

    public RequestMoveFromInventoryAction(ActionConsumer callback,Expression expression){
        super(callback);
        if(callback==null){
            throw new IllegalArgumentException("callback can't be null");
        }
        if(expression==null){
            throw new IllegalArgumentException("Expression cant be null");
        }
        this.expression=expression;
    }
}
