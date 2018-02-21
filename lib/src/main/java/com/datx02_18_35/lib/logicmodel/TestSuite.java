package com.datx02_18_35.lib.logicmodel;

import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.ExpressionFactory;
import com.datx02_18_35.lib.logicmodel.expression.OperatorType;
import com.datx02_18_35.lib.logicmodel.expression.Rule;
import com.datx02_18_35.lib.logicmodel.game.Session;

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
                }else {
                    applyRule();
                }
                break;
            case "SHOW_GAMEBOARD":
                showGameboard();
                break;
            case "SHOW_INVENTORY":
                showInventory();
                break;
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
        int i = 0;
        for (Expression expr : session.getGameBoard()) {
            System.out.println("["+(i++)+"]"+expr);
        }
    }

    public void showInventory(){
        int i = 0;
        for (Expression expr : session.getInventory()) {
            System.out.println("["+(i++)+"]"+expr);
        }
    }

    private void selectCard(){
        showGameboard();
        System.out.println("Select a card by the index");
        while (true) {
            int input = inputReader.nextInt();
            if (input < 0) {
                System.out.println("Invalid argument");
                continue;
            }
            for (Expression expr : session.getGameBoard()) {
                if (input == 0) {
                    selectedCards.add(expr);
                    showLegalRules();
                    return;
                } else {
                    input--;
                }
            }
            System.out.println("Invalid argument");
        }
    }

    private void addCardFromInventory(){
        showInventory();
        System.out.println("Select a card to move by the index");
        while (true) {
            int input = inputReader.nextInt();
            if (input < 0) {
                System.out.println("Invalid argument");
                continue;
            }
            for (Expression expr : session.getInventory()) {
                if (input == 0) {
                    session.addExpressionToGameBoard(expr);
                    return;
                } else {
                    input--;
                }
            }
            System.out.println("Invalid argument");
        }
    }

    private List<Rule> showLegalRules(){
        List<Rule> rules =  new ArrayList<>(Rule.getLegalRules(session.getAssumption(),selectedCards));
        int i = 0;
        for (Rule rule : rules) {
            System.out.println("["+(i++)+"]"+rule.type);
        }
        return rules;
    }

    private void applyRule(){
        List<Rule> rules = showLegalRules();
        int input = inputReader.nextInt();
        while (input < 0 || input >= rules.size()) {
            System.out.println("Invalid input!");
            input = inputReader.nextInt();
        }
        session.addExpressionToInventory(exprFactory.applyRule(rules.get(input)));
        clearSelection();
        showGameboard();
    }

    private void clearSelection() {
        selectedCards.clear();
    }

}


