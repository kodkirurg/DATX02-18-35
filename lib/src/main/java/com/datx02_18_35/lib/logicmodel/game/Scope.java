package com.datx02_18_35.lib.logicmodel.game;

import com.datx02_18_35.lib.logicmodel.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 2018-02-20.
 */

public class Scope {
    private Inventory inventory;
    private GameBoard gameBoard;

    public Scope(Expression assumption) {
        inventory = new Inventory(assumption);
        gameBoard = new GameBoard(assumption);
    }

    public Scope(List<Expression> hypothesis) {
        inventory = new Inventory();
        gameBoard = new GameBoard(hypothesis);
    }

    public GameBoard getGameBoard(){return null;}

}
