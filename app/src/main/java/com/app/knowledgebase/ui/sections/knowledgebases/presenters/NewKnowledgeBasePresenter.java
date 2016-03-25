package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.StrategiesDao;
import com.app.knowledgebase.helpers.IdHelper;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Strategy;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class NewKnowledgeBasePresenter extends BasePresenter implements INewKnowledgeBasePresenter {
    INewKnowledgeBaseView view;

    public NewKnowledgeBasePresenter(Context context, INewKnowledgeBaseView view) {
        super(context);
        this.view = view;
    }

    @Override
    public void onStrategiesInitialized() {
        List<String> strategies = StrategiesDao.get().getAllStrategiesTitles(getDatabase());
        view.fillStrategiesSpinner(strategies);
    }

    @Override
    public void onSaveNewKnowledgeBaseClicked(String title, RealmList<Strategy> chosenStrategies) {
        Realm database = getDatabase();
        database.executeTransaction(realm -> {
            KnowledgeBase knowledgeBase = database.createObject(KnowledgeBase.class);
            knowledgeBase.setId(IdHelper.get().getGeneratedUniqueId(database, KnowledgeBase.class));
            knowledgeBase.setTitle(title);
            knowledgeBase.setStrategies(chosenStrategies);
            knowledgeBase.setResolveIterations(null);
            knowledgeBase.setStartFacts(null);
            knowledgeBase.setRules(null);
        });
    }

    @Override
    public int[] getSelectedStrategiesIdsArray(List<Integer> selectedIndexes) {
        int[] strategiesIds = new int[selectedIndexes.size()];
        for (int i = 0; i < strategiesIds.length; i++) {
            strategiesIds[i] = selectedIndexes.get(i);
        }
        return strategiesIds;
    }

    @Override
    public RealmList<Strategy> getSelectedStrategies(List<String> strategies) {
        RealmList<Strategy> selectedStrategies = new RealmList<>();
        for (String strategyName : strategies) {
            selectedStrategies.add(StrategiesDao.get().getStrategyByName(getDatabase(), strategyName));
        }
        return selectedStrategies;
    }


}
