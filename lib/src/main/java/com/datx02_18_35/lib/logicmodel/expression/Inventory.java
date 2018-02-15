package com.datx02_18_35.lib.logicmodel.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Jonatan on 2018-02-15.
 */

public class Inventory {
    private Stack<Set<Expression>> inventories;
    private int length;
    Inventory(){
        inventories =new Stack<>();
        inventories.push(new HashSet<>());
    }

    public void addExpression(Expression expression) {
        Collection<Expression> col = new ArrayList<>();
        col.add(expression);
        addExpressions(col);
    }

    public void addExpressions(Collection<Expression> expressions){
        ADD_NEXT:
        for (Expression expr : expressions) {
             for (Set<Expression> scopeInventory : inventories) {
                 if (scopeInventory.contains(expr)) {
                     continue ADD_NEXT;
                 }
             }
            inventories.peek().add(expr);
        }
    }
    public void addScope(){
        inventories.add(new HashSet<>());
    }
    public void removeScope(){
        inventories.remove(inventories.size());
    }
    public List<Set<Expression>> getInventory(){
        return inventories;
    }

}
