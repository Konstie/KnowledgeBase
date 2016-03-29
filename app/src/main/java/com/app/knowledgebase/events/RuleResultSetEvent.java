package com.app.knowledgebase.events;

import com.app.knowledgebase.models.Condition;

import io.realm.RealmList;

public class RuleResultSetEvent {
    private String resultFactTitle;
    private RealmList<Condition> conditions;

    public RuleResultSetEvent(String resultFactTitle, RealmList<Condition> conditions) {
        this.resultFactTitle = resultFactTitle;
        this.conditions = conditions;
    }

    public String getResultFactTitle() {
        return resultFactTitle;
    }

    public RealmList<Condition> getConditions() {
        return conditions;
    }
}
