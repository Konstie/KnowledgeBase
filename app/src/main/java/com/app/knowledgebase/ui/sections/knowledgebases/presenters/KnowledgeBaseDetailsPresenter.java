package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.KnowledgeBaseDao;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

public class KnowledgeBaseDetailsPresenter extends BasePresenter implements IKnowledgeBaseDetailsPresenter {
    private IKnowledgeBaseDetailsView view;

    public KnowledgeBaseDetailsPresenter(Context context, IKnowledgeBaseDetailsView view) {
        super(context);
        this.view = view;
    }

    @Override
    public void onRulesListFilled(int knowledgeBaseId) {
        KnowledgeBase currentBase = KnowledgeBaseDao.get().findKnowledgeBaseById(getDatabase(), knowledgeBaseId);
        view.showKnowledgeBaseDetails(currentBase.getRules().where().findAll());
    }

    @Override
    public void getKnowledgeBaseId() {
        view.retrieveKnowledgeBaseId();
    }

    @Override
    public void openRuleDetails(int ruleId) {
        view.openRuleDetails(ruleId);
    }
}
