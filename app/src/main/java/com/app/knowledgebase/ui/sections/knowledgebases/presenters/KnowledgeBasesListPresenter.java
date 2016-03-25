package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.KnowledgeBaseDao;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import io.realm.RealmResults;

public class KnowledgeBasesListPresenter extends BasePresenter implements IKnowledgeBasesListPresenter {

    private IConflictsListView conflictsListView;

    public KnowledgeBasesListPresenter(Context context, IConflictsListView conflictsListView) {
        super(context);
        this.conflictsListView = conflictsListView;
    }

    private RealmResults<KnowledgeBase> getKnowledgeBasesList() {
        return KnowledgeBaseDao.get().findAllKnowledgeBases(getDatabase());
    }

    @Override
    public void obtainDatabasesList() {
        conflictsListView.fillConflictsList(getKnowledgeBasesList());
    }

    @Override
    public void onAddNewDatabase() {
        conflictsListView.addNewBase();
    }

    @Override
    public void openDatabaseDetails(int position) {

    }
}
