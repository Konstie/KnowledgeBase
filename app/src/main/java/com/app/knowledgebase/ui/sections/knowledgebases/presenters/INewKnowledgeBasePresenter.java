package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.Strategy;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

public interface INewKnowledgeBasePresenter {
    void onStrategiesInitialized();
    void onFactsInitialized();
    void setCheckedStrategies(boolean[] checkedStrategies);
    void setCheckedStartFacts(boolean[] checkedStartFacts);
    void onSaveNewKnowledgeBaseClicked(String title, RealmList<Strategy> chosenStrategies, RealmList<Fact> startFacts);
}
