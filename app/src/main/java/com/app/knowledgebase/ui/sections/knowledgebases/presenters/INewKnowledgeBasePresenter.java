package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import com.app.knowledgebase.models.Strategy;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

public interface INewKnowledgeBasePresenter {
    void onStrategiesInitialized();
    void onSaveNewKnowledgeBaseClicked(String title, RealmList<Strategy> chosenStrategies);
    int[] getSelectedStrategiesIdsArray(List<Integer> selectedIndexes);
    List<Strategy> getSelectedStrategies(List<String> strategies);
}
