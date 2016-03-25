package com.app.knowledgebase.helpers;

import android.content.Context;
import android.content.Intent;

import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.ui.sections.knowledgebases.KnowledgeBaseActivity;
import com.app.knowledgebase.ui.sections.knowledgebases.KnowledgeBasesListActivity;
import com.app.knowledgebase.ui.sections.knowledgebases.NewKnowledgeBaseActivity;
import com.app.knowledgebase.ui.sections.rules.RulesListActivity;

public class IntentHelper {
    private static IntentHelper intentHelper;

    private IntentHelper() {}

    public static IntentHelper get() {
        if (intentHelper == null) {
            intentHelper = new IntentHelper();
        }
        return intentHelper;
    }

    public void openKnowledgeBaseDetails(Context context, String knowledgeBaseName) {
        Intent knowledgeBaseDetailsIntent = new Intent(context, KnowledgeBaseActivity.class);
        knowledgeBaseDetailsIntent.putExtra(Constants.EXTRA_KNOWLEDGE_BASE_NAME, knowledgeBaseName);
        context.startActivity(knowledgeBaseDetailsIntent);
    }

    public void openNewKnowledgeBaseActivity(Context context) {
        context.startActivity(new Intent(context, NewKnowledgeBaseActivity.class));
    }

    public void openRuleDetails(Context context, String ruleId) {
        Intent ruleDetailsIntent = new Intent(context, RulesListActivity.class);
        ruleDetailsIntent.putExtra(Constants.EXTRA_RULE_ID, ruleId);
        context.startActivity(ruleDetailsIntent);
    }

    public void openConflictResultActivity(Context context, String knowledgeBaseName) {

    }
}