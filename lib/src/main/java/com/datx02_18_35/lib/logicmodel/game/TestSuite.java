package com.datx02_18_35.lib.logicmodel.game;

import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.ExpressionFactory;
import com.datx02_18_35.lib.logicmodel.expression.OperatorType;
import com.datx02_18_35.lib.logicmodel.expression.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jonatan on 2018-02-21.
 */

public class TestSuite {
    Scanner inputReader = new Scanner(System.in);
    ExpressionFactory exprFactory;
    String input;
    Session session;

    List<Expression> selectedCards;
    public TestSuite(ExpressionFactory exprFactory, Session session) {
        this.exprFactory = exprFactory;
        this.session = session;
        selectedCards = new ArrayList<Expression>();
    }


    public void makeMove(){
        System.out.println("Make a move from the following set of moves: MAKE_ASSUMPTION, APPLY_RULE, SHOW_GAMEBOARD, SHOW_RUlES, SELECT_CARD, CLEAR_SELECTION");
        input = inputReader.nextLine();
        switch (input){
            case "MAKE_ASSUMPTION":
                this.createExpression();
            case "APPLY_RULE":
                if(selectedCards.size() == 0){
                    System.out.println("Invalid argument");
                }else {
                    applyRule();
                }
            case "SHOW_GAMEBOARD":
                showCards();
            case "SHOW_RUlES":
                showLegalRules();
            case "SELECT_CARD":
                selectCard();
            case "CLEAR_SELECTION":
                clearSelection();
            default:
                System.out.println("Invalid argument");
        }

        makeMove();

    }

    public void createExpression() {
        System.out.println("Decide root between: CONJUNCTION, DISJUNCTION, IMPLICATION, PROPOSITION OR ABSURDITY");
        Collection<Expression> expression = new ArrayList<>();
        input = inputReader.nextLine();
        switch (input) {
            case "CONJUNCTION":
                expression.add(exprFactory.createOperator(OperatorType.CONJUNCTION, createLeftOperand(), createRightOperand()));
            case "DISJUNCTION":
                expression.add(exprFactory.createOperator(OperatorType.DISJUNCTION, createLeftOperand(), createRightOperand()));
            case "IMPLICATION":
                expression.add(exprFactory.createOperator(OperatorType.IMPLICATION, createLeftOperand(), createRightOperand()));
            case "PROPOSITION":
                expression.add(createProposition());
            case "ABSURDITY":
                expression.add(exprFactory.createAbsurdity());
            default:
                System.out.println("Invalid argument");
                this.createExpression();

        }
        session.addExpressionToGameBoard(expression);
        showCards();
    }

    public Expression createLeftOperand() {
        System.out.println("Decide left operator, chose between: CONJUNCTION, DISJUNCTION, IMPLICATION, PROPOSITION OR ABSURDITY");
        input = inputReader.nextLine();
        switch (input) {
            case "CONJUNCTION":
                return exprFactory.createOperator(OperatorType.CONJUNCTION, createLeftOperand(), createRightOperand());
            case "DISJUNCTION":
                return exprFactory.createOperator(OperatorType.DISJUNCTION, createLeftOperand(), createRightOperand());
            case "IMPLICATION":
                return exprFactory.createOperator(OperatorType.IMPLICATION, createLeftOperand(), createRightOperand());
            case "PROPOSITION":
                return createProposition();
            case "ABSURDITY":
                return exprFactory.createAbsurdity();
            default:
                System.out.println("Invalid argument");
                return this.createLeftOperand();

        }
    }

    public Expression createRightOperand() {
        System.out.println("Decide right operator, chose between: CONJUNCTION, DISJUNCTION, IMPLICATION, PROPOSITION OR ABSURDITY");
        input = inputReader.nextLine();
        switch (input) {
            case "CONJUNCTION":
                return exprFactory.createOperator(OperatorType.CONJUNCTION, createLeftOperand(), createRightOperand());
            case "DISJUNCTION":
                return exprFactory.createOperator(OperatorType.DISJUNCTION, createLeftOperand(), createRightOperand());
            case "IMPLICATION":
                return exprFactory.createOperator(OperatorType.IMPLICATION, createLeftOperand(), createRightOperand());
            case "PROPOSITION":
                return createProposition();
            case "ABSURDITY":
                return exprFactory.createAbsurdity();
            default:
                System.out.println("Invalid argument");
                return this.createRightOperand();

        }
    }

    public Expression createProposition() {
        System.out.println("Please choose proposition between: P,Q,R,S,T");
        input = inputReader.nextLine();
        if (input.equals("P") || input.equals("Q") || input.equals("R") || input.equals("S") || input.equals("T")) {
            return exprFactory.createProposition(input);
        } else {
            System.out.println("Invalid argument");
            return this.createProposition();
        }

    }

    public void showCards(){
        List<Expression> expressions = session.getExpressionsOnGameBoard();
        for (int i = 0; i<expressions.size(); i++){
            System.out.println("["+i+"]"+expressions.get(i).toString());
        }

    }

    public void selectCard(){
        showCards();

        List<Expression> expressions = session.getExpressionsOnGameBoard();
        System.out.println("Select a card by the index");
        int input = inputReader.nextInt();
        if(input<expressions.size() && input>=0) {
            selectedCards.add(expressions.get(input));
            showLegalRules();
        }else {
            System.out.println("Invalid argument");
            this.selectCard();
        }
    }

    public List<Rule> showLegalRules(){
        Collection<Rule> rules =  Rule.getLegalRules(session.getCurrentAssumption(),selectedCards);
        List<Rule> listOfRules = new ArrayList<>(rules);
        for (int i = 0; i<listOfRules.size(); i++){
            System.out.println("["+i+"]" + listOfRules.get(i).type.toString()); //Skriv ut expressions också
        }
        return listOfRules;
    }

    public void applyRule(){
        List<Rule> rules = showLegalRules();
        Collection<String> strings = new ArrayList<>();
        for (Rule r : rules){
            strings.add(r.type.toString());
        }
        int input = inputReader.nextInt();
        if(input > 0 && input< rules.size()) {
            session.addExpressionToGameBoard(exprFactory.applyRule(rules.get(input)));
            showCards();
            clearSelection();
        }


    }

    public void clearSelection(){
        selectedCards.clear();
    }
}


