package com.app.knowledgebase.ui.sections.result;

import android.os.Bundle;

import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.ui.sections.abs.BaseActivity;

public class KnowledgeBaseResultActivity extends BaseActivity {
    private ConflictResolverLogicPresenter presenter;

    private int knowledgeBaseId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            knowledgeBaseId = getIntent().getIntExtra(Constants.EXTRA_KNOWLEDGE_BASE_ID, -1);
        }

        presenter = new ConflictResolverLogicPresenter(this, knowledgeBaseId);
        presenter.initializeKnowledgeBaseRules();
    }
}
