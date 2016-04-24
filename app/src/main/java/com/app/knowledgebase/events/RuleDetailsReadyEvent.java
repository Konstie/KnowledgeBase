package com.app.knowledgebase.events;

import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.ui.sections.rules.fragments.RuleDetailsType;

import io.realm.RealmList;

public class RuleDetailsReadyEvent {
    private RuleDetailsType ruleDetailsType;
    private RealmList<Condition> conditions;

    public RuleDetailsReadyEvent(RuleDetailsType ruleDetailsType, RealmList<Condition> conditions) {
        this.ruleDetailsType = ruleDetailsType;
        this.conditions = conditions;
    }

    public RuleDetailsType getRuleDetailsType() {
        return ruleDetailsType;
    }

    public RealmList<Condition> getConditions() {
        return conditions;
    }
}
