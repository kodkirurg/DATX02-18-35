package com.datx02_18_35.lib.logicmodel.game;

import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.ExpressionFactory;
import com.datx02_18_35.lib.logicmodel.expression.OperatorType;
import java.util.Scanner;

/**
 * Created by Jonatan on 2018-02-21.
 */

public class TestSuite {
    Scanner inputReader = new Scanner(System.in);
    ExpressionFactory exprFactory;
    String input;


    public TestSuite(ExpressionFactory exprFactory) {
        this.exprFactory = exprFactory;
    }


    public void makeMove(){
        System.out.println("Make a move from the following set of moves: MAKE_ASSUMPTION");
        this.createExpression();
    }

    public Expression createExpression() {
        System.out.println("Decide root between: CONJUNCTION, DISJUNCTION, IMPLICATION, PROPOSITION OR ABSURDITY");
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
                return this.createExpression();

        }
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
}


