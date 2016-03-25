package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

public interface IKnowledgeBasesListPresenter {
    void obtainDatabasesList();
    void onAddNewDatabase();
    void openDatabaseDetails(int position);
}