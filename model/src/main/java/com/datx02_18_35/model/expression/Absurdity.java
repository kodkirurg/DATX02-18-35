package com.datx02_18_35.model.expression;

/**
 * Created by robin on 2018-02-07.
 */

public class Absurdity extends Expression {
    Absurdity() {
        super();
        this.hash = calculateHash();
    }

    @Override
    protected int calculateHash() {
        return (int)ExpressionUtil.HASH_ABSU_MAGIC_NUMBER;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Absurdity;
    }

    @Override
    public String toString(){
        return "#";
    }


}
