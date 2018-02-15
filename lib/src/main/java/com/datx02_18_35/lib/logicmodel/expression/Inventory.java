package com.datx02_18_35.lib.logicmodel.expression;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jonatan on 2018-02-15.
 */

public class Inventory {
    private List<Set<Expression>> inventoryList;
    private int length;
    Inventory(){
        inventoryList=new ArrayList<>();
        inventoryList.add(new HashSet<>());
    }

    public void addExpression(Collection<Expression> expressions){
        ADD_NEXT:
        for (Expression expr : expressions) {
             for (Set<Expression> scopeInventory : inventoryList) {
                 if (scopeInventory.contains(expr)) {
                     continue ADD_NEXT;
                 }
             }
            inventoryList.get(inventoryList.size()).add(expr);
        }
    }
    public void addScope(){
        inventoryList.add(new HashSet<>());
    }
    public void removeScope(){
        inventoryList.remove(inventoryList.size());
    }
    public List<Set<Expression>> getInventory(){
        return inventoryList;
    }

}
