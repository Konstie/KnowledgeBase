package com.app.knowledgebase.ui.sections.facts.presenters;

import android.content.Context;

import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.ui.sections.abs.BasePresenter;

import io.realm.Realm;

public class CreateFactPresenterImpl extends BasePresenter implements CreateFactPresenter {
    private static final String DB_FACTS = "facts.realm";

    private Realm database;

    public CreateFactPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void initializeDatabase() {
        database = getDatabase(DB_FACTS);
    }

    private void addNewFact(String factDescription) {
        database.beginTransaction();

        Fact fact = database.createObject(Fact.class);
        fact.setDescription(factDescription);

        database.commitTransaction();
    }

    @Override
    public void onAddFactClicked(String factDescription) {
        addNewFact(factDescription);
    }
}
