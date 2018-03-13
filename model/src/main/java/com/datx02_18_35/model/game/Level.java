package com.datx02_18_35.model.game;

import com.datx02_18_35.model.ExpressionParser;
import com.datx02_18_35.model.expression.Absurdity;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.expression.Operator;
import com.datx02_18_35.model.expression.OperatorType;
import com.datx02_18_35.model.expression.Proposition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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


    public static Level createLevel(String filepath) throws NullPointerException, IOException, LevelParseException{

        Map<String,String> map = new HashMap<>();
        Scanner input = new Scanner(new File(filepath));
        List<String> lineList = new ArrayList<>();
        while(input.hasNextLine()){
            String line = input.nextLine();
            String[] strings =line.split(" ");
            if(strings[0].equals(SYMBOL)){
                if(strings.length!=3){
                    throw new LevelParseException("Text in file not formatted correctly");
                }else{
                    map.put(strings[1],strings[2]);
                }

            }else{
                lineList.add(line);
            }

        }


        ExpressionFactory expressionFactory = new ExpressionFactory(map);
        ExpressionParser expressionParser = new ExpressionParser(expressionFactory);
        List<Expression> hypothesis= new ArrayList<>();
        Expression goal = null;
        String Title = "";
        for(String string:lineList){
            String[] strings=string.split(" ");
            if(strings.length>0){
                switch (strings[0]){
                    case HYPOTHESIS:
                        for(int i=1; i<strings.length;i++){
                            hypothesis.add(expressionParser.parseString(strings[i]));
                        }
                        break;
                    case GOAL:
                        if(goal==null && strings.length == 2) {
                            goal = expressionParser.parseString(strings[1]);
                        }else{
                            throw new LevelParseException("Two goals in level file");
                        }
                        break;

                    case TITLE :
                        if(strings.length>0){
                            for (int i=1; i<strings.length;i++){
                                Title+=strings[i]+" ";
                            }
                        }else {
                            throw new LevelParseException("No title in level file");
                        }
                        break;

                }
            }
        }

        return new Level(Title,hypothesis,goal,expressionFactory);
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



    static class LevelParseException extends Exception {
        LevelParseException(String string){
            super(string);
        }
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
