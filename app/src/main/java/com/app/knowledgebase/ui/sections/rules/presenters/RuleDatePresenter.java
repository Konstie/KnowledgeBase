package com.app.knowledgebase.ui.sections.rules.presenters;

import android.content.Context;
import android.util.Log;

import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.presenter.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class RuleDatePresenter extends BasePresenter implements IRuleDatePresenter {
    private IRuleDateView ruleDateView;
    private Date ruleDate;
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

    public void setInitialDateForRule() {
        ruleDate = (currentRule == null) ? Calendar.getInstance().getTime() : currentRule.getDateAdded();
    }

    @Override
    public void saveNewDateForRule(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, monthOfYear, dayOfMonth);
        ruleDate = calendar.getTime();
    }

    public Date getRuleDate() {
        return ruleDate;
    }

    public String getFormattedDate() {
        Date currentDate = (ruleDate == null) ? Calendar.getInstance().getTime() : ruleDate;
        return new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(currentDate);
    }
}