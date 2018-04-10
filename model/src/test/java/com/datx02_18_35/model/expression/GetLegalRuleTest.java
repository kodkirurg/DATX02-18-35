package com.datx02_18_35.model.expression;

/**
 * Created by Jonatan on 2018-04-09.
 */
import com.datx02_18_35.model.rules.Rule;
import com.datx02_18_35.model.rules.RuleType;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.datx02_18_35.model.rules.Rule.getLegalRules;
import static java.lang.Math.*;
import static org.junit.Assert.assertTrue;


public class GetLegalRuleTest {
    final static String assumptionString="A";
    final static String propositonString1="P";
    final static String propositonString2="Q";
    final static String propositonString3="R";
    final static Map<String,String> emptySymbolMap= new HashMap<>();
    final static ExpressionFactory testExpressionFactory = new ExpressionFactory(emptySymbolMap);
    private final static Random rand= new Random();

    public static String generateChar(){
        return Character.toString((char)('A'+rand.nextInt(('Z'-'A'))));
    }

    public static Expression generateExpression(double chanceOfOperator){
        double rand1=rand.nextDouble();
        if(rand1<(1.0-chanceOfOperator)){
            if(random()<0.8){
                return testExpressionFactory.createProposition(generateChar());
            }else
                return testExpressionFactory.createAbsurdity();
        }
        else{
            double rand2=rand.nextDouble();
            Expression leftExpression = generateExpression(chanceOfOperator*0.95);
            Expression rightExpression = generateExpression(chanceOfOperator*0.95);
            if(rand2<1.0/3.0){
                return testExpressionFactory.createOperator(OperatorType.CONJUNCTION,leftExpression,rightExpression);
            }else if(1.0/3.0<rand2 && rand2<2.0/3.0){
                return testExpressionFactory.createOperator(OperatorType.DISJUNCTION,leftExpression,rightExpression);
            }else{
                return testExpressionFactory.createOperator(OperatorType.IMPLICATION,leftExpression,rightExpression);
            }
        }
    }

    public static List<Rule> generateAlwaysPossibleRules(List<Expression> expressions) {
        List<Rule> generatedRules = new ArrayList<>();
        switch (expressions.size()){
            case 0: {
                generatedRules.add(new Rule(RuleType.LAW_OF_EXCLUDED_MIDDLE, (Expression) null));
                break;
            }
            case 1:{
                generatedRules.add(new Rule(RuleType.DISJUNCTION_INTRODUCTION,expressions.get(0),(Expression)null));
                generatedRules.add(new Rule(RuleType.DISJUNCTION_INTRODUCTION,(Expression)null,expressions.get(0)));
                break;

            }
            case 2:{
                generatedRules.add(new Rule(RuleType.CONJUNCTION_INTRODUCTION,expressions.get(0),expressions.get(1)));
                break;
            }

        }

        return generatedRules;
    }



