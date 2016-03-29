package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import com.app.knowledgebase.models.Rule;

import io.realm.RealmList;
import io.realm.RealmResults;

public interface IKnowledgeBaseDetailsView {
    void showKnowledgeBaseDetails(RealmList<Rule> rulesList);
    void retrieveKnowledgeBaseId();
    void openRuleDetails(int ruleId);
}
