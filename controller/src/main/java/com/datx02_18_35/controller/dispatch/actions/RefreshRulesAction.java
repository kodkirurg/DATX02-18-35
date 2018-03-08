package com.datx02_18_35.controller.dispatch.actions;

import com.datx02_18_35.model.expression.Rule;

import java.util.Collection;

/**
 * Created by robin on 2018-03-08.
 */

public class RefreshRulesAction extends Action {
    public final Collection<Rule> rules;

    public RefreshRulesAction(Collection<Rule> rules) {
        this.rules = rules;
    }
}
