package com.datx02_18_35.model.game;

import com.datx02_18_35.model.ExpressionParser;
import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.expression.Operator;
import com.datx02_18_35.model.expression.OperatorType;
import com.datx02_18_35.model.expression.Proposition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class Level {
    private static final String SYMBOL="SYMBOL";
    private static final String HYPOTHESIS ="HYPOTHESIS";
    private static final String GOAL="GOAL";
    private static final String TITLE="TITLE";

    public final List<Expression> hypothesis;
    public final String title;
    public final Expression goal;
    public final ExpressionFactory expressionFactory;
    public final List<Proposition> propositions;

    private boolean isLevelComplete;
    
    private Level(String title,List<Expression> hypothesis, Expression goal,ExpressionFactory expressionFactory){
        this.hypothesis=hypothesis;
        this.goal=goal;
        this.title=title;
        this.expressionFactory=expressionFactory;
        this.isLevelComplete=false;
        this.propositions = new ArrayList<>(extractPropositions(goal, hypothesis));
    }

    public boolean isLevelComplete(){
        return isLevelComplete;
    }

    public ExpressionFactory getExpressionFactory() {
        return this.expressionFactory;
    }

    void completeLevel(){
        isLevelComplete=true;
    }

    /////////////////////////////////////////////
    // STATIC FUNCTIONS /////////////////////////
    /////////////////////////////////////////////

    private static Set<Proposition> extractPropositions(Expression goal, List<Expression> hypothesis) {
        Set<Proposition> propSet = new HashSet<>();
        propSet.addAll((extractPropositions(goal)));
        for (Expression expr : hypothesis) {
            propSet.addAll(extractPropositions(expr));
        }
        return propSet;
    }
    private static Set<Proposition> extractPropositions(Expression expression) {
        Set<Proposition> propSet = new HashSet<>();
        if (expression instanceof Proposition) {
            Proposition proposition = (Proposition)expression;
            propSet.add(proposition);
        } else if (expression instanceof Operator){
            Operator operator = (Operator)expression;
            propSet.addAll(extractPropositions(operator.getOperand1()));
            propSet.addAll(extractPropositions(operator.getOperand2()));
        }
        return propSet;
    }

    private static LevelParseException getWrongNumberOfArgumentsLevelParseException(String token, int lineNumb) {
        return new LevelParseException("Wrong number of arguments for token " + token + " on line: " + lineNumb);
    }
    private static LevelParseException getTooManyGoalsLevelParseException(int lineNumb) {
        return new LevelParseException("Level file contains more than one goal on line: " + lineNumb);
    }
    private static LevelParseException getTooManyTitlesLevelParseException(int lineNumb) {
        return new LevelParseException("Level file contains more than one title on line: " + lineNumb);
    }

    public static Level parseLevel(String levelString) throws LevelParseException{

        int lineNumb;
        Map<String,String> symbolMap = new HashMap<>();
        String[] lines = levelString.split("\n");

        // Parse SYMBOL
        lineNumb = 0;
        for (String line : lines) {
            String[] tokens = line.split("\\s+"); // regex: One or more whitespaces
            if (tokens[0].equals(SYMBOL)) {
                if (tokens.length != 3) {
                    throw getWrongNumberOfArgumentsLevelParseException(tokens[0], lineNumb);
                }
                else {
                    symbolMap.put(tokens[1], tokens[2]);
                }
            }
            lineNumb += 1;
        }

        // Initialize members for level
        ExpressionFactory expressionFactory = new ExpressionFactory(symbolMap);
        ExpressionParser expressionParser = new ExpressionParser(expressionFactory);
        List<Expression> hypothesis= new ArrayList<>();
        Expression goal = null;
        String title = null;

        lineNumb = 0;
        for (String line : lines) {
            String[] tokens = line.split("\\s+"); // regex: One or more whitespaces
            if (tokens.length>0) {
                String argument = Util.join(Util.tail(tokens));

                switch (tokens[0]) {
                    case HYPOTHESIS:
                        hypothesis.add(expressionParser.parseString(argument));
                        break;
                    case GOAL:
                        if (goal != null) {
                            throw getTooManyGoalsLevelParseException(lineNumb);
                        }
                        goal = expressionParser.parseString(argument);
                        break;
                    case TITLE :
                        if (title != null) {
                            throw getTooManyTitlesLevelParseException(lineNumb);
                        }
                        title = argument;
                        break;
                }
            }
            lineNumb += 1;
        }
        return new Level(title,hypothesis,goal,expressionFactory);
    }

    public static final List<String> exampleLevels;
    static {
        exampleLevels = new ArrayList<>();
        final String levelStr =
                "TITLE Example Level\n" +
                "\n" +
                "SYMBOL P BlueBall\n" +
                "SYMBOL Q RedBall\n" +
                "\n" +
                "HYPOTHESIS (P)\n" +
                "HYPOTHESIS (Q)\n" +
                "\n" +
                "GOAL ((P)&(Q))\n";
        exampleLevels.add(levelStr);
    }


    public static final Level exampleLevel;
    static {
        Map<String,String> dummyMap = new HashMap<>();
        ExpressionFactory expressionFactory = new ExpressionFactory(dummyMap);
        List<Expression> hypothesis = new ArrayList<>();
        Expression p = expressionFactory.createProposition("P");
        Expression q = expressionFactory.createProposition("Q");
        hypothesis.add(p);
        hypothesis.add(q);
        Expression goal = expressionFactory.createOperator(OperatorType.IMPLICATION,p,q);
        exampleLevel = new Level("dummy",hypothesis,goal,expressionFactory);
    }
}
