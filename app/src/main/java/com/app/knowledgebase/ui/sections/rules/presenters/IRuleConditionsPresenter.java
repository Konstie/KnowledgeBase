package com.app.knowledgebase.ui.sections.rules.presenters;

import com.app.knowledgebase.ui.sections.abs.presenter.IBasePresenter;
import com.app.knowledgebase.ui.sections.rules.fragments.RuleDetailsType;

public interface IRuleConditionsPresenter extends IBasePresenter {
    void onConditionsInitialized(RuleDetailsType ruleDetailsType);
    void onAddConditionClicked();
    void onEditConditionClicked(int position);
}
