package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.FactsDao;
import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.util.List;

public class RuleResultPresenter extends BasePresenter implements IRuleResultPresenter {
    public RuleResultPresenter(Context context) {
        super(context);
    }

    @Override
    public List<String> getPossibleResultFactsList() {
        return FactsDao.get().getAllSortedFactsInText(getDatabase());
    }

    @Override
    public String getResultFactDescription(int ruleId) {
        Rule currentRule = RulesDao.get().findRuleByUniqueId(getDatabase(), ruleId);
        return currentRule.getResultFact().getDescription();
    }
}
