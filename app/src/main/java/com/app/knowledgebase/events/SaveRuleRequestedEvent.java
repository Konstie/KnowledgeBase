package com.app.knowledgebase.events;

import com.app.knowledgebase.models.Condition;

import io.realm.RealmList;

public class SaveRuleRequestedEvent {
    private RealmList<Condition> conditions;

    public SaveRuleRequestedEvent(RealmList<Condition> conditions) {
        this.conditions = conditions;
    }

    public RealmList<Condition> getConditions() {
        return conditions;
    }
}
