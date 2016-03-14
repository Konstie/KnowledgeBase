package com.app.knowledgebase.dao;

import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Rule;

import io.realm.Realm;
import io.realm.RealmQuery;

public class KnowledgeBaseDao {
    private static KnowledgeBaseDao instance;

    private KnowledgeBaseDao() {}

    public static KnowledgeBaseDao get() {
        if (instance == null) {
            instance = new KnowledgeBaseDao();
        }
        return instance;
    }

    public KnowledgeBase findKnowledgeBaseByTitle(Realm database, String baseTitle) {
        RealmQuery<KnowledgeBase> query = database.where(KnowledgeBase.class);
        query.equalTo("title", baseTitle);
        return query.findFirst();
    }
}
