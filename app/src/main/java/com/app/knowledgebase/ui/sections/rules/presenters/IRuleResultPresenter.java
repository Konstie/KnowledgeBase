package com.app.knowledgebase.ui.sections.rules.presenters;

import com.app.knowledgebase.ui.sections.abs.presenter.IBasePresenter;

import java.util.List;

public interface IRuleResultPresenter extends IBasePresenter {
    List<String> getPossibleResultFactsList();
    String getResultFactDescription(int ruleId);
}