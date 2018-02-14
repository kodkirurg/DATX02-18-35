package com.datx02_18_35.lib.logicmodel.expression;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import java.util.List;

/**
 * Created by robin on 2018-02-14.
 */

public class Rule {
    public final RuleType type;
    public final List<Expression> expressions;


    public Rule(RuleType type, List<Expression> expressions) {
        assert expressions != null;
        this.type = type;
        this.expressions = expressions;
    }

    public static Collection<Rule> getLegalRules(Collection<Expression> expressions){
        ArrayList<Expression> exprs = (ArrayList<Expression>) expressions;
        ArrayList<Rule> legalRules = new ArrayList<Rule>();
        switch(exprs.size()){
            case 1:

                legalRules.add(new Rule(RuleType.DISJUNCTION_INTRODUCTION, exprs));
                if(exprs.get(0) instanceof Conjunction){
                    legalRules.add(new Rule(RuleType.CONJUNCTION_ELIMINATION, exprs));
                }
                return legalRules;
            case 2:
                ArrayList<Expression> reverseExprs = exprs;
                Collections.reverse(reverseExprs);

                legalRules.add(new Rule(RuleType.CONJUNCTION_INTRODUCTION, exprs));
                legalRules.add(new Rule(RuleType.CONJUNCTION_INTRODUCTION,reverseExprs ));

                if(exprs.get(0) instanceof Implication && ((Implication)exprs.get(0)).operand1.logicEquals(exprs.get(0))) {
                    legalRules.add(new Rule(RuleType.IMPLICATION_ELIMINATION,exprs));

                }else if(exprs.get(1) instanceof Implication && ((Implication)exprs.get(1)).operand1.logicEquals(exprs.get(0))){
                    legalRules.add(new Rule(RuleType.IMPLICATION_ELIMINATION,reverseExprs));
                }

                if(exprs.get(0) instanceof Assumption){
                    legalRules.add(new Rule(RuleType.IMPLICATION_INTRODUCTION,exprs));
                }else if(exprs.get(1) instanceof Assumption){
                    legalRules.add(new Rule(RuleType.IMPLICATION_INTRODUCTION,reverseExprs));
                }

                return  legalRules;
            case 3:
                ArrayList<Expression> exprsOrder = new ArrayList<>();
                if(exprs.get(0) instanceof Disjunction && exprs.get(1) instanceof Implication && exprs.get(2) instanceof Implication){
                    Disjunction disj = (Disjunction)exprs.get(0);
                    Implication impl1 = (Implication)exprs.get(1);
                    Implication impl2 = (Implication)exprs.get(2);
                    if(impl1.operand2.logicEquals(impl2.operand2)) {
                        if (disj.operand1.logicEquals(impl1.operand1) && disj.operand2.logicEquals(impl2.operand1)) {
                            exprsOrder = exprs;
                        }else if(disj.operand2.logicEquals(impl1.operand1) && disj.operand1.logicEquals(impl2.operand1)){
                            exprsOrder.add(disj);
                            exprsOrder.add(impl2);
                            exprsOrder.add(impl1);
                        }
                    }
                }else if(exprs.get(0) instanceof Implication && exprs.get(1) instanceof Disjunction && exprs.get(2) instanceof Implication){
                    Disjunction disj = (Disjunction)exprs.get(1);
                    Implication impl1 = (Implication)exprs.get(0);
                    Implication impl2 = (Implication)exprs.get(2);
                    if(impl1.operand2.logicEquals(impl2.operand2)) {
                        if (disj.operand1.logicEquals(impl1.operand1) && disj.operand2.logicEquals(impl2.operand1)) {
                            exprsOrder = exprs;
                        }else if(disj.operand2.logicEquals(impl1.operand1) && disj.operand1.logicEquals(impl2.operand1)){
                            exprsOrder.add(disj);
                            exprsOrder.add(impl2);
                            exprsOrder.add(impl1);
                        }
                    }
                }else if(exprs.get(0) instanceof Implication && exprs.get(1) instanceof Implication && exprs.get(2) instanceof Disjunction){
                    Disjunction disj = (Disjunction)exprs.get(2);
                    Implication impl1 = (Implication)exprs.get(0);
                    Implication impl2 = (Implication)exprs.get(1);
                    if(impl1.operand2.logicEquals(impl2.operand2)) {
                        if (disj.operand1.logicEquals(impl1.operand1) && disj.operand2.logicEquals(impl2.operand1)) {
                            exprsOrder = exprs;
                        }else if(disj.operand2.logicEquals(impl1.operand1) && disj.operand1.logicEquals(impl2.operand1)){
                            exprsOrder.add(disj);
                            exprsOrder.add(impl2);
                            exprsOrder.add(impl1);
                        }
                    }
                }
                legalRules.add(new Rule(RuleType.DISJUNCTION_ELIMINATION,exprsOrder));
            default:
                throw new IllegalArgumentException("Too many arguments");
        }

    }
}
