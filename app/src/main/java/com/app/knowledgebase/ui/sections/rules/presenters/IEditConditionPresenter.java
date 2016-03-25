package com.app.knowledgebase.ui.sections.rules.presenters;

import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.ui.sections.abs.presenter.IBasePresenter;

public interface IEditConditionPresenter extends IBasePresenter {
    Fact getFactByTitle(String description);
    void onSaveNewConditionClicked(int positionInRule, String conditionId, String newOperator, String newFact);
}
