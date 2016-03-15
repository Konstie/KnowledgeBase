package com.app.knowledgebase.models;

import java.util.List;

import io.realm.RealmObject;

public class Condition extends RealmObject {
    public static final int OPERATOR_POS = 0;
    public static final int FACT_POS = 1;
    public static final int CONDITION_CAPACITY = 2;

    private int positionInRule;
    private List<String> conditionItem;

    public int getPositionInRule() {
        return positionInRule;
    }

    public void setPositionInRule(int positionInRule) {
        this.positionInRule = positionInRule;
    }

    public List<String> getConditionItem() {
        return conditionItem;
    }

    public void setConditionItem(List<String> conditionItem) {
        this.conditionItem = conditionItem;
    }

    public String getConditionFact() {
        String conditionFactTitle = conditionItem.get(FACT_POS);
        if (conditionItem.size() == CONDITION_CAPACITY) {
            return conditionFactTitle;
        } else {
            return "";
        }
    }

    public void setConditionFact(String newConditionFact) {
        conditionItem.set(FACT_POS, newConditionFact);
    }

    public void setConditionOperator(String conditionOperator) {
        conditionItem.set(OPERATOR_POS, conditionOperator);
    }

    public String getConditionOperator() {
        String conditionOperatorTitle = conditionItem.get(OPERATOR_POS);
        if (conditionItem.size() == CONDITION_CAPACITY) {
            return conditionOperatorTitle;
        } else {
            return "";
        }
    }
}
