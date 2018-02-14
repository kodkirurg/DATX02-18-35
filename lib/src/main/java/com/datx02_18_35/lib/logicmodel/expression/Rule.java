package com.datx02_18_35.lib.logicmodel.expression;

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
}
