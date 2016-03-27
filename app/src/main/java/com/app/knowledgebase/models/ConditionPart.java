package com.app.knowledgebase.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ConditionPart extends RealmObject {
    @PrimaryKey private int id;
    private String conditionOperator;
    private Fact conditionFact;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConditionOperator() {
        return conditionOperator;
    }

    public void setConditionOperator(String conditionOperator) {
        this.conditionOperator = conditionOperator;
    }

    public Fact getConditionFact() {
        return conditionFact;
    }

    public void setConditionFact(Fact conditionFact) {
        this.conditionFact = conditionFact;
    }
}
