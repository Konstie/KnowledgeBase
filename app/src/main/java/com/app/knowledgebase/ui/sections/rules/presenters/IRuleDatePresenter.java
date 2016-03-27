package com.app.knowledgebase.ui.sections.rules.presenters;

public interface IRuleDatePresenter {
    void onDateChangeClicked();
    void saveNewDateForRule(int year, int monthOfYear, int dayOfMonth);
}
