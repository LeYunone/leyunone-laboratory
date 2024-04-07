package com.leyunone.laboratory.core.rule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

import java.util.List;

public class AllEstablishRule {

    private List<Rule> rules;

    private Runnable runnable;

    public AllEstablishRule(List<Rule> rules, Runnable runnable) {
        this.rules = rules;
        this.runnable = runnable;
    }

    public boolean evaluate(Facts facts) {
        boolean ok = false;
        for (Rule rule : rules) {
            ok = rule.evaluate(facts);
            if (!ok) break;
        }
        return ok;
    }

    public void execute(Facts facts) {
        if (evaluate(facts)) {
            runnable.run();
        }
    }
}