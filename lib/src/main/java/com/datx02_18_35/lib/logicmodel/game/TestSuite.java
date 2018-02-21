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
    private Scanner inputReader = new Scanner(System.in);
    private ExpressionFactory exprFactory;
    private String input;
    private Session session;

    private List<Expression> selectedCards;
    public TestSuite(ExpressionFactory exprFactory, Session session) {
        this.exprFactory = exprFactory;
        this.session = session;
        selectedCards = new ArrayList<Expression>();
    }


    public void makeMove(){
        System.out.println("Make a move from the following set of moves: MAKE_ASSUMPTION, APPLY_RULE, SHOW_GAMEBOARD, SHOW_INVENTORY," +
                "SHOW_RUlES, SELECT_CARD, CLEAR_SELECTION");
        input = inputReader.nextLine();
        switch (input){
            case "MAKE_ASSUMPTION":
                this.createExpression();
                break;
            case "APPLY_RULE":
                if(selectedCards.size() == 0){
                    System.out.println("Invalid argument");
                    break;
                }else {
                    applyRule();
                    break;
                }
            case "SHOW_GAMEBOARD":
                showGameboard();
                break;
            case "SHOW_INVENTORY":
                showInventory();
            case "SHOW_RUlES":
                showLegalRules();
                break;
            case "SELECT_CARD":
                selectCard();
                break;
            case "CLEAR_SELECTION":
                clearSelection();
                break;
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
                break;
            case "DISJUNCTION":
                expression.add(exprFactory.createOperator(OperatorType.DISJUNCTION, createLeftOperand(), createRightOperand()));
                break;
            case "IMPLICATION":
                expression.add(exprFactory.createOperator(OperatorType.IMPLICATION, createLeftOperand(), createRightOperand()));
                break;
            case "PROPOSITION":
                expression.add(createProposition());
                break;
            case "ABSURDITY":
                expression.add(exprFactory.createAbsurdity());
                break;
            default:
                System.out.println("Invalid argument");
                this.createExpression();

        }
        //session.addExpressionToGameBoard(expression);
        // showCards();

    }

    private Expression createLeftOperand() {
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

    private Expression createRightOperand() {
        System.out.println("Decide right operator, choose between: CONJUNCTION, DISJUNCTION, IMPLICATION, PROPOSITION OR ABSURDITY");
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

    private Expression createProposition() {
        System.out.println("Please choose proposition between: P,Q,R,S,T");
        input = inputReader.nextLine();
        if (input.equals("P") || input.equals("Q") || input.equals("R") || input.equals("S") || input.equals("T")) {
            return exprFactory.createProposition(input);
        } else {
            System.out.println("Invalid argument");
            return this.createProposition();
        }

    }

    private void showGameboard(){
        List<Expression> expressions = session.getExpressionsOnGameBoard();
        for (int i = 0; i<expressions.size(); i++){
            System.out.println("["+i+"]"+expressions.get(i).toString());
        }

    }

    public void showInventory(){
        List<Expression> expressions = session.getExpressionsInInventory();
        for (int i = 0; i<expressions.size();i++){
            System.out.println("["+i+"]"+expressions.get(i).toString());
        }
    }

    private void selectCard(){
        showGameboard();

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

    private void moveCardFromInventory(){
        showInventory();
        List<Expression> expressions = session.getExpressionsInInventory();
        System.out.println("Select a card to move by the index");
        int input = inputReader.nextInt();
        if(input<expressions.size() && input>=0) {
            List<Expression> expression = new ArrayList<>();
            expression.add(expressions.get(input));
            session.addExpressionToGameBoard(expression);
        }else {
            System.out.println("Invalid argument");
            this.moveCardFromInventory();
        }
    }

    private List<Rule> showLegalRules(){
        Collection<Rule> rules =  Rule.getLegalRules(session.getCurrentAssumption(),selectedCards);
        List<Rule> listOfRules = new ArrayList<>(rules);
        for (int i = 0; i<listOfRules.size(); i++){
            System.out.println("["+i+"]" + listOfRules.get(i).type.toString()); //Skriv ut expressions också
        }
        return listOfRules;
    }

    private void applyRule(){
        List<Rule> rules = showLegalRules();
        Collection<String> strings = new ArrayList<>();
        for (Rule r : rules){
            strings.add(r.type.toString());
        }
        int input = inputReader.nextInt();
        if(input > 0 && input< rules.size()) {
            session.addExpressionToGameBoard(exprFactory.applyRule(rules.get(input)));
            showGameboard();
            clearSelection();
        }


    }

    private void clearSelection(){
        selectedCards.clear();
    }
}


