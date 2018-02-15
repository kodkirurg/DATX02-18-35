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
        length=0;
    }

    public void addExpression(Expression expr){
         boolean exists=false;
         int i=0;
        while(!exists && i<length-1){
            if(inventoryList.get(i).contains(expr)){
                exists=true;
            }
        }
        if(!exists){
            inventoryList.get(length).add(expr);
        }
    }
    public void addScope(){
        inventoryList.add(new HashSet<Expression>());
        length+=1;
    }
    public void removeScope(){
        inventoryList.remove(length);
        length-=1;
    }
    public List<Collection<Expression>> getInventory(){
        return inventoryList;
    }

}
