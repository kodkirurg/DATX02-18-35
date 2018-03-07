package com.datx02_18_35.model.game;

import com.datx02_18_35.model.ExpressionParser;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.ExpressionFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class Level {
    private final Map<String,String> idToSymbol;
    private final ExpressionParser expressionParser;

    public final List<Expression> hypothesis;
    public final String levelName;
    public final Expression goal;
    public final ExpressionFactory expressionFactory;


    public static Level createLevel(String filepath) throws NullPointerException, FileNotFoundException, IOException{
        Scanner input = new Scanner(new File(filepath));
        while(input.hasNextLine()){

        }
        return null;
    }

    private Level(String levelName,List<Expression> hypothesis, Expression goal, Map<String,String> idToSymbol){
        this.hypothesis=hypothesis;
        this.goal=goal;
        this.idToSymbol=idToSymbol;
        this.levelName=levelName;
        this.expressionFactory = new ExpressionFactory(idToSymbol);
        this.expressionParser = new ExpressionParser(expressionFactory);

    }
}
