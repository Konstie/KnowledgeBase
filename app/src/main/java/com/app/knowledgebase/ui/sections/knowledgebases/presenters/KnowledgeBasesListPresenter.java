package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import android.content.Context;
import android.widget.Toast;

import com.app.knowledgebase.dao.KnowledgeBaseDao;
import com.app.knowledgebase.dao.StrategiesDao;
import com.app.knowledgebase.helpers.PreferencesHelper;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import io.realm.RealmResults;

public class KnowledgeBasesListPresenter extends BasePresenter implements IKnowledgeBasesListPresenter {

    private Context context;
    private IConflictsListView conflictsListView;

    public KnowledgeBasesListPresenter(Context context, IConflictsListView conflictsListView) {
        super(context);
        this.context = context;
        this.conflictsListView = conflictsListView;
    }

    private RealmResults<KnowledgeBase> getKnowledgeBasesList() {
        return KnowledgeBaseDao.get().findAllKnowledgeBases(getDatabase());
    }

    @Override
    public void onCreate() {
        if (!PreferencesHelper.get().areStrategiesSaved()) {
            StrategiesDao.get().setAllStrategies(getDatabase());
            PreferencesHelper.get().setStrategiesSaved(true);
        }
    }

    @Override
    public void obtainDatabasesList() {
        conflictsListView.fillConflictsList(getKnowledgeBasesList());
    }

    @Override
    public void onAddNewDatabase() {
        if (PreferencesHelper.get().areStrategiesSaved()) {
            conflictsListView.addNewBase();
        } else {
            Toast.makeText(context, "Strategies are not stored to database yet, Please, wait for a while and try again...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMenu() {
        conflictsListView.showKnowledgeBaseMenu();
    }

    @Override
    public void openDatabaseDetails(int position) {

    }
}
