package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;
import android.util.Log;

import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.dao.FactsDao;
import com.app.knowledgebase.dao.KnowledgeBaseDao;
import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmResults;

public class AddRulePresenter extends BasePresenter implements IAddRulePresenter {
    private IAddRuleView addRuleView;

    public AddRulePresenter(Context context, IAddRuleView addRuleView) {
        super(context);
        this.addRuleView = addRuleView;
    }

    @Override
    public void onConditionsInitialized(int ruleId) {
        Log.w("AddRulePresenter", "Rule id: " + ruleId);
        RealmResults<Condition> conditions = ConditionsDao.get().findConditionsByRuleId(getDatabase(), ruleId);
        RealmList<Condition> conditionsForRule = new RealmList<>();
        conditionsForRule.addAll(conditions.subList(0, conditions.size()));
        Log.w("AddRulePresenter", "Conditions count: " + conditionsForRule.size());
        addRuleView.setupConditionsForRule(conditionsForRule);
    }

    @Override
    public void onAddConditionClicked() {
        addRuleView.addNewCondition();
    }

    @Override
    public void onEditConditionClicked(int position) {
        addRuleView.onEditCondition(position);
    }

    @Override
    public void onSaveRuleClicked(int baseId, int ruleId, RealmList<Condition> conditionList, String resultFact, Date currentRuleDate, boolean newRule) {
        KnowledgeBase knowledgeBase = KnowledgeBaseDao.get().findKnowledgeBaseById(getDatabase(), baseId);
        RealmList<Rule> rules = knowledgeBase.getRules();
        Rule currentRule = RulesDao.get().findRuleByUniqueId(getDatabase(), ruleId);

        final Fact fact;
        if (FactsDao.get().findFactByDescription(getDatabase(), resultFact) == null) {
            fact = FactsDao.get().createFact(getDatabase(), resultFact);
        } else {
            fact = FactsDao.get().findFactByDescription(getDatabase(), resultFact);
        }

        Log.w("Conditions count ", "" + conditionList.size());
        Log.w("Saved fact ", fact.getDescription());
        getDatabase().executeTransaction(realm -> {
            Log.w("Add rule presenter", "Rule saved: id #" + ruleId + ", baseId: " + baseId
                    + ", conditions count: " + conditionList.size());
            currentRule.setDescription("Test #" + ruleId);
            currentRule.setId(ruleId);
            currentRule.setDateAdded(currentRuleDate);
            currentRule.setConditions(conditionList);
            currentRule.setFactsCount(conditionList.size());
            currentRule.setConsequentFact(fact);
        });

        getDatabase().executeTransaction(realm -> {
            RealmList<Rule> newRulesList = new RealmList<Rule>();
            Log.e("AddRule", "Rules to add: " + newRulesList.size());
            newRulesList.addAll(rules);
            if (newRule) {
                newRulesList.add(currentRule);
            }
            knowledgeBase.setRules(newRulesList);
        });
        addRuleView.onSaveThisRule();
    }

    public void onRemoveConditionClicked(long conditionId) {
        Condition condition = ConditionsDao.get().findConditionById(getDatabase(), conditionId);
        getDatabase().executeTransaction(realm -> {
            condition.removeFromRealm();
        });
    }

    public Rule getCurrentRule(int ruleId) {
        if (ruleId != -1) {
            return RulesDao.get().findRuleByUniqueId(getDatabase(), ruleId);
        } else {
            return null;
        }
    }

    public Rule getLastCreatedRule() {
        return RulesDao.get().getLastCreatedRule(getDatabase());
    }
}
