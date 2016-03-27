package com.app.knowledgebase.ui.sections.rules.presenters;

import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.ui.sections.abs.presenter.IBasePresenter;

import java.util.Date;

import io.realm.RealmList;

public interface IAddRulePresenter extends IBasePresenter {
    void onConditionsInitialized(int ruleId);
    void onAddConditionClicked();
    void onEditConditionClicked(int position);
    void onSaveRuleClicked(int baseId, long ruleId, RealmList<Condition> conditionList, Fact resultFact, Date currentRuleDate);
}
