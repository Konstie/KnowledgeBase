package com.app.knowledgebase.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Condition extends RealmObject {
    @PrimaryKey private String uniqueId;
    private int positionInRule;
    private ConditionPart conditionItem;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getPositionInRule() {
        return positionInRule;
    }

    public void setPositionInRule(int positionInRule) {
        this.positionInRule = positionInRule;
    }

    public ConditionPart getConditionItem() {
        return conditionItem;
    }

    public void setConditionItem(ConditionPart conditionItem) {
        this.conditionItem = conditionItem;
    }
}
