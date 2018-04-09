package com.datx02_18_35.model.expression;

/**
 * Created by Jonatan on 2018-04-09.
 */
import com.datx02_18_35.model.rules.Rule;
import com.datx02_18_35.model.rules.RuleType;

import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.datx02_18_35.model.rules.Rule.getLegalRules;
import static java.lang.Math.*;
import static org.junit.Assert.assertTrue;


public class getLegalRuleTest {
    final static String assumptionString="A";
    final static String propositonString1="P";
    final static String propositonString2="Q";
    final static String propositonString3="R";
    final static Map<String,String> emptySymbolMap= new HashMap<>();
    final static ExpressionFactory testExpressionFactory = new ExpressionFactory(emptySymbolMap);
    private final static Random rand= new Random();

    private String generateChar(){
        return Character.toString((char)('A'+rand.nextInt(('Z'-'A'))));
    }


    private Expression generateExpression(double chanceOfOperator){
        double rand1=rand.nextDouble();
        if(rand1<(1-chanceOfOperator)){
            if(random()<0.8){
                return testExpressionFactory.createProposition(generateChar());
            }else
                return testExpressionFactory.createAbsurdity();
        }
        else{
            double rand2=rand.nextDouble();
            Expression leftExpression = generateExpression(chanceOfOperator*0.95);
            Expression rightExpression = generateExpression(chanceOfOperator*0.95);
            if(rand2<1/3){
                return testExpressionFactory.createOperator(OperatorType.CONJUNCTION,leftExpression,rightExpression);
            }else if(1/3<rand2 && rand2<2/3){
                return testExpressionFactory.createOperator(OperatorType.DISJUNCTION,leftExpression,rightExpression);
            }else{
                return testExpressionFactory.createOperator(OperatorType.IMPLICATION,leftExpression,rightExpression);
            }
        }
    }


    @Test
    public void implicationIntroduction(){
        Expression expression1=generateExpression(0.2);
        Expression assumption=generateExpression(0.2);
        ArrayList<Expression> expressions=new ArrayList<>();
        expressions.add(expression1);
        Rule correctRule = new Rule(RuleType.IMPLICATION_INTRODUCTION,assumption,expression1);
        List<Rule> rules = getLegalRules(assumption,expressions);
        assertTrue(rules.contains(correctRule));

        Rule incorrectRule = new Rule(RuleType.IMPLICATION_INTRODUCTION,expression1,assumption);

        assertTrue(!rules.contains(incorrectRule));

    }
    @Test
    public void conjunctionElimination(){
        Expression expression1=generateExpression(0.2);
        Expression expression2=generateExpression(0.2);
        Conjunction conjunction= (Conjunction)testExpressionFactory.createOperator(OperatorType.CONJUNCTION,expression1,expression2);
        ArrayList<Expression> expressions=new ArrayList<>();
        expressions.add(conjunction);
        Rule correctRule= new Rule(RuleType.CONJUNCTION_ELIMINATION,conjunction);
        List<Rule> rules = getLegalRules(null,expressions);
        assertTrue(rules.contains(correctRule));


    }
    @Test
    public void absurdityElimination(){
        Absurdity absurdity = testExpressionFactory.createAbsurdity();
        ArrayList<Expression> expressions = new ArrayList<>();
        expressions.add(absurdity);
        Rule correctRule = new Rule(RuleType.ABSURDITY_ELIMINATION,absurdity,null);
        List<Rule> rules = getLegalRules(null,expressions);
        assertTrue(rules.contains(correctRule));

    }

    @Test
    public void disjunctionIntroduction(){
        Expression expression1=generateExpression(0.2);
        ArrayList<Expression> expressions= new ArrayList<>();
        expressions.add(expression1);
        Rule correctRule1 = new Rule(RuleType.DISJUNCTION_INTRODUCTION,null,expression1);
        Rule correctRule2 = new Rule(RuleType.DISJUNCTION_INTRODUCTION,expression1,null);
        List<Rule> rules = getLegalRules(null,expressions);
        assertTrue(rules.contains(correctRule1));
        assertTrue(rules.contains(correctRule2));

    }


    @Test
    public void conjunctionIntroduction(){
        Expression expression1 = generateExpression(0.2);
        Expression expression2 = generateExpression(0.2);
        ArrayList<Expression> expressions = new ArrayList<>();
        expressions.add(expression1);
        expressions.add(expression2);
        Rule correctRule = new Rule(RuleType.CONJUNCTION_INTRODUCTION,expression1,expression2);
        List<Rule> rules = getLegalRules(null,expressions);
        assertTrue(rules.contains(correctRule));



        Rule incorrectRule = new Rule(RuleType.CONJUNCTION_INTRODUCTION,expression2,expression1);
        if(!expression1.equals(expression2)){
            assertTrue(!rules.contains(incorrectRule));
        }
    }
    @Test
    public void implicationElimination(){
        Expression expression1 = generateExpression(0.2);
        Expression expression2 = generateExpression(0.2);
        Implication implication = (Implication)testExpressionFactory.createOperator(OperatorType.IMPLICATION,expression1,expression2);
        ArrayList<Expression> expressions = new ArrayList<>();
        expressions.add(expression1);
        expressions.add(implication);
        Rule correctRule = new Rule(RuleType.IMPLICATION_ELIMINATION,implication,expression1);
        List<Rule> rules = getLegalRules(null,expressions);
        assertTrue(rules.contains(correctRule));




    }
    @Test
    public void disjunctionElimination(){
        Expression expression1 = generateExpression(0.2);
        Expression expression2 = generateExpression(0.2);
        Expression expression3 = generateExpression(0.2);
        Disjunction disjunction = (Disjunction)testExpressionFactory.createOperator(OperatorType.DISJUNCTION,expression1,expression2);
        Implication implication1 = (Implication)testExpressionFactory.createOperator(OperatorType.IMPLICATION,expression1,expression3);
        Implication implication2 = (Implication) testExpressionFactory.createOperator(OperatorType.IMPLICATION,expression2,expression3);
        ArrayList<Expression> expressions = new ArrayList<>();
        expressions.add(disjunction);
        expressions.add(implication1);
        expressions.add(implication2);
        Rule correctRule = new Rule(RuleType.DISJUNCTION_ELIMINATION,disjunction,implication1,implication2);
        List<Rule> rules = getLegalRules(null,expressions);
        assertTrue(rules.contains(correctRule));

    }


}
