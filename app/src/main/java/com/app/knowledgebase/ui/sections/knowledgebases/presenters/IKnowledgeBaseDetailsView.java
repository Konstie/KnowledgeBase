package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import com.app.knowledgebase.models.Rule;

import io.realm.RealmResults;

public interface IKnowledgeBaseDetailsView {
    void showKnowledgeBaseDetails(RealmResults<Rule> rulesList);
    void retrieveKnowledgeBaseId();
    void openRuleDetails(int ruleId);
}
