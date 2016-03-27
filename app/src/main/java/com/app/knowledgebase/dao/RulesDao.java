package com.app.knowledgebase.dao;

import com.app.knowledgebase.helpers.IdHelper;
import com.app.knowledgebase.models.Rule;

import io.realm.Realm;
import io.realm.RealmQuery;

public class RulesDao {
    private static RulesDao instance;

    private RulesDao() {}

    public static RulesDao get() {
        if (instance == null) {
            instance = new RulesDao();
        }
        return instance;
    }

    public void createNewRule(Realm database) {
        database.executeTransaction(realm -> {
            Rule rule = database.createObject(Rule.class);
            rule.setId(IdHelper.get().getGeneratedUniqueIdForRule(database));
        });
    }

    public Rule findRuleByUniqueId(Realm database, long uniqueId) {
        RealmQuery<Rule> query = database.where(Rule.class);
        query.equalTo("id", uniqueId);
        return query.findFirst();
    }

    public Rule getLastCreatedRule(Realm database) {
        long lastId = database.where(Rule.class).max("id").longValue();
        return findRuleByUniqueId(database, lastId);
    }
}
