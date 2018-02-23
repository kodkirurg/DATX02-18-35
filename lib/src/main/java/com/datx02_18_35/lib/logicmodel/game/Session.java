package com.datx02_18_35.lib.logicmodel.game;

import com.datx02_18_35.lib.logicmodel.expression.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by robin on 2018-02-20.
 */

public class Session {
    private Stack<Scope> scopes = new Stack<>();
    private List<Expression> hypothesis = new ArrayList<>();

    public Session(List<Expression> hypothesis) {
        this.hypothesis.addAll(hypothesis);
        this.scopes.push(new Scope(hypothesis));
    }

    public void pushScope(Expression assumption) {
        scopes.push(new Scope(assumption));
    }

    public void closeScope() throws EmptyStackException {
        scopes.pop();
    }

    public Iterable<Scope> getScopes() {
        return scopes;
    }

    public Expression getAssumption() {
        return scopes.peek().getAssumption();
    }

    public Iterable<Expression> getGameBoard() {
        return scopes.peek().getGameBoard();
    }

    public Iterable<Expression> getInventory() {
        return new Iterable<Expression>() {
            @Override
            public Iterator<Expression> iterator() {
                return new Iterator<Expression>() {
                    private Iterator<Scope> scopeIter = scopes.iterator();
                    private Iterator<Expression> inventoryIter = scopeIter.next().getInventory().iterator();

                    @Override
                    public boolean hasNext() {
                        return inventoryIter.hasNext() || scopeIter.hasNext();
                    }

                    @Override
                    public Expression next() {
                        assert this.hasNext();
                        if (!inventoryIter.hasNext()) {
                            inventoryIter = scopeIter.next().getInventory().iterator();
                        }
                        return inventoryIter.next();
                    }
                };
            }
        };
    }

    public void addExpressionToGameBoard(List<Expression> expressions){
        scopes.peek().addExpressionToGameBoard(expressions);
    }
    public void addExpressionToGameBoard(Expression expression) {
        scopes.peek().addExpressionToGameBoard(expression);
    }

    public void addExpressionToInventory(Collection<Expression> expressions) {
        scopes.peek().addExpressionToInventory(expressions);
    }

    public void addExpressionToInventory(Expression expression) {
        scopes.peek().addExpressionToInventory(expression);
    }
}
