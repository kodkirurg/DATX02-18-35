package com.datx02_18_35.model.expression;

import java.io.IOException;

/**
 * Created by robin on 2018-02-07.
 */

public class Proposition extends Expression {

    private final String id;
    private String symbol;

    Proposition(String id, String symbol) {
        super();
        this.id = id;
        this.symbol = symbol;
        hash=calculateHash();
    }
    Proposition(String id) {
        super();
        this.id = id;
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
        return "("+ this.id + ")";
    }

    public String getSymbol() {
        return symbol;
    }
}
