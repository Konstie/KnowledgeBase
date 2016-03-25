package com.app.knowledgebase.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ConditionPart extends RealmObject {
    @PrimaryKey private String uniqueId;
    private String conditionOperator;
    private String conditionFact;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getConditionOperator() {
        return conditionOperator;
    }

    public void setConditionOperator(String conditionOperator) {
        this.conditionOperator = conditionOperator;
    }

    public String getConditionFact() {
        return conditionFact;
    }

    public void setConditionFact(String conditionFact) {
        this.conditionFact = conditionFact;
    }
}
