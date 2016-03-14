package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;

import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class RuleDatePresenter extends BasePresenter implements IRuleDatePresenter {
    private IRuleDateView ruleDateView;
    private Rule currentRule;

    public RuleDatePresenter(Context context, IRuleDateView ruleDateView, Rule currentRule) {
        super(context);
        this.ruleDateView = ruleDateView;
        this.currentRule = currentRule;
    }

    @Override
    public void onDateChangeClicked() {
        ruleDateView.showDatePickerDialog();
    }

    @Override
    public void saveDateForRule(int year, int monthOfYear, int dayOfMonth) {
        Realm database = getDatabase();
        database.executeTransaction(realm -> {
//            Rule ruleToChange = RulesDao.get().findRuleByIdAndKnowledgeBaseId(
//                    database, currentRule.getId(), currentRule.getKnowledgeBase().getTitle()
//            );
//            ruleToChange.setDateAdded(new Date(year, monthOfYear, dayOfMonth));
        });
    }

    public String getFormattedDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(currentRule.getDateAdded());
    }
}