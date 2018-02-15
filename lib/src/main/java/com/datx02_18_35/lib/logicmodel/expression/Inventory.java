package com.datx02_18_35.lib.logicmodel.expression;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Jonatan on 2018-02-15.
 */

public class Inventory {
    private List<Collection<Expression>> inventoryList;
    private int length;
    private Collection<Expression> inventoryHash;
    Inventory(){
        inventoryList=new ArrayList<>();
        inventoryHash=new HashSet<>();
        inventoryList.add(inventoryHash);
    }

    public void addExpression(Collection<Expression> expr){
        assert (expr.size()>=1);
        ArrayList<Expression> exprArrayList = (ArrayList<Expression>)expr;
         for (int i=0; i<exprArrayList.size();i++) {
             boolean exists=false;
             int j = 0;
             while (!exists && j < inventoryList.size() - 1) {
                 if (inventoryList.get(j).contains(exprArrayList.get(i))) {
                     exists = true;
                 }
             }
             if (!exists) {
                 inventoryList.get(inventoryList.size()).add(exprArrayList.get(i));
             }
         }
    }
    public void addScope(){
        inventoryList.add(new HashSet<Expression>());
    }
    public void removeScope(){
        inventoryList.remove(inventoryList.size());
    }
    public List<Collection<Expression>> getInventory(){
        return inventoryList;
    }

}
