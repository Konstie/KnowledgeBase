package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;

import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

public class AddRulePresenter extends BasePresenter implements IAddRulePresenter {
    private IAddRuleView addRuleView;

    public AddRulePresenter(Context context, IAddRuleView addRuleView) {
        super(context);
        this.addRuleView = addRuleView;
    }

    @Override
    public void onAddConditionClicked() {
        addRuleView.addNewCondition();
    }

    @Override
    public void onAddRuleClicked() {
        addRuleView.addNewRule();
    }
}
