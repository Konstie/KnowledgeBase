package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.FactsDao;
import com.app.knowledgebase.dao.StrategiesDao;
import com.app.knowledgebase.helpers.IdHelper;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.IteratedFact;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Strategy;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NewKnowledgeBasePresenter extends BasePresenter implements INewKnowledgeBasePresenter {
    private INewKnowledgeBaseView view;
    private RealmList<Strategy> selectedStrategies;
    private RealmList<Fact> startFacts;

    public NewKnowledgeBasePresenter(Context context, INewKnowledgeBaseView view) {
        super(context);
        this.view = view;
        this.selectedStrategies = new RealmList<>();
        this.startFacts = new RealmList<>();
    }

    @Override
    public void onStrategiesInitialized() {
        List<String> strategies = StrategiesDao.get().getAllStrategiesTitles(getDatabase());
        view.fillStrategiesSpinner(strategies);
    }

    @Override
    public void onFactsInitialized() {
        List<String> facts = FactsDao.get().getAllSortedFactsInText(getDatabase());
        view.fillFactsSpinner(facts);
    }

    @Override
    public void setCheckedStrategies(boolean[] checkedStrategies) {
        for (int index = 0; index < checkedStrategies.length; index++) {
            Strategy currentStrategy = StrategiesDao.get().getStrategyById(getDatabase(), index + 1);
            if (checkedStrategies[index]) {
                selectedStrategies.add(currentStrategy);
            }
        }
    }

    @Override
    public void setCheckedStartFacts(boolean[] checkedStartFacts) {
        List<String> facts = FactsDao.get().getAllSortedFactsInText(getDatabase());
        for (int index = 0; index < checkedStartFacts.length; index++) {
            if (checkedStartFacts[index]) {
                startFacts.add(FactsDao.get().findFactByDescription(getDatabase(), facts.get(index)));
            }
        }
    }

    public RealmList<Strategy> getSelectedStrategies() {
        return selectedStrategies;
    }

    public RealmList<Fact> getStartFacts() {
        return startFacts;
    }

    private RealmList<IteratedFact> getIteratedFacts(RealmList<Fact> facts) {
        RealmList<IteratedFact> iteratedFacts = new RealmList<>();
        for (Fact fact : facts) {
            IteratedFact iteratedFact = FactsDao.get().createIteratedFact(getDatabase(), fact, true);
            iteratedFacts.add(iteratedFact);
        }
        return iteratedFacts;
    }

    @Override
    public void onSaveNewKnowledgeBaseClicked(String title, RealmList<Strategy> chosenStrategies, RealmList<Fact> startFacts) {
        RealmList<IteratedFact> iteratedFacts = getIteratedFacts(startFacts);

        Realm database = getDatabase();
        database.executeTransaction(realm -> {
            KnowledgeBase knowledgeBase = database.createObject(KnowledgeBase.class);
            knowledgeBase.setId(IdHelper.get().getGeneratedUniqueIdForKnowledgeBase(database));
            knowledgeBase.setTitle(title);
            knowledgeBase.setStrategies(chosenStrategies);
            knowledgeBase.setResolveIterations(null);
            knowledgeBase.setStartFacts(iteratedFacts);
            knowledgeBase.setRules(null);
        });
    }
}
