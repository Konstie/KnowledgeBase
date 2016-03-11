package com.app.knowledgebase.ui.sections.facts.presenters;

import android.content.Context;

import com.app.knowledgebase.ui.sections.abs.BasePresenter;

import io.realm.Realm;

public class FactsListPresenterImpl extends BasePresenter implements FactsListPresenter {
    private static final String DB_FACTS = "facts.realm";

    public FactsListPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void initializeDatabase() {
        Realm factsDatabase = getDatabase(DB_FACTS);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onFactItemClicked(int position) {

    }

    @Override
    public void onAddFactClicked() {

    }

    @Override
    public void onDestroy() {

    }
}
