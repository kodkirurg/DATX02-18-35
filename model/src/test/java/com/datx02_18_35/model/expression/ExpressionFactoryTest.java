package com.datx02_18_35.model.expression;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionFactoryTest {

    final static String propositionAsString1 = "abc123ABC&#";
    final static String propositionAsString2 = "oasd93Äå";
    final static ExpressionFactory expressionFactory = new ExpressionFactory(null);

    @Test
    public void getSymbolMap() {
    }

    @Test
    public void createProposition() {
        Proposition proposition = expressionFactory.createProposition(propositionAsString1);

        assertTrue(proposition != null);
        assertTrue(proposition.toString().equals( "(" + propositionAsString1 +")" ));
        assertTrue (proposition.getSymbol()==null);
        assertTrue(proposition.calculateHash() != 0);
    }

    @Test
    public void createPropositionWithMap() {
    }

    @Test
    public void createAbsurdity() {
        Absurdity absurdity = expressionFactory.createAbsurdity();

        assertTrue( absurdity != null);
        assertTrue(absurdity.toString().equals("(#)"));
    }

    @Test
    public void createNegation() {
        Expression negation = expressionFactory.createNegation(expressionFactory.createProposition(propositionAsString1));
        Expression proposition = expressionFactory.createProposition(propositionAsString1);


        //assert simple propsition is negated
        assert negation instanceof Negation;
        assert negation.toString().equals("(!" + proposition.toString() + ")");
        assert negation.calculateHash() != 0;
        
    }

    @Test
    public void createOperator() {
    }

    @Test
    public void applyRule() {
    }
}