package com.app.knowledgebase.helpers;

import com.app.knowledgebase.constants.Constants;

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

    public String getNewConditionId(String ruleId, int positionInRule) {
        return ruleId + Constants.DIVIDER + KEY_CONDITION + positionInRule;
    }

    public int getGeneratedUniqueId(Realm database, Class<? extends RealmObject> itemType) {
        return (database.where(itemType).findAll().size() > 0)
                ? database.where(itemType).max("id").intValue() + 1 : 0;
    }
}
