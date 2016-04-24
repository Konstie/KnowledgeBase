package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;
import android.util.Log;

import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;
import com.app.knowledgebase.ui.sections.rules.fragments.RuleDetailsType;

import io.realm.RealmList;
import io.realm.RealmResults;

public class RuleConditionsPresenter extends BasePresenter implements IRuleConditionsPresenter {
    private IConditionsListView conditionsListView;
    private int ruleId;

    public RuleConditionsPresenter(Context context, IConditionsListView conditionsListView, int ruleId) {
        super(context);
        this.conditionsListView = conditionsListView;
        this.ruleId = ruleId;
    }

    @Override
    public void onConditionsInitialized(RuleDetailsType ruleDetailsType) {
        Log.w("AddRulePresenter", "Rule id: " + ruleId);
        RealmResults<Condition> conditionResults;
        if (ruleDetailsType == RuleDetailsType.CONDITIONS) {
            conditionResults = getConditionsForRule();
        } else {
            conditionResults = getConsequentsForRule();
        }

        conditionsListView.setupConditionsForRule(conditionResults);
    }

    @Override
    public void onAddConditionClicked() {
        conditionsListView.addNewCondition();
    }

    @Override
    public void onEditConditionClicked(int position) {
        conditionsListView.onEditCondition(position);
    }

    private Rule getRuleById() {
        return RulesDao.get().findRuleByUniqueId(getDatabase(), ruleId);
    }

    public RealmResults<Condition> getConditionsForRule() {
        return ConditionsDao.get().findConditionsByRuleId(getDatabase(), ruleId);
    }

    public RealmList<Condition> getConditionsList(RealmResults<Condition> conditions) {
        RealmList<Condition> conditionsList = new RealmList<>();
        for (Condition condition : conditions) {
            conditionsList.add(condition);
        }
        return conditionsList;
    }

    public RealmResults<Condition> getConsequentsForRule() {
        return ConditionsDao.get().findConsequentsByRuleId(getDatabase(), ruleId);
    }
}
