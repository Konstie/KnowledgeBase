package com.app.knowledgebase.ui.sections.knowledgebases.presenters;

import com.app.knowledgebase.models.KnowledgeBase;

import io.realm.RealmResults;

public interface IConflictsListView {
    void fillConflictsList(RealmResults<KnowledgeBase> knowledgeBases);
    void addNewBase();
}
