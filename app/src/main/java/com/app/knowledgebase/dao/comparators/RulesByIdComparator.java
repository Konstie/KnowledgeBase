package com.app.knowledgebase.dao.comparators;

import com.app.knowledgebase.models.Rule;

import java.util.Comparator;

public class RulesByIdComparator implements Comparator<Rule> {
    @Override
    public int compare(Rule firstRule, Rule secondRule) {
        return firstRule.getPositionInBase() - secondRule.getPositionInBase();
    }
}
