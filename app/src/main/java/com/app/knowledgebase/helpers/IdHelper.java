package com.app.knowledgebase.helpers;

import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.IteratedFact;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Strategy;

import io.realm.Realm;
import io.realm.RealmObject;

public class IdHelper {
    private static final String KEY_RULE = "rule";
    private static final String KEY_CONDITION = "cond";

    private static IdHelper idHelper;

    private IdHelper() {}

    public static IdHelper get() {
        if (idHelper == null) {
            idHelper = new IdHelper();
        }
        return idHelper;
    }

    public String getKnowledgeBaseItemId(String sourceId, int level) {
        int firstIndex = sourceId.indexOf(Constants.DIVIDER);
        return sourceId.substring(0, sourceId.indexOf(Constants.DIVIDER, firstIndex + level));
    }

    public String getNewRuleId(String knowledgeBaseName, int position) {
        return knowledgeBaseName + Constants.DIVIDER + KEY_RULE + position;
    }

    public int getGeneratedUniqueIdForStrategy(Realm database) {
        return (database.where(Strategy.class).findAll().size() > 0)
                ? database.where(Strategy.class).max("uniqueId").intValue() + 1 : 0;
    }

    public int getGeneratedUniqueIdForIteratedFact(Realm database) {
        return (database.where(IteratedFact.class).findAll().size() > 0)
                ? database.where(IteratedFact.class).max("uniqueId").intValue() + 1 : 0;
    }

    public int getGeneratedUniqueIdForFact(Realm database) {
        return (database.where(Fact.class).findAll().size() > 0)
                ? database.where(Fact.class).max("id").intValue() + 1 : 0;
    }

    public int getGeneratedUniqueIdForKnowledgeBase(Realm database) {
        return (database.where(KnowledgeBase.class).findAll().size() > 0)
                ? database.where(KnowledgeBase.class).max("id").intValue() + 1 : 0;
    }
}
