package com.app.knowledgebase.ui.sections.facts.presenter;

import android.content.Context;

import com.app.knowledgebase.dao.FactsDao;
import com.app.knowledgebase.helpers.IdHelper;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import io.realm.Realm;
import io.realm.RealmResults;

public class FactsListPresenter extends BasePresenter implements IFactsListPresenter {
    private Realm database;
    private IFactsView factsView;

    public FactsListPresenter(Context context, IFactsView factsView) {
        super(context);
        this.factsView = factsView;
    }

    @Override
    public void initializeDatabase() {
        database = getDatabase();
    }

    public RealmResults<Fact> getAllFacts() {
        return database.where(Fact.class).findAll();
    }

    @Override
    public void showAllFacts() {
        factsView.addItems(getAllFacts());
    }

    @Override
    public void onFactItemClicked(int position) {
        factsView.showEditFactDialog(position);
    }

    @Override
    public void onSaveFactClicked(Fact fact, String newDescription) {
        Fact factToUpdate = FactsDao.get().findFactByDescription(database, fact.getDescription());
        FactsDao.get().updateFact(database, factToUpdate, newDescription);
    }

    @Override
    public void onRemoveFactClicked(Fact fact) {
        Fact factToRemove = FactsDao.get().findFactByDescription(database, fact.getDescription());
        FactsDao.get().removeFact(database, factToRemove);
    }

    @Override
    public void onAddFactClicked(String factDescription) {
        database.beginTransaction();

        Fact fact = database.createObject(Fact.class);
        fact.setId(IdHelper.get().getGeneratedUniqueIdForFact(getDatabase()));
        fact.setDescription(factDescription);

        database.commitTransaction();
    }

    @Override
    public void onDestroy() {
        database.close();
    }
}