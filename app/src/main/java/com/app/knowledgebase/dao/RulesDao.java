package com.app.knowledgebase.dao;

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

    public Rule findRuleByIdAndKnowledgeBaseId(Realm database, int ruleId, String knowledgeBaseName) {
        RealmQuery<Rule> query = database.where(Rule.class);
        query
                .equalTo("description", ruleId)
                .equalTo("knowledgeBase.title", knowledgeBaseName);
        return query.findFirst();
    }
}