    public static List<Rule> generateAllRules(List<Expression> expressions,boolean hasBeenRecursivelyCalled){
        List<Rule> generatedRules = new ArrayList<>();

        ArrayList<RuleType> rulesOfTypeOne = new ArrayList<>();
        rulesOfTypeOne.add(RuleType.ABSURDITY_ELIMINATION);
        rulesOfTypeOne.add(RuleType.CONJUNCTION_ELIMINATION);
        rulesOfTypeOne.add(RuleType.IMPLICATION_INTRODUCTION);
        rulesOfTypeOne.add(RuleType.DISJUNCTION_INTRODUCTION);

        ArrayList<RuleType> rulesOfTypeTwo = new ArrayList<>();
        rulesOfTypeTwo.add(RuleType.CONJUNCTION_INTRODUCTION);
        rulesOfTypeTwo.add(RuleType.IMPLICATION_ELIMINATION);

        ArrayList<RuleType> rulesOfTypeThree = new ArrayList<>();
        rulesOfTypeThree.add(RuleType.DISJUNCTION_ELIMINATION);

        switch (expressions.size()){
            case 0:{
                Rule lem=new Rule(RuleType.LAW_OF_EXCLUDED_MIDDLE,(Expression)null);
                generatedRules.add(lem);
                break;
            }
            case 1:{
                for(RuleType ruleType:rulesOfTypeOne){
                    if(ruleType==RuleType.DISJUNCTION_INTRODUCTION){
                        generatedRules.add(new Rule(RuleType.DISJUNCTION_INTRODUCTION,expressions.get(0),null));
                        generatedRules.add(new Rule(RuleType.DISJUNCTION_INTRODUCTION,null,expressions.get(0)));
                    }else
                        generatedRules.add(new Rule(ruleType,expressions.get(0)));
                }
                break;
            }
            case 2:{
                for (RuleType ruleType:rulesOfTypeTwo){
                    generatedRules.add(new Rule(ruleType,expressions.get(0),expressions.get(1)));
                }
                if(!hasBeenRecursivelyCalled) {
                    List<Expression> reversedExpressions = new ArrayList<>(expressions);
                    Collections.reverse(reversedExpressions);
                    List<Rule> reversedRules = generateAllRules(reversedExpressions, true);
                    generatedRules.addAll(reversedRules);
                }
                break;
            }
            case 3:{
                for (RuleType ruleType:rulesOfTypeThree){
                    generatedRules.add(new Rule(ruleType,expressions.get(0),expressions.get(1),expressions.get(2)));
                }

                if(!hasBeenRecursivelyCalled) {
                    List<Expression> expressionsOrdered021 = new ArrayList<>();
                    expressionsOrdered021.add(expressions.get(0));
                    expressionsOrdered021.add(expressions.get(2));
                    expressionsOrdered021.add(expressions.get(1));
                    generatedRules.addAll(generateAllRules(expressionsOrdered021, true));

                    List<Expression> expressionsOrdered102 = new ArrayList<>();
                    expressionsOrdered021.add(expressions.get(1));
                    expressionsOrdered021.add(expressions.get(0));
                    expressionsOrdered021.add(expressions.get(2));
                    generatedRules.addAll(generateAllRules(expressionsOrdered102, true));

                    List<Expression> expressionsOrdered120 = new ArrayList<>();
                    expressionsOrdered021.add(expressions.get(1));
                    expressionsOrdered021.add(expressions.get(2));
                    expressionsOrdered021.add(expressions.get(0));
                    generatedRules.addAll(generateAllRules(expressionsOrdered120, true));

                    List<Expression> expressionsOrdered201 = new ArrayList<>();
                    expressionsOrdered021.add(expressions.get(2));
                    expressionsOrdered021.add(expressions.get(0));
                    expressionsOrdered021.add(expressions.get(1));
                    generatedRules.addAll(generateAllRules(expressionsOrdered201, true));

                    List<Expression> expressionsOrdered210 = new ArrayList<>();
                    expressionsOrdered021.add(expressions.get(2));
                    expressionsOrdered021.add(expressions.get(1));
                    expressionsOrdered021.add(expressions.get(0));
                    generatedRules.addAll(generateAllRules(expressionsOrdered210, true));
                }
                break;
            }
        }
        return generatedRules;
    }


    @Test
    public void implicationIntroduction(){
        for (int reps=0; reps<100000; reps++){
            Expression expression1=generateExpression(0.2);
            Expression assumption=generateExpression(0.2);
            ArrayList<Expression> expressions=new ArrayList<>();
            expressions.add(expression1);
            Rule correctRule = new Rule(RuleType.IMPLICATION_INTRODUCTION,assumption,expression1);
            List<Rule> rules = getLegalRules(assumption,expressions);
            assertTrue(rules.contains(correctRule));

            List<Rule> incorrectRules = generateAllRules(expressions,false);
            incorrectRules.remove(correctRule);
            incorrectRules.removeAll(generateAlwaysPossibleRules(expressions));

            assertTrue(!rules.containsAll(incorrectRules));
        }

    }
    @Test
    public void conjunctionElimination(){
        for(int reps=0;reps<100000;reps++) {
            Expression expression1 = generateExpression(0.2);
            Expression expression2 = generateExpression(0.2);
            Conjunction conjunction = (Conjunction) testExpressionFactory.createOperator(OperatorType.CONJUNCTION, expression1, expression2);
            ArrayList<Expression> expressions = new ArrayList<>();
            expressions.add(conjunction);
            Rule correctRule = new Rule(RuleType.CONJUNCTION_ELIMINATION, conjunction);
            List<Rule> rules = getLegalRules(null, expressions);
            assertTrue(rules.contains(correctRule));

            List<Rule> incorrectRules = generateAllRules(expressions,false);
            incorrectRules.remove(correctRule);
            incorrectRules.removeAll(generateAlwaysPossibleRules(expressions));
            assertTrue(!rules.containsAll(incorrectRules));
        }


    }
    @Test
    public void absurdityElimination(){
        for(int reps=0;reps<100000;reps++) {
            Absurdity absurdity = testExpressionFactory.createAbsurdity();
            ArrayList<Expression> expressions = new ArrayList<>();
            expressions.add(absurdity);
            Rule correctRule = new Rule(RuleType.ABSURDITY_ELIMINATION, absurdity, null);
            List<Rule> rules = getLegalRules(null, expressions);
            assertTrue(rules.contains(correctRule));

            List<Rule> incorrectRules = generateAllRules(expressions,false);
            incorrectRules.remove(correctRule);
            incorrectRules.removeAll(generateAlwaysPossibleRules(expressions));
            assertTrue(!rules.containsAll(incorrectRules));
        }

    }

