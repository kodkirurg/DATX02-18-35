package com.datx02_18_35.lib.logicmodel.expression;

/**
 * Created by Johan on 2018-02-14.
 */

public class Assumption extends Expression {

    protected Expression expression;

    protected int calculateHash(){
        return  (int)(expression.hashCode()*ExpressionUtil.HASH_ASSU_MAGIC_NUMBER);
    }

    @Override
    public boolean equals(Object other){
        return (other instanceof Assumption) && (expression.equals(((Assumption)other).expression));
    }



}
