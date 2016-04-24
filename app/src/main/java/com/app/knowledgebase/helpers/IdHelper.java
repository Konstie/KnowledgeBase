package com.app.knowledgebase.helpers;

import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.ConditionPart;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.models.Rule;

import io.realm.Realm;

public class IdHelper {
    private static final String KEY_RULE = "rule";

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

    public int getGeneratedUniqueIdForFact(Realm database) {
        return (database.where(Fact.class).findAll().size() > 0)
                ? database.where(Fact.class).max("id").intValue() + 1 : 0;
    }

    public int getGeneratedUniqueIdForKnowledgeBase(Realm database) {
        return (database.where(KnowledgeBase.class).findAll().size() > 0)
                ? database.where(KnowledgeBase.class).max("id").intValue() + 1 : 0;
    }

    public int getGeneratedUniqueIdForRule(Realm database) {
        return (database.where(Rule.class).findAll().size() > 0)
                ? database.where(Rule.class).max("id").intValue() + 1 : 0;
    }

    public int getGeneratedUniqueIdForCondition(Realm database) {
        return (database.where(Condition.class).findAll().size() > 0)
                ? database.where(Condition.class).max("id").intValue() + 1 : 0;
    }

    public int getGeneratedUniqueIdForConditionPart(Realm database) {
        return (database.where(ConditionPart.class).findAll().size() > 0)
                ? database.where(ConditionPart.class).max("id").intValue() + 1 : 0;
    }
}
