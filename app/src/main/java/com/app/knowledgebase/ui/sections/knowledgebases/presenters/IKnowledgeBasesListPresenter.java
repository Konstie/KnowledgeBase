package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

public interface IKnowledgeBasesListPresenter {
    void onCreate();
    void obtainDatabasesList();
    void onAddNewDatabase();
    void showMenu();
    void openDatabaseDetails(int position);
}