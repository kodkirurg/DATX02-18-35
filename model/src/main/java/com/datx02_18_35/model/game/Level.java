package com.datx02_18_35.model.game;

import com.datx02_18_35.model.ExpressionParser;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.expression.OperatorType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class Level {
    public final List<Expression> hypothesis;
    public final String levelName;
    public final Expression goal;
    public final ExpressionFactory expressionFactory;




    private Level(String levelName,List<Expression> hypothesis, Expression goal,ExpressionFactory expressionFactory){
        this.hypothesis=hypothesis;
        this.goal=goal;
        this.levelName=levelName;
        this.expressionFactory=expressionFactory;
    }


    public static Level createLevel(String filepath) throws NullPointerException, FileNotFoundException, IOException{
        Map<String,String> map = new HashMap<>();
        Scanner input = new Scanner(new File(filepath));
        List<String> lineList = new ArrayList<>();
        while(input.hasNextLine()){
            String line = input.nextLine();
            String[] strings =line.split(" ");
            if(strings[0].equals("Symbol")){
                if(strings.length!=3){
                    throw new IllegalArgumentException("Text in file not formatted correctly");
                }else{
                    map.put(strings[1],strings[2]);
                }

            }else{
                lineList.add(line);
            }

        }
        ExpressionFactory exprFactory = new ExpressionFactory(map);
        ExpressionParser exprParser = new ExpressionParser(exprFactory);
        Collection<Expression> hypo= new ArrayList<>();
        Expression goal = null;
        for(String string:lineList){
            String[] strings=string.split(" ");
            if(strings.length>0){
                switch (strings[0]){
                    case "Hypothesis":
                        for(int i=1; i<strings.length;i++){
                            hypo.add(exprParser.parseString(strings[i]));
                        }
                    case "goal":
                        if(goal==null){
                            if(strings.length==1) {
                                goal = exprParser.parseString(strings[1]);
                            }
                        }

                }
            }
        }

        return null;
    }


    public ExpressionFactory getExpressionFactory(){
        return expressionFactory;
    }

    class InvalidParseFormatException extends Exception {
        public InvalidParseFormatException(String string){
            super();
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
        Expression goal = expressionFactory.createOperator(OperatorType.CONJUNCTION,p,q);
        exampleLevel = new Level("dummy",hypothesis,goal,expressionFactory);
    }
}
