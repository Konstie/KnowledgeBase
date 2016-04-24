package com.app.knowledgebase.ui.sections.rules.presenters;

import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.ui.sections.abs.presenter.IBasePresenter;

import java.util.Date;

import io.realm.RealmList;

public interface IAddRulePresenter extends IBasePresenter {
    void onSaveRuleClicked(int baseId, int ruleId, RealmList<Condition> conditionList, RealmList<Condition> consequentsList, Date currentRuleDate, boolean newRule);
}
