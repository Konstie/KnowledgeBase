package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

public interface IKnowledgeBaseDetailsPresenter {
    void onRulesListFilled(int knowledgeBaseId);
    void getKnowledgeBaseId();
    void openRuleDetails(int positionInBase);
}
