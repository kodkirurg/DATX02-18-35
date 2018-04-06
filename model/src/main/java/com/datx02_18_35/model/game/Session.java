package com.datx02_18_35.model.game;

import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.rules.IllegalRuleException;
import com.datx02_18_35.model.rules.Rule;
import com.datx02_18_35.model.rules.RuleType;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.rules.TestRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by robin on 2018-02-20.
 */

public class Session {
    private Stack<Scope> scopes;
    private Level level;
    private int stepsApplied;

    public Session(Level level) {
        this.level = level;
        this.scopes = new Stack<>();
        this.scopes.push(new Scope(level.hypothesis));
        this.stepsApplied = 0;
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

    public Iterable<Iterable<Expression>> getInventories(){
        final Iterator<Scope> scopesIterator = this.getScopes().iterator();
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
    public Iterable<Expression> getAssumptions(){
        final Iterator<Scope> scopesIterator = this.getScopes().iterator();

        // Skip the first scope
        scopesIterator.next();


        return new Iterable<Expression>() {
            @Override
            public Iterator<Expression> iterator() {

                return new Iterator<Expression>() {
                    @Override
                    public boolean hasNext() {
                        return scopesIterator.hasNext();
                    }

                    @Override
                    public Expression next() {
                        return scopesIterator.next().getAssumption();
                    }
                };
            }
        };
    }

    public Iterable<Expression> getHypotheses(){
        return new Iterable<Expression>() {
            @Override
            public Iterator<Expression> iterator() {
                return level.hypothesis.iterator();
            }
        };
    }

     public Iterable<Expression> getAllExpressions() {
        return new Iterable<Expression>() {
            @Override
            public Iterator<Expression> iterator() {
                return new Iterator<Expression>() {
                    private Iterator<Scope> scopeIter = scopes.iterator();
                    private Iterator<Expression> currentIter = scopeIter.next().getInventory().iterator();
                    private Iterator<Expression> assumptionIter = getAssumptions().iterator();
                    private Iterator<Expression> hypothesisIter = getHypotheses().iterator();

                    @Override
                    public boolean hasNext() {
                        return currentIter.hasNext() || scopeIter.hasNext() || assumptionIter.hasNext() || hypothesisIter.hasNext() ;
                    }

                    @Override
                    public Expression next() {
                        Boolean newIter = false;
                        assert this.hasNext();
                        if (!currentIter.hasNext()) {
                            if (scopeIter.hasNext()){
                                Iterator<Expression> testIter = scopeIter.next().getInventory().iterator();
                                if(testIter.hasNext()) {
                                    currentIter = testIter;
                                    newIter=true;
                                }
                            }if (assumptionIter.hasNext()&&!newIter) {
                                currentIter = assumptionIter;

                                newIter=true;
                            }if(hypothesisIter.hasNext()&&!newIter){
                                currentIter = hypothesisIter;
                            }
                        }
                        return currentIter.next();
                    }
                };
            }
        };
    }

    public Level getLevel(){return this.level;}


    public void addExpressionToGameBoard(Collection<Expression> expressions){
        addExpressionToGameBoard(new ArrayList<Expression>(expressions));
    }
    public void addExpressionToGameBoard(List<Expression> expressions){
        assert isExpressionInScope(expressions);
        scopes.peek().addExpressionToGameBoard(expressions);
    }
    public void addExpressionToGameBoard(Expression expression) {
        assert isExpressionInScope(expression);
        scopes.peek().addExpressionToGameBoard(expression);
    }
    public void removeExpressionFromGameBoard(Collection<Expression> expressions){
        scopes.peek().removeExpressionFromGameBoard(expressions);
    }
    public void addExpressionToInventory(Collection<Expression> expressions) {
        scopes.peek().addExpressionToInventory(expressions);
    }

    public void addExpressionToInventory(Expression expression) {
        scopes.peek().addExpressionToInventory(expression);
    }

    public void makeAssumption(Expression expression) {
        this.scopes.push(new Scope(expression));
    }

    public List<Expression> applyRule(Rule rule) throws IllegalRuleException {
        TestRule.assertRuleIsLegal(this, rule);
        if (!level.ruleSet.contains(rule.type)) {
            throw new IllegalRuleException(rule, "The current level does not allow rules of this type.");
        }
        List<Expression> expressions = level.expressionFactory.applyRule(rule);
        if(rule.type == RuleType.IMPLICATION_INTRODUCTION){
            this.closeScope();
        }
        this.addExpressionToInventory(expressions);
        this.addExpressionToGameBoard(expressions);
        this.stepsApplied += 1;
        return expressions;
    }


    public Rule finishIncompleteRule(Rule rule, Expression expression) throws IllegalRuleException {
        return Rule.finishIncompleteRule(rule,expression);
    }

    public List<Rule> getLegalRules(List<Expression> expressions){
        return Rule.filterRules(Rule.getLegalRules(getAssumption(),expressions),level.ruleSet);
    }

    public boolean checkWin(){
        assert scopes.size()!=0;
        assert scopes!=null;
        if(scopes.size()>1) {
            return false;
        }
        for (Expression e: scopes.peek().getInventory()){
            if(e.equals(level.goal)){
                return true;
            }
        }
        return false;
    }

    public int getStepsApplied() {
        return this.stepsApplied;
    }


    public int getScopeInt(){
        int i=0;
        for(Scope scopes:this.getScopes()){
            i++;
        }
        return i;
    }

    public boolean isExpressionInScope(Expression expression) {
        for (Expression existingExpression : getAllExpressions()){
            if(existingExpression.equals(expression)){
                return true;
            }
        }
        return false;
    }

    public boolean isExpressionInScope(Collection<Expression> expressions){
        Collection<Expression> testExpressions = new HashSet<>(expressions);
        for (Expression existingExpression : getAllExpressions()){
            testExpressions.remove(existingExpression);
        }
        return testExpressions.isEmpty();
    }

}
