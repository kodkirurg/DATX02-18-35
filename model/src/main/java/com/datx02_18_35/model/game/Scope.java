package com.datx02_18_35.model.game;

import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.expression.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by robin on 2018-02-20.
 */

public class Scope {
    private Expression assumption;
    private Set<Expression> inventory = new HashSet<>();
    private List<Expression> gameBoard = new ArrayList<>();

    Scope(Expression assumption) {
        assert assumption != null;
        this.assumption = assumption;
        gameBoard.add(assumption);
        Util.log("Expression was added as assumption: " + assumption);
    }

    Scope(List<Expression> hypothesis) {
        assert hypothesis != null;
        this.assumption = null;
        gameBoard.addAll(hypothesis);
        for (Expression expression : hypothesis) {
            Util.log("Expression was added to hypothesis: " + expression);
        }
    }

    public Expression getAssumption() {
        return assumption;
    }

    public Iterable<Expression> getGameBoard() {
        return gameBoard;
    }

    public Iterable<Expression> getInventory() {
        return inventory;
    }

    void addExpressionToGameBoard(Expression expression) {
        assert expression != null;
        this.gameBoard.add(expression);
    }

    void addExpressionToGameBoard(List<Expression> expressions) {
        assert expressions != null;
        this.gameBoard.addAll(expressions);
    }

    void addExpressionToInventory(Expression expression) {
        assert expression != null;
        this.inventory.add(expression);
        Util.log("Expression was added to inventory in: " + expression);
    }

    void addExpressionToInventory(Collection<Expression> expressions) {
        assert expressions != null;
        this.inventory.addAll(expressions);
        for (Expression expression : expressions) {
            Util.log("Expression was added to inventory in: " + expression);
        }
    }
    void removeExpressionFromGameBoard(Collection<Expression> expressions){
        assert expressions != null;
        for (Expression expr : expressions){
            gameBoard.remove(expr);
        }
    }
}
