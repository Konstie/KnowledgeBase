package com.app.knowledgebase.dao;

import com.app.knowledgebase.helpers.IdHelper;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Rule;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ConditionsDao {
    private static ConditionsDao instance;

    private ConditionsDao() {}

    public static ConditionsDao get() {
        if (instance == null) {
            instance = new ConditionsDao();
        }
        return instance;
    }

    public void createNewCondition(Realm database) {
        database.executeTransaction(realm -> {
            Condition condition = database.createObject(Condition.class);
            condition.setId(IdHelper.get().getGeneratedUniqueIdForCondition(database));
        });
    }

    public Condition findConditionById(Realm database, long id) {
        RealmQuery<Condition> query = database.where(Condition.class);
        query.equalTo("id", id);
        return query.findFirst();
    }

    public Condition findConditionByPositionInKnowledgeBase(Realm database, String knowledgeBaseName, int position) {
        RealmQuery<Condition> query = database.where(Condition.class);
        query
                .equalTo("knowledgeBase.title", knowledgeBaseName)
                .equalTo("positionInRule", position);
        return query.findFirstAsync();
    }

    public Condition getLastCreatedCondition(Realm database) {
        long lastId = database.where(Rule.class).max("id").longValue();
        return findConditionById(database, lastId);
    }
}
