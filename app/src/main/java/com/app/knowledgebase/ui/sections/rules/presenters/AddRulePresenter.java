package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.dao.KnowledgeBaseDao;
import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.helpers.IdHelper;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;

public class AddRulePresenter extends BasePresenter implements IAddRulePresenter {
    private IAddRuleView addRuleView;

    public AddRulePresenter(Context context, IAddRuleView addRuleView) {
        super(context);
        this.addRuleView = addRuleView;
    }

    @Override
    public void onConditionsInitialized(int ruleId) {
        Rule currentRule = RulesDao.get().findRuleByUniqueId(getDatabase(), ruleId);
        RealmList<Condition> conditions = currentRule.getConditions();
        addRuleView.setupConditionsForRule(conditions);
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
    public void onSaveRuleClicked(int baseId, long ruleId, RealmList<Condition> conditionList, Fact resultFact, Date currentRuleDate) {
        KnowledgeBase knowledgeBase = KnowledgeBaseDao.get().findKnowledgeBaseById(getDatabase(), baseId);
        RealmList<Rule> rules = knowledgeBase.getRules();
        getDatabase().executeTransaction(realm -> {
            Rule rule = getDatabase().createObject(Rule.class);
            rule.setId(IdHelper.get().getGeneratedUniqueIdForRule(getDatabase()));
            rule.setDescription("Test #" + ruleId);
            rule.setActivated(false);
            rule.setDateAdded(currentRuleDate);
            rule.setConditions(conditionList);
            rule.setFactsCount(conditionList.size());
            rule.setResultFact(resultFact);
            rules.add(rule);
            knowledgeBase.setRules(rules);
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