    @Test
    public void disjunctionIntroduction(){
        for(int reps=0;reps<100000;reps++) {
            Expression expression1 = generateExpression(0.2);
            ArrayList<Expression> expressions = new ArrayList<>();
            expressions.add(expression1);
            Rule correctRule1 = new Rule(RuleType.DISJUNCTION_INTRODUCTION, null, expression1);
            Rule correctRule2 = new Rule(RuleType.DISJUNCTION_INTRODUCTION, expression1, null);
            List<Rule> rules = getLegalRules(null, expressions);
            assertTrue(rules.contains(correctRule1));
            assertTrue(rules.contains(correctRule2));

            List<Rule> incorrectRules = generateAllRules(expressions,false);
            incorrectRules.removeAll(generateAlwaysPossibleRules(expressions));
            assertTrue(!rules.containsAll(incorrectRules));
        }

    }


    @Test
    public void conjunctionIntroduction(){
        for(int reps=0;reps<100000;reps++) {
            Expression expression1 = generateExpression(0.2);
            Expression expression2 = generateExpression(0.2);
            ArrayList<Expression> expressions = new ArrayList<>();
            expressions.add(expression1);
            expressions.add(expression2);
            Rule correctRule = new Rule(RuleType.CONJUNCTION_INTRODUCTION, expression1, expression2);
            List<Rule> rules = getLegalRules(null, expressions);
            assertTrue(rules.contains(correctRule));

            List<Rule> incorrectRules = generateAllRules(expressions,false);
            incorrectRules.removeAll(generateAlwaysPossibleRules(expressions));
            assertTrue(!rules.containsAll(incorrectRules));
        }
    }
    @Test
    public void implicationElimination(){
        for(int reps=0;reps<100000;reps++) {
            Expression expression1 = generateExpression(0.2);
            Expression expression2 = generateExpression(0.2);
            Implication implication = (Implication) testExpressionFactory.createOperator(OperatorType.IMPLICATION, expression1, expression2);
            ArrayList<Expression> expressions = new ArrayList<>();
            expressions.add(expression1);
            expressions.add(implication);
            Rule correctRule = new Rule(RuleType.IMPLICATION_ELIMINATION, implication, expression1);
            List<Rule> rules = getLegalRules(null, expressions);
            assertTrue(rules.contains(correctRule));

            List<Rule> incorrectRules = generateAllRules(expressions,false);
            incorrectRules.remove(correctRule);
            incorrectRules.removeAll(generateAlwaysPossibleRules(expressions));

            assertTrue(!rules.containsAll(incorrectRules));
        }




    }
    @Test
    public void disjunctionElimination(){
        for(int reps=0;reps<100000;reps++) {
            Expression expression1 = generateExpression(0.2);
            Expression expression2 = generateExpression(0.2);
            Expression expression3 = generateExpression(0.2);
            Disjunction disjunction = (Disjunction) testExpressionFactory.createOperator(OperatorType.DISJUNCTION, expression1, expression2);
            Implication implication1 = (Implication) testExpressionFactory.createOperator(OperatorType.IMPLICATION, expression1, expression3);
            Implication implication2 = (Implication) testExpressionFactory.createOperator(OperatorType.IMPLICATION, expression2, expression3);
            ArrayList<Expression> expressions = new ArrayList<>();
            expressions.add(disjunction);
            expressions.add(implication1);
            expressions.add(implication2);
            Rule correctRule = new Rule(RuleType.DISJUNCTION_ELIMINATION, disjunction, implication1, implication2);
            List<Rule> rules = getLegalRules(null, expressions);
            assertTrue(rules.contains(correctRule));

            List<Rule> incorrectRules = generateAllRules(expressions,false);
            incorrectRules.remove(correctRule);
            incorrectRules.removeAll(generateAlwaysPossibleRules(expressions));
            assertTrue(!rules.containsAll(incorrectRules));
        }

    }


}
