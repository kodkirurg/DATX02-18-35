package com.datx02_18_35.model.level;

import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.expression.Operator;
import com.datx02_18_35.model.expression.OperatorType;
import com.datx02_18_35.model.expression.Proposition;
import com.datx02_18_35.model.expression.ExpressionParseException;
import com.datx02_18_35.model.expression.ExpressionParser;
import com.datx02_18_35.model.rules.Rule;
import com.datx02_18_35.model.rules.RuleType;
;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class Level implements Serializable {
    private static final int    HASH_MAGIC_NUMBER = 1_528_680_899;

    public final List<Expression> hypothesis;
    public final String title;
    public final Expression goal;
    public final ExpressionFactory expressionFactory;
    public final List<Expression> usedSymbols;
    public final String description;
    public final Set<RuleType> ruleSet;

    private final int hashCode;

    private Level(
            String title,
            List<Expression> hypothesis,
            Expression goal,
            ExpressionFactory expressionFactory,
            String description,
            Set<RuleType> ruleSet) {

        this.hypothesis=hypothesis;
        this.goal=goal;
        this.title=title;
        this.expressionFactory=expressionFactory;
        this.usedSymbols = new ArrayList<>();
        this.usedSymbols.addAll(extractPropositions(goal, hypothesis));
        this.usedSymbols.add(expressionFactory.createAbsurdity());
        this.description = description;
        this.ruleSet = ruleSet;

        long magic = HASH_MAGIC_NUMBER;
        long longHash = magic;
        long hypothesisHashProduct = 1;
        for (Expression expr : hypothesis) {
            hypothesisHashProduct *= expr.hashCode();
        }
        magic *= HASH_MAGIC_NUMBER;
        longHash += title.hashCode()*magic;
        magic *= HASH_MAGIC_NUMBER;
        longHash += goal.hashCode()*magic;
        magic *= HASH_MAGIC_NUMBER;
        longHash += hypothesisHashProduct*magic;
        hashCode = (int)longHash;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Level)) return false;
        final Level otherLevel = (Level)other;
        if (!title.equals(otherLevel.title)) return false;
        if (!goal.equals(otherLevel.goal)) return false;
        for (Expression thisExpr : hypothesis) {
            boolean inOtherHypothesis = false;
            for (Expression otherExpr : otherLevel.hypothesis) {
                if (thisExpr.equals(otherExpr)) {
                    inOtherHypothesis = true;
                    break;
                }
            }
            if (!inOtherHypothesis) {
                return false;
            }
        }

        for (Expression otherExpr : otherLevel.hypothesis) {
            boolean inThisHypothesis = false;
            for (Expression thisExpr : hypothesis) {
                if (otherExpr.equals(thisExpr)) {
                    inThisHypothesis = true;
                    break;
                }
            }
            if (!inThisHypothesis) {
                return false;
            }
        }
        return true;
    }

    public ExpressionFactory getExpressionFactory() {
        return this.expressionFactory;
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

    private static LevelParseException getWrongNumberOfArgumentsLevelParseException(int lineNumb, String token) {
        return new LevelParseException("Wrong number of arguments for token " + token + " on line: " + lineNumb);
    }
    private static LevelParseException getTooManyGoalsLevelParseException(int lineNumb) {
        return new LevelParseException("Level file contains more than one goal on line: " + lineNumb);
    }
    private static LevelParseException getTooManyTitlesLevelParseException(int lineNumb) {
        return new LevelParseException("Level file contains more than one title on line: " + lineNumb);
    }
    private static LevelParseException getTooManyDescriptionsParseException(int lineNumb) {
        return new LevelParseException("Level file contains more than one description on line: " + lineNumb);
    }
    private static LevelParseException getUnexpectedDescriptionArgumentException(int lineNumb, String found, String expected) {
        return new LevelParseException(
                "Level file contains an unexpected argument to DESCRIPTION on line: " + lineNumb
                + ". Found: " + found + ", expected: " + expected);
    }
    private static LevelParseException getDescriptionEndOfFileException() {
        return new LevelParseException("Reached end of file while parsing level description");
    }
    private static LevelParseException getInvalidRuleSetException(int lineNumb, String token) {
        return new LevelParseException("Level file contains an invalid ruleset: " + token + " on line: " + lineNumb);
    }

    public static Level parseLevel(String levelString) throws LevelParseException, ExpressionParseException {

        int lineNumb;
        Map<String,String> symbolMap = new HashMap<>();
        //symbolMap.put("(#)","Absurdity");
        List<String> lines = Arrays.asList(levelString.split("\n"));

        // Parse SYMBOL
        lineNumb = 0;
        for (String line : lines) {
            String[] tokens = line.split("\\s+"); // regex: One or more whitespaces
            if (tokens[0].equals("SYMBOL")) {
                if (tokens.length != 3) {
                    throw getWrongNumberOfArgumentsLevelParseException(lineNumb, tokens[0]);
                }
                else {
                    symbolMap.put("(" +tokens[1] + ")", tokens[2]);
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
        String description = null;
        Set<RuleType> ruleSet = new HashSet<>();

        // Parse HYPOTHESIS, GOAL, TITLE, DESCRIPTION
        lineNumb = 0;
        Iterator<String> lineIterator = lines.iterator();
        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            String[] tokens = line.split("\\s+"); // regex: One or more whitespaces
            if (tokens.length>0) {
                switch (tokens[0]) {
                    case "HYPOTHESIS": {
                        hypothesis.add(expressionParser.parseString(Util.join(Util.tail(tokens))));
                    }
                    break;
                    case "GOAL": {
                        if (goal != null) {
                            throw getTooManyGoalsLevelParseException(lineNumb);
                        }
                        goal = expressionParser.parseString(Util.join(Util.tail(tokens)));
                    }
                    break;
                    case "TITLE": {
                        if (title != null) {
                            throw getTooManyTitlesLevelParseException(lineNumb);
                        }
                        title = Util.join(" ", Util.tail(tokens));
                    }
                    break;
                    case "RULESET": {
                        if (tokens.length <= 1) {
                            throw getWrongNumberOfArgumentsLevelParseException(lineNumb, tokens[0]);
                        }
                        for (int i = 1; i < tokens.length; ++i) {
                            switch (tokens[i]) {
                                case "CONSTRUCTIVE":
                                    ruleSet.addAll(RuleType.Sets.CONSTRUCTIVE);
                                    break;
                                case "IMPLICATION_ONLY":
                                    ruleSet.addAll(RuleType.Sets.IMPLICATION_ONLY);
                                    break;
                                case "CONJUNCTION_ONLY":
                                    ruleSet.addAll(RuleType.Sets.CONJUNCTON_ONLY);
                                    break;
                                case "DISJUNCTION_ONLY":
                                    ruleSet.addAll(RuleType.Sets.DISJUNCTION_ONLY);
                                    break;
                                default:
                                    throw getInvalidRuleSetException(lineNumb, tokens[i]);
                            }
                        }
                    }
                    case "DESCRIPTION": {
                        if (description != null) {
                            throw getTooManyDescriptionsParseException(lineNumb);
                        }
                        if (Util.join(" ", Util.tail(tokens)).equals("START")) {
                            StringBuilder descriptionBuilder = new StringBuilder();
                            boolean firstLine = true;
                            boolean descriptionFinished = false;
                            while (lineIterator.hasNext()) {
                                String descriptionLine = lineIterator.next();
                                String[] descriptionTokens = descriptionLine.split("\\s+");
                                if (descriptionTokens.length > 0
                                    && descriptionTokens[0].equals("DESCRIPTION")) {

                                    String descriptionArgument = Util.join(" ", Util.tail(descriptionTokens));
                                    if (descriptionArgument.equals("END")) {
                                        description = descriptionBuilder.toString();
                                        descriptionFinished = true;
                                        break;
                                    }
                                    else {
                                        throw getUnexpectedDescriptionArgumentException(
                                                lineNumb,
                                                descriptionArgument,
                                                "END");
                                    }

                                } else {
                                    if (!firstLine) {
                                        descriptionBuilder.append("\n");
                                    }
                                    descriptionBuilder.append(descriptionLine);
                                    firstLine = false;
                                }
                                lineNumb += 1;
                            }
                            if (!descriptionFinished) {
                                throw getDescriptionEndOfFileException();
                            }
                        }
                        else {
                            throw getUnexpectedDescriptionArgumentException(
                                    lineNumb, Util.join(" ", Util.tail(tokens)), "START");
                        }
                    }
                }
            }
            lineNumb += 1;
        }
        if (description == null) {
            description = "";
        }
        if (ruleSet.isEmpty()) {
            ruleSet.addAll(RuleType.Sets.DEFAULT);
        }
        return new Level(title,hypothesis,goal,expressionFactory, description, ruleSet);
    }
    

}
