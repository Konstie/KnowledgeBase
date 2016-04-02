package com.app.knowledgebase.helpers;

import android.content.Context;
import android.content.Intent;

import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.ui.sections.facts.FactsListActivity;
import com.app.knowledgebase.ui.sections.knowledgebases.KnowledgeBaseDetailsActivity;
import com.app.knowledgebase.ui.sections.knowledgebases.NewKnowledgeBaseActivity;
import com.app.knowledgebase.ui.sections.result.KnowledgeBaseResultActivity;
import com.app.knowledgebase.ui.sections.rules.RuleDetailsActivity;

public class IntentHelper {
    private static IntentHelper intentHelper;

    private IntentHelper() {}

    public static IntentHelper get() {
        if (intentHelper == null) {
            intentHelper = new IntentHelper();
        }
        return intentHelper;
    }

    public void openKnowledgeBaseDetails(Context context, int knowledgeBaseId, String knowledgeBaseName) {
        Intent knowledgeBaseDetailsIntent = new Intent(context, KnowledgeBaseDetailsActivity.class);
        knowledgeBaseDetailsIntent.putExtra(Constants.EXTRA_KNOWLEDGE_BASE_ID, knowledgeBaseId);
        knowledgeBaseDetailsIntent.putExtra(Constants.EXTRA_KNOWLEDGE_BASE_TITLE, knowledgeBaseName);
        context.startActivity(knowledgeBaseDetailsIntent);
    }

    public void openNewKnowledgeBaseActivity(Context context) {
        context.startActivity(new Intent(context, NewKnowledgeBaseActivity.class));
    }

    public void createNewRule(Context context, int baseId) {
        Intent ruleDetailsIntent = new Intent(context, RuleDetailsActivity.class);
        ruleDetailsIntent.putExtra(Constants.EXTRA_KNOWLEDGE_BASE_ID, baseId);
        context.startActivity(ruleDetailsIntent);
    }

    public void openRuleDetails(Context context, int baseId, int ruleId) {
        Intent ruleDetailsIntent = new Intent(context, RuleDetailsActivity.class);
        ruleDetailsIntent.putExtra(Constants.EXTRA_KNOWLEDGE_BASE_ID, baseId);
        ruleDetailsIntent.putExtra(Constants.EXTRA_RULE_ID, ruleId);
        context.startActivity(ruleDetailsIntent);
    }

    public void openFactsList(Context context) {
        context.startActivity(new Intent(context, FactsListActivity.class));
    }

    public void openKnowledgeBaseResultActivity(Context context, int baseId) {
        Intent baseResultsIntent = new Intent(context, KnowledgeBaseResultActivity.class);
        baseResultsIntent.putExtra(Constants.EXTRA_KNOWLEDGE_BASE_ID, baseId);
        context.startActivity(baseResultsIntent);
    }
}