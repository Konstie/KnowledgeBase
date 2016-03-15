package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.dao.FactsDao;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.util.List;

import io.realm.Realm;

public class EditConditionPresenter extends BasePresenter implements IEditConditionPresenter {
    private Realm database;

    public EditConditionPresenter(Context context) {
        super(context);
        database = getDatabase();
    }

    @Override
    public Fact getFactByTitle(String description) {
        return FactsDao.get().findFactByDescription(database, description);
    }

    @Override
    public void onSaveConditionClicked(String knowledgeBaseName, String newFact, int position) {
        Condition conditionToUpdate = ConditionsDao.get().findConditionByPositionInKnowledgeBase(database, knowledgeBaseName, position);
        Fact chosenFact = FactsDao.get().findFactByDescription(database, newFact);
        database.executeTransaction(realm -> {
            conditionToUpdate.setConditionFact(chosenFact.toString());
        });
    }

    public List<String> getAllFactsInText() {
        return FactsDao.get().getAllSortedFactsInText(database);
    }
}
