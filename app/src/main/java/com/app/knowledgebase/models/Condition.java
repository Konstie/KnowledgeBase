package com.app.knowledgebase.models;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Condition extends RealmObject implements Serializable {
    @PrimaryKey private long id;
    private int positionInRule;
    private ConditionPart conditionItem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
