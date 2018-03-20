package com.datx02_18_35.model.expression;

import java.io.Serializable;

/**
 * Created by robin on 2018-02-07.
 */

public abstract class Expression implements Serializable {

    protected int hash;

    @Override
    public final int hashCode() {
        return hash;
    }

    protected abstract int calculateHash();

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();


}
