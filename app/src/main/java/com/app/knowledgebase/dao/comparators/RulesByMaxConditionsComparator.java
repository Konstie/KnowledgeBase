package com.app.knowledgebase.dao.comparators;

import com.app.knowledgebase.models.Rule;

import java.util.Comparator;

public class RulesByMaxConditionsComparator implements Comparator<Rule> {
    @Override
    public int compare(Rule firstRule, Rule secondRule) {
        int result = firstRule.getFactsCount() - secondRule.getFactsCount();
        if (result != 0) return result;

        return firstRule.getPositionInBase() - secondRule.getPositionInBase();
    }
}
