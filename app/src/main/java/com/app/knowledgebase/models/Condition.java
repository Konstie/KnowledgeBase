package com.app.knowledgebase.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Condition extends RealmObject {
    @PrimaryKey
    private long id;
    private int ruleId;
    private ConditionPart conditionItem;
    private boolean isConsequent;
    private int positionInRule;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public ConditionPart getConditionItem() {
        return conditionItem;
    }

    public void setConditionItem(ConditionPart conditionItem) {
        this.conditionItem = conditionItem;
    }

    public int getPositionInRule() {
        return positionInRule;
    }

    public void setPositionInRule(int positionInRule) {
        this.positionInRule = positionInRule;
    }

    public void setIsConsequent(boolean isConsequent) {
        this.isConsequent = isConsequent;
    }

    public boolean isConsequent() {
        return isConsequent;
    }
}
