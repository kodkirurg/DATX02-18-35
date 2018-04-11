package com.datx02_18_35.model.expression;

/**
 * Created by robin on 2018-02-07.
 */

public class Proposition extends Expression {

    private final String id;
    private final String symbol;

    Proposition(String id, String symbol) {
        if (id == null) {
            throw new IllegalArgumentException("id can't be null");
        }
        if (symbol == null) {
            throw new IllegalArgumentException("symbol can't be null");
        }
        this.id = id;
        this.symbol = symbol;
        hash=calculateHash();
    }


    @Override
    public int calculateHash() {
        return (int)(id.hashCode() * ExpressionUtil.HASH_PROP_MAGIC_NUMBER);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Proposition && id.equals(((Proposition) other).id);
    }

    public String toString(){
        return this.id + "(" + this.symbol + ")";
    }

    public String getSymbol() {
        return symbol;
    }
}
