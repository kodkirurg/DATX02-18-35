package com.datx02_18_35.lib.logicmodel.game;

import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.ExpressionFactory;
import com.datx02_18_35.lib.logicmodel.expression.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by robin on 2018-02-20.
 */

public class Session {
    private Stack<Scope> scopes = new Stack<>();
    private List<Expression> hypothesis = new ArrayList<>();
    private Expression goal;
    private ExpressionFactory expFactory;

    public Session(List<Expression> hypothesis,Expression goal) {
        this.hypothesis.addAll(hypothesis);
        this.scopes.push(new Scope(hypothesis));
        this.goal=goal;
        expFactory = ExpressionFactory.getSingleton();
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

    public Iterable<Iterable<Expression>> getInventorys(){
        Iterable<Scope> scopesIter = getScopes();
        Iterator<Scope> scopesIterator = scopesIter.iterator();
        return new Iterable<Iterable<Expression>>() {
            @Override
            public Iterator<Iterable<Expression>> iterator() {
                return new Iterator<Iterable<Expression>>() {
                    @Override
                    public boolean hasNext() {
                        return scopesIterator.hasNext();
                    }

                    @Override
                    public Iterable<Expression> next() {
                        assert this.hasNext();
                        return scopesIterator.next().getInventory();
                    }
                };
            }
        };
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

    public void makeAssumption(){
        throw new NotImplementedException();
    }

    public Collection<Rule> getAvailableRules(Collection<Expression> expressions){
        Collection<Rule> rules=Rule.getLegalRules(this.getAssumption(),expressions);
        return rules;
    }

    public void applyRule(Rule rule){
        this.addExpressionToInventory(expFactory.applyRule(rule));
    }

    public boolean checkWin(){
        assert scopes.size()!=0;
        assert scopes!=null;
        if(scopes.size()>1) {
            return false;
        }
        Scope scope = this.getScopes().iterator().next();
        Iterable<Expression> inventory = scope.getInventory();
        for (Expression e: inventory){
            if(e.equals(goal)){
                return true;
            }
        }
        return false;

    }
    private Expression createExpression(){
        throw new NotImplementedException();
    }




}
