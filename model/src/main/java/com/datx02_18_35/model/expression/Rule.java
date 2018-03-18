package com.datx02_18_35.model.expression;



import com.datx02_18_35.model.game.IllegalRuleException;
import com.datx02_18_35.model.game.TestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import java.util.Iterator;
import java.util.List;

/**
 * Created by robin on 2018-02-14.
 */

public class Rule {
    public final RuleType type;
    public final List<Expression> expressions;

    public Rule(RuleType type, Expression... expressions) {
        this(type, Arrays.asList(expressions));
    }

    public Rule(RuleType type, List<Expression> expressions) {
        assert expressions != null;
        this.type = type;
        this.expressions = new ArrayList<>(expressions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name());
        sb.append("(");
        Iterator<Expression> iter = expressions.iterator();
        if (iter.hasNext()) {
            sb.append(iter.next().toString());
            while (iter.hasNext()) {
                sb.append(",");
                sb.append(iter.next().toString());
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static List<Rule> getLegalRules( Expression assumption, List<Expression> selection){

        switch(selection.size()) {
            case 0: {
                // No applicable rules
                return new ArrayList<>();
            }
            case 1: {
                final List<Rule> legalRules = new ArrayList<>();
                final Expression fst = selection.get(0);

                if (assumption != null) {
                    legalRules.add(new Rule(RuleType.IMPLICATION_INTRODUCTION, assumption, fst));
                }

                if (fst instanceof Conjunction) {
                    legalRules.add(new Rule(RuleType.CONJUNCTION_ELIMINATION, fst));
                }
                else if (fst instanceof Absurdity) {
                    legalRules.add(new Rule(RuleType.ABSURDITY_ELIMINATION, fst, null));
                }

                legalRules.add(new Rule(RuleType.DISJUNCTION_INTRODUCTION, fst, null));
                legalRules.add(new Rule(RuleType.DISJUNCTION_INTRODUCTION, null, fst));

                return legalRules;
            }
            case 2: {
                final List<Rule> legalRules = new ArrayList<>();
                final Expression fst = selection.get(0);
                final Expression snd = selection.get(1);

                legalRules.add(new Rule(RuleType.CONJUNCTION_INTRODUCTION, selection));

                if (fst instanceof Implication && ((Implication) fst).operand1.equals(snd)) {
                    legalRules.add(new Rule(RuleType.IMPLICATION_ELIMINATION, selection));
                }
                return legalRules;
            }
            case 3: {
                final List<Rule> legalRules = new ArrayList<>();
                final Expression fst = selection.get(0);
                final Expression snd = selection.get(1);
                final Expression thd = selection.get(2);

                // Disjunction elimination check:
                // fst:     A | B
                // snd:     A -> C
                // thd:     B -> C
                // result:  C
                // Check root operators
                if (   fst instanceof Disjunction
                    && snd instanceof Implication
                    && thd instanceof Implication) {

                    final Disjunction disj = (Disjunction)fst;
                    final Implication impl1 = (Implication)snd;
                    final Implication impl2 = (Implication)thd;

                    // Check that the assumptions in the implications
                    // correspond with the operands of the disjunction
                    // and that the implications matches
                    if (   disj.getOperand1().equals(impl1.getOperand1())
                        && disj.getOperand2().equals(impl2.getOperand1())
                        && impl1.getOperand2().equals(impl2.getOperand2())) {

                        legalRules.add(new Rule(RuleType.DISJUNCTION_ELIMINATION, selection));
                    }
                }
                return legalRules;
            }
            default: {
                // Selection 4 or more expressions
                // No applicable rules
                return new ArrayList<>();
            }
        }
    }

    public static Rule finishIncompleteRule(Rule rule,Expression expression) throws IllegalRuleException {
        List<Expression> expressions = new ArrayList<>();
        switch (rule.type) {
            case ABSURDITY_ELIMINATION:
                if (false == rule.expressions.get(0) instanceof Absurdity) {
                    throw new IllegalRuleException(rule, "First argument must be Absurdity");
                }
                if (rule.expressions.get(1) != null) {
                    throw new IllegalRuleException(rule, "Second argument must be null");
                }
                return new Rule(RuleType.ABSURDITY_ELIMINATION, rule.expressions.get(0), expression);
            case DISJUNCTION_INTRODUCTION:
                if (   rule.expressions.get(0) == null
                    && rule.expressions.get(1) != null) {
                    return new Rule(RuleType.DISJUNCTION_INTRODUCTION, expression, rule.expressions.get(1));
                } else if (rule.expressions.get(0) != null
                        && rule.expressions.get(1) == null){
                    return new Rule(RuleType.DISJUNCTION_INTRODUCTION, rule.expressions.get(0), expression);
                } else {
                    throw new IllegalRuleException(rule, "Exactly one argument must be null!");
                }
            default:
                throw new IllegalRuleException(rule, "Rule is the wrong type!");
        }
    }
}
