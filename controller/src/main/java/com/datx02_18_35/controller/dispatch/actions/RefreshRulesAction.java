package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.model.expression.Rule;

import java.util.List;

/**
 * Created by robin on 2018-03-08.
 */

public class RefreshRulesAction extends Action {
    public final List<Rule> rules;

    public RefreshRulesAction(List<Rule> rules) {
        if (rules == null) {
            throw new IllegalArgumentException("rules can't be null");
        }
        this.rules = rules;
    }
}
