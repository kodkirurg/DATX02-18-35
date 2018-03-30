package com.datx02_18_35.model.expression;

/**
 * Created by Jonatan on 2018-02-28.
 */

public class ExpressionParser {
    private ExpressionFactory exprFactory;

    public ExpressionParser(ExpressionFactory expressionFactory){
        exprFactory=expressionFactory;
    }
    public Expression parseString(String expression) throws ExpressionParseException {
        final String trimExpr = expression.trim();
        final int length = trimExpr.length();
        if (length == 0) {
            throw new ExpressionParseException("Expression can't be empty");
        }
        if (length == 1) {
            if (trimExpr.equals("#")) {
                return exprFactory.createAbsurdity();
            } else {
                return exprFactory.createProposition(trimExpr);
            }
        }
        if (length == 2) {
            throw new ExpressionParseException("Expression must either be a single symbol or an operator with two operands");
        }

        int paraCount = 0;
        char[] exprChars = trimExpr.toCharArray();
        for (int i = 0; i < exprChars.length; i++) {
            if (exprChars[i] == '(') {
                paraCount += 1;
            } else if (exprChars[i] == ')') {
                paraCount -= 1;
            }

            if (paraCount < 0) {
                throw new ExpressionParseException("Unexpected right parenthesis in expression " + trimExpr + " at: " + i);
            }

            if (paraCount == 0) {
                if (i == exprChars.length-1) {
                    //Parentheses eclipses entire string
                    return parseString(trimExpr.substring(1,length-1));
                }
                String leftStr = trimExpr.substring(0, i + 1);
                Expression left = parseString(leftStr);
                String rightStr = trimExpr.substring(i + 2, length);
                Expression right = parseString(rightStr);
                OperatorType type;
                switch (trimExpr.charAt(i + 1)) {
                    case '&':
                        type = OperatorType.CONJUNCTION;
                        break;
                    case '|':
                        type = OperatorType.DISJUNCTION;
                        break;
                    case '>':
                        type = OperatorType.IMPLICATION;
                        break;
                    default:
                        throw new ExpressionParseException("In expression " + trimExpr + ", unknown operator: " + trimExpr.charAt(i + 1));
                }
                return exprFactory.createOperator(type, left, right);
            }
        }

        // Unbalanced expression
        throw new ExpressionParseException("In expression " + trimExpr + ", reached end if line while expecting right parenthesis");
    }
}
