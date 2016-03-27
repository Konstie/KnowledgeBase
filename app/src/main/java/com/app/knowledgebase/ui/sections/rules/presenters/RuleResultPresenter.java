package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.FactsDao;
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
}
