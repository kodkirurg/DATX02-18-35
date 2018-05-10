package com.datx02_18_35.model.expression;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ExpressionFactoryTest {

    final static String propositionAsString1 = "abc123ABC&#";
    final static Map<String,String> SymbolMap=getSymbolMap();
    final static ExpressionFactory expressionFactory  = new ExpressionFactory(SymbolMap);

    private static Map<String,String> getSymbolMap(){
        Map<String,String> symbolMap = new HashMap<>();
        for(int i='A';i<='Z';i++){
            symbolMap.put(Character.toString((char)i),"Sun");
        }
        return symbolMap;
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

}