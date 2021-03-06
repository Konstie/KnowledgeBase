package com.app.knowledgebase.ui.sections.knowledgebases;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.helpers.IntentHelper;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.BaseActivity;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.IKnowledgeBaseDetailsView;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.KnowledgeBaseDetailsPresenter;
import com.app.knowledgebase.ui.sections.rules.adapters.RulesListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class KnowledgeBaseDetailsActivity extends BaseActivity implements IKnowledgeBaseDetailsView {
    private boolean rulesListFilled = false;
    private int knowledgeBaseId;
    private String knowledgeBaseName;

    private RulesListAdapter adapter;
    private KnowledgeBaseDetailsPresenter presenter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.rules_list_view) GridView rulesListView;
    @Bind(R.id.btn_add_new_rule) FloatingActionButton buttonAddRule;
    @Bind(R.id.text_no_rules) TextView noRulesTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_details);
        ButterKnife.bind(this);

        presenter = new KnowledgeBaseDetailsPresenter(this, this);
        presenter.getKnowledgeBaseId();
        presenter.onRulesListFilled(knowledgeBaseId);

        Log.w("KnowledgeDBActivity", "knowledgeBaseId: " + knowledgeBaseId);

        buttonAddRule.setOnClickListener(v -> {
            IntentHelper.get().createNewRule(this, knowledgeBaseId);
        });
    }

    @Override
    public void showKnowledgeBaseDetails(RealmList<Rule> rulesList) {
        Log.w("KnowledgeDBActivity", "Rules list size: " + rulesList.size());
        adapter = new RulesListAdapter(this, rulesList);
        rulesListView.setAdapter(adapter);
        if (rulesList.size() > 0) {
            rulesListFilled = true;
            noRulesTextView.setVisibility(View.INVISIBLE);
        } else {
            rulesListFilled = false;
            noRulesTextView.setVisibility(View.VISIBLE);
        }
        rulesListView.setOnItemClickListener((parent, view, position, id) -> {
            presenter.openRuleDetails(rulesList.get(position).getId());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_run:
                runKnowledgeBase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            if (rulesListFilled) {
                noRulesTextView.setVisibility(View.INVISIBLE);
            } else {
                noRulesTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void runKnowledgeBase() {
        IntentHelper.get().openKnowledgeBaseResultActivity(this, knowledgeBaseId);
    }

    @Override
    public void retrieveKnowledgeBaseId() {
        if (getIntent() != null) {
            knowledgeBaseId = getIntent().getIntExtra(Constants.EXTRA_KNOWLEDGE_BASE_ID, -1);
            knowledgeBaseName = getIntent().getStringExtra(Constants.EXTRA_KNOWLEDGE_BASE_TITLE);
        }

        setToolbar(toolbar, (knowledgeBaseName == null || knowledgeBaseName.isEmpty())
                ? getString(R.string.kbase_toolbar_title) : knowledgeBaseName, true);

        if (knowledgeBaseId != -1) {
            presenter.onRulesListFilled(knowledgeBaseId);
        }
    }

    @Override
    public void openRuleDetails(int ruleId) {
        IntentHelper.get().openRuleDetails(this, knowledgeBaseId, ruleId);
    }
}
