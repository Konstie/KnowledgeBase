package com.app.knowledgebase.ui.sections.facts.presenters;

public interface CreateFactPresenter extends DatabasePresenterListener {
    void onAddFactClicked(String factDescription);
}
