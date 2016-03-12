package com.app.knowledgebase.ui.sections.facts.presenter;

import com.app.knowledgebase.models.Fact;

import io.realm.RealmResults;

public interface IFactsView {
    void addItems(RealmResults<Fact> facts);
    void showEditFactDialog(int factPosition);
}
