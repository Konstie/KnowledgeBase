package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.dao.FactsDao;
import com.app.knowledgebase.dao.RulesDao;
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
                Arrays.asList(ConditionOperators.NONE, ConditionOperators.OR, ConditionOperators.AND)
        );
    }

    @Override
    public Fact getFactByTitle(String description) {
        return FactsDao.get().findFactByDescription(database, description);
    }

    @Override
    public void onSaveNewConditionClicked(int positionInRule, String conditionId, String newOperator, String newFact) {
        database.executeTransaction(realm -> {
//            Condition newCondition = database.createObject(Condition.class);
//            ConditionPart conditionPart = database.createObject(ConditionPart.class);
//            conditionPart.setUniqueId(conditionId + "#" + 0);
//            conditionPart.setConditionOperator(newOperator);
//            conditionPart.setConditionFact(newFact);
//            newCondition.setConditionItem(conditionPart);
//            newCondition.setPositionInRule(positionInRule);
//            newCondition.setUniqueId(conditionId);
//
//            Rule ruleToEdit = RulesDao.get().findRuleByUniqueId(
//                    database, conditionId.substring(0, conditionId.indexOf("#", conditionId.indexOf("#") + 1))
//            );
//            RealmList<Condition> conditions = ruleToEdit.getConditions();
//            conditions.add(newCondition);
        });
    }

    public List<String> getAllFactsInText() {
        return FactsDao.get().getAllSortedFactsInText(database);
    }

    public List<String> getConditionOperators() {
        return conditionOperators;
    }
}
