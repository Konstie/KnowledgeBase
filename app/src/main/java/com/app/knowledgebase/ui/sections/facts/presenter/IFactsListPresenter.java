package com.app.knowledgebase.ui.sections.facts.presenter;

import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.ui.sections.abs.presenter.IDatabasePresenter;

public interface IFactsListPresenter extends IDatabasePresenter {
    void showAllFacts();
    void onFactItemClicked(int position);
    void onSaveFactClicked(Fact fact, String newDescription);
    void onRemoveFactClicked(Fact fact);
    void onAddFactClicked(String factDescription);
    void onDestroy();
}
