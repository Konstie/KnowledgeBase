package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import android.content.Context;
import android.util.Log;

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
        Log.w("KnowledgeDBPresenter", "knowledge base id: " + currentBase.getId());
        Log.w("KnowledgeDBPresenter", "knowledge base title: " + currentBase.getTitle());
        Log.w("KnowledgeDBPresenter", "knowledge base strategies count: " + currentBase.getStrategies().size());
        Log.w("KnowledgeDBPresenter", "knowledge base start facts count: " + currentBase.getStartFacts().size());
        Log.w("KnowledgeDBPresenter", "currentBase rules count: " + currentBase.getRules().size());
        view.showKnowledgeBaseDetails(currentBase.getRules());
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
