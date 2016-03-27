package com.app.knowledgebase.ui.sections.rules.presenters;

import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.ui.sections.abs.presenter.IBasePresenter;

public interface IEditConditionPresenter extends IBasePresenter {
    Fact getFactByTitle(String description);
    Condition getLastCreatedCondition();
    void onSaveNewConditionClicked(int ruleId, int positionInRule, String newOperator, String newFact);
    void onUpdateNewConditionClicked(long conditionId, int ruleId, int positionInRule, String newOperator, String newFact);
}
