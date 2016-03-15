package com.app.knowledgebase.dao;

import com.app.knowledgebase.models.Condition;

import io.realm.Realm;
import io.realm.RealmQuery;

public class ConditionsDao {
    private static ConditionsDao instance;

    private ConditionsDao() {}

    public static ConditionsDao get() {
        if (instance == null) {
            instance = new ConditionsDao();
        }
        return instance;
    }

    public Condition findConditionByPositionInKnowledgeBase(Realm database, String knowledgeBaseName, int position) {
        RealmQuery<Condition> query = database.where(Condition.class);
        query
                .equalTo("knowledgeBase.title", knowledgeBaseName)
                .equalTo("positionInRule", position);
        return query.findFirstAsync();
    }
}
