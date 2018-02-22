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
        selectedCards = new ArrayList<>();
    }


    public void makeMove(){
        System.out.println("Make a move from the following set of moves: 1. MAKE_ASSUMPTION, 2. APPLY_RULE, 3. SHOW_GAMEBOARD, 4. SHOW_INVENTORY," +
                " 5. ADD_CARD_FROM_INVENTORY, 6. SHOW_RUlES, 7. SELECT_CARD, 8. CLEAR_SELECTION");
        int input = inputReader.nextInt();
        switch (input){
            case 1:
                Expression expression = this.createExpression();
                Collection<Expression> expressions = new ArrayList<>();
                expressions.add(expression);
                session.pushScope(expression);
                this.showGameboard();
                break;
            case 2:
                if(selectedCards.size() == 0){
                    System.out.println("Can't apply rule since no cards are selected");
                }else {
                    applyRule();
                }
                break;
            case 3:
                showGameboard();
                break;
            case 4:
                showInventory();
                break;
            case 5:
                this.addCardFromInventory();
                break;
            case 6:
                showLegalRules();
                break;
            case 7:
                selectCard();
                break;
            case 8:
                clearSelection();
                break;
            default:
                System.out.println("Invalid argument in makeMove");
        }

        makeMove();

    }

    public Expression createExpression() {
        System.out.println("Decide root between: 1. CONJUNCTION, 2. DISJUNCTION, 3. IMPLICATION, 4. PROPOSITION OR 5. ABSURDITY");
        Expression expression;
        int input = inputReader.nextInt();
        switch (input) {
            case 1:
                expression=(exprFactory.createOperator(OperatorType.CONJUNCTION, createLeftOperand(), createRightOperand()));
                break;
            case 2:
                expression=(exprFactory.createOperator(OperatorType.DISJUNCTION, createLeftOperand(), createRightOperand()));
                break;
            case 3:
                expression=(exprFactory.createOperator(OperatorType.IMPLICATION, createLeftOperand(), createRightOperand()));
                break;
            case 4:
                expression=(createProposition());
                break;
            case 5:
                expression=(exprFactory.createAbsurdity());
                break;
            default:
                System.out.println("Invalid argument");
                expression=this.createExpression();

        }
        return expression;

    }

    private Expression createLeftOperand() {
        System.out.println("Decide left operator, chose between: 1. CONJUNCTION, 2. DISJUNCTION, 3. IMPLICATION, 4. PROPOSITION OR 5. ABSURDITY");
        int input = inputReader.nextInt();
        switch (input) {
            case 1:
                return exprFactory.createOperator(OperatorType.CONJUNCTION, createLeftOperand(), createRightOperand());
            case 2:
                return exprFactory.createOperator(OperatorType.DISJUNCTION, createLeftOperand(), createRightOperand());
            case 3:
                return exprFactory.createOperator(OperatorType.IMPLICATION, createLeftOperand(), createRightOperand());
            case 4:
                return createProposition();
            case 5:
                return exprFactory.createAbsurdity();
            default:
                System.out.println("Invalid argument");
                return this.createLeftOperand();

        }
    }

    private Expression createRightOperand() {
        System.out.println("Decide right operator, choose between: 1. CONJUNCTION, 2. DISJUNCTION, 3. IMPLICATION, 4. PROPOSITION OR 5. ABSURDITY");
        int input = inputReader.nextInt();
        switch (input) {
            case 1:
                return exprFactory.createOperator(OperatorType.CONJUNCTION, createLeftOperand(), createRightOperand());
            case 2:
                return exprFactory.createOperator(OperatorType.DISJUNCTION, createLeftOperand(), createRightOperand());
            case 3:
                return exprFactory.createOperator(OperatorType.IMPLICATION, createLeftOperand(), createRightOperand());
            case 4:
                return createProposition();
            case 5:
                return exprFactory.createAbsurdity();
            default:
                System.out.println("Invalid argument");
                return this.createRightOperand();

        }
    }

    private Expression createProposition() {
        System.out.println("Please choose proposition between: 1. P, 2. Q, 3. R, 4. S, 5. T");
        int input = inputReader.nextInt();
        switch (input){
            case 1:
                return exprFactory.createProposition("P");
            case 2:
                return exprFactory.createProposition("Q");
            case 3:
                return exprFactory.createProposition("R");
            case 4:
                return exprFactory.createProposition("S");
            case 5:
                return exprFactory.createProposition("T");
            default:
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
            System.out.println("Invalid arguement");
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
        if(rules.get(input).type.name().equals("ABSURDITY_ELIMINATION")||rules.get(input).type.name().equals("DISJUNCTION_INTRODUCTION")){
            System.out.println("Please create card for absurdity elimination/disjunction introduction");
            Expression expression = this.createExpression();
            List<Expression> expressions = new ArrayList<>();
            expressions.addAll(rules.get(input).expressions);
            expressions.add(expression);
            Rule rule = new Rule(rules.get(input).type,expressions);
            session.addExpressionToInventory(exprFactory.applyRule(rule));
            clearSelection();
            showGameboard();
        }
        else if(rules.get(input).type.name().equals("IMPLICATION_INTRODUCTION")){
            session.closeScope();
            session.addExpressionToInventory(exprFactory.applyRule(rules.get(input)));
            clearSelection();
            showGameboard();
        }
        else{
            session.addExpressionToInventory(exprFactory.applyRule(rules.get(input)));
            clearSelection();
            showGameboard();
        }
    }

    private void clearSelection() {
        selectedCards.clear();
    }

}


