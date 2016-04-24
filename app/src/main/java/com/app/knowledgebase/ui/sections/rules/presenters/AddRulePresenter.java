package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;
import android.util.Log;

import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.dao.KnowledgeBaseDao;
import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.util.Date;

import io.realm.RealmList;

public class AddRulePresenter extends BasePresenter implements IAddRulePresenter {
    private IAddRuleView addRuleView;

    public AddRulePresenter(Context context, IAddRuleView addRuleView) {
        super(context);
        this.addRuleView = addRuleView;
    }

    @Override
    public void onSaveRuleClicked(int baseId, int ruleId, RealmList<Condition> conditionList, RealmList<Condition> consequentsList, Date currentRuleDate, boolean newRule) {
        KnowledgeBase knowledgeBase = KnowledgeBaseDao.get().findKnowledgeBaseById(getDatabase(), baseId);
        RealmList<Rule> rules = knowledgeBase.getRules();
        Rule currentRule = RulesDao.get().findRuleByUniqueId(getDatabase(), ruleId);

        Log.w("Conditions count ", "" + conditionList.size());
        getDatabase().executeTransaction(realm -> {
            Log.w("Add rule presenter", "Rule saved: id #" + ruleId + ", baseId: " + baseId
                    + ", conditions count: " + conditionList.size());
            currentRule.setDescription("Test #" + ruleId);
            currentRule.setId(ruleId);
            currentRule.setDateAdded(currentRuleDate);
            currentRule.setConditions(conditionList);
            currentRule.setFactsCount(conditionList.size());
            currentRule.setConsequents(consequentsList);
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
