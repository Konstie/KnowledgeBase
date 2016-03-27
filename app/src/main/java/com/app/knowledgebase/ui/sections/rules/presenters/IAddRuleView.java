package com.app.knowledgebase.ui.sections.rules.presenters;

import com.app.knowledgebase.models.Condition;

import io.realm.RealmList;

public interface IAddRuleView {
    void setupConditionsForRule(RealmList<Condition> conditions);
    void addNewCondition();
    void onEditCondition(int position);
    void onSaveThisRule();
}
