package com.app.knowledgebase.ui.sections.rules.presenters;

import com.app.knowledgebase.models.Condition;

import io.realm.RealmList;
import io.realm.RealmResults;

public interface IConditionsListView {
    void setupConditionsForRule(RealmResults<Condition> conditions);
    void addNewCondition();
    void onEditCondition(int position);
}
