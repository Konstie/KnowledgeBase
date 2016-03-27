package com.app.knowledgebase.dao;

import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Rule;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class KnowledgeBaseDao {
    private static KnowledgeBaseDao instance;

    private KnowledgeBaseDao() {}

    public static KnowledgeBaseDao get() {
        if (instance == null) {
            instance = new KnowledgeBaseDao();
        }
        return instance;
    }

    public RealmResults<KnowledgeBase> findAllKnowledgeBases(Realm database) {
        return database.where(KnowledgeBase.class).findAllAsync();
    }

    public KnowledgeBase findKnowledgeBaseById(Realm database, int id) {
        RealmQuery<KnowledgeBase> query = database.where(KnowledgeBase.class);
        query.equalTo("id", id);
        return query.findFirst();
    }

    public KnowledgeBase findKnowledgeBaseByTitle(Realm database, String baseTitle) {
        RealmQuery<KnowledgeBase> query = database.where(KnowledgeBase.class);
        query.equalTo("title", baseTitle);
        return query.findFirst();
    }
}
