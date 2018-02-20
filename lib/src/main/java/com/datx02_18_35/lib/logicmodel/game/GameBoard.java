package com.datx02_18_35.lib.logicmodel.game;

import com.datx02_18_35.lib.logicmodel.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 2018-02-20.
 */

public class GameBoard {
    List<Expression> expressions = new ArrayList<>();

    GameBoard(Expression expression) {
        this.expressions.add(expression);
    }

    GameBoard(List<Expression> expressions) {
        this.expressions.addAll(expressions);
    }
}
