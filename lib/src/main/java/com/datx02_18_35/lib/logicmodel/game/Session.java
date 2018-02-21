package com.datx02_18_35.lib.logicmodel.game;

import com.datx02_18_35.lib.logicmodel.expression.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
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

    public void addExpressionToGameBoard(Collection<Expression> expressions){}
    public List<Expression>  getExpressionsOnGameBoard(){return null;}
    public List<Expression>  getExpressionsInInventory(){return null;}
    public Expression getCurrentAssumption(){return null;}


}
