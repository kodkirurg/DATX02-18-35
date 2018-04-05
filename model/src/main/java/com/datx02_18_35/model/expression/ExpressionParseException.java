package com.datx02_18_35.model.expression;

import com.datx02_18_35.model.GameException;

/**
 * Created by robin on 2018-03-28.
 */

public class ExpressionParseException extends GameException {
    public ExpressionParseException(String string){
        super(string);
    }
}
