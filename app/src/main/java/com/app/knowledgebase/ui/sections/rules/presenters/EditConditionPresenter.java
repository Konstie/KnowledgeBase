package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.dao.FactsDao;
import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.helpers.IdHelper;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.ConditionPart;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class EditConditionPresenter extends BasePresenter implements IEditConditionPresenter {
    private List<String> conditionOperators;

    private Realm database;

    public EditConditionPresenter(Context context) {
        super(context);
        database = getDatabase();
        conditionOperators = new ArrayList<>(
                Arrays.asList(ConditionOperators.OR, ConditionOperators.AND)
        );
    }

    @Override
    public Fact getFactByTitle(String description) {
        return FactsDao.get().findFactByDescription(database, description);
    }

    @Override
    public Condition getLastCreatedCondition() {
        return ConditionsDao.get().getLastCreatedCondition(getDatabase());
    }

    @Override
    public void onSaveNewConditionClicked(int ruleId, int positionInRule, String newOperator, String newFact) {
        Fact conditionFact;
        if (FactsDao.get().findFactByDescription(getDatabase(), newFact) != null) {
            conditionFact = FactsDao.get().findFactByDescription(getDatabase(), newFact);
        } else {
            conditionFact = FactsDao.get().createFact(getDatabase(), newFact);
        }

        database.executeTransaction(realm -> {
            Condition newCondition = database.createObject(Condition.class);
            ConditionPart conditionPart = database.createObject(ConditionPart.class);
            conditionPart.setId(IdHelper.get().getGeneratedUniqueIdForConditionPart(getDatabase()));
            conditionPart.setConditionOperator(newOperator);

            conditionPart.setConditionFact(conditionFact);
            newCondition.setConditionItem(conditionPart);
            newCondition.setPositionInRule(positionInRule);
            newCondition.setId(IdHelper.get().getGeneratedUniqueIdForCondition(getDatabase()));

            Rule ruleToEdit = RulesDao.get().findRuleByUniqueId(database, ruleId);
            RealmList<Condition> conditions = ruleToEdit.getConditions();
            conditions.add(newCondition);
        });
    }

    @Override
    public void onUpdateNewConditionClicked(long conditionId, int ruleId, int positionInRule, String newOperator, String newFact) {
        database.executeTransaction(realm -> {
            Condition conditionToUpdate = ConditionsDao.get().findConditionById(getDatabase(), conditionId);
            ConditionPart conditionPart = database.createObject(ConditionPart.class);
            conditionPart.setId(IdHelper.get().getGeneratedUniqueIdForConditionPart(getDatabase()));
            conditionPart.setConditionOperator(newOperator);
            Fact conditionFact;
            if (FactsDao.get().findFactByDescription(getDatabase(), newFact) != null) {
                conditionFact = FactsDao.get().findFactByDescription(getDatabase(), newFact);
            } else {
                conditionFact = FactsDao.get().createFact(getDatabase(), newFact);
            }
            conditionPart.setConditionFact(conditionFact);
            conditionToUpdate.setConditionItem(conditionPart);
        });
    }

    public List<String> getAllFactsInText() {
        return FactsDao.get().getAllSortedFactsInText(database);
    }

    public List<String> getConditionOperators() {
        return conditionOperators;
    }
}
