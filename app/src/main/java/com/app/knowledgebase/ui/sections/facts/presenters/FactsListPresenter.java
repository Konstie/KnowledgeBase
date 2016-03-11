package com.app.knowledgebase.ui.sections.facts.presenters;

public interface FactsListPresenter extends DatabasePresenterListener {
    void onResume();
    void onFactItemClicked(int position);
    void onAddFactClicked();
    void onDestroy();
}
