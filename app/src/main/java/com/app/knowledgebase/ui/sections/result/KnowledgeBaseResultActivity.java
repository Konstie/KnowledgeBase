package com.app.knowledgebase.ui.sections.result;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.app.knowledgebase.R;
import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.models.ResolveIteration;
import com.app.knowledgebase.ui.sections.abs.BaseActivity;
import com.app.knowledgebase.ui.sections.result.adapter.ResolveIterAdapter;
import com.app.knowledgebase.ui.sections.result.presenters.ConflictResolverLogicPresenter;
import com.app.knowledgebase.ui.sections.result.presenters.IKnowledgeBaseResultView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KnowledgeBaseResultActivity extends BaseActivity implements IKnowledgeBaseResultView {
    private int knowledgeBaseId;

    private ConflictResolverLogicPresenter presenter;
    private ResolveIterAdapter adapter;

    @Bind(R.id.list_resolve_iterations) ListView resolveIterationsListView;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            knowledgeBaseId = getIntent().getIntExtra(Constants.EXTRA_KNOWLEDGE_BASE_ID, -1);
        }

        setToolbar(toolbar, R.string.kbase_toolbar_title, true);

        presenter = new ConflictResolverLogicPresenter(this, this, knowledgeBaseId);
        presenter.initializeKnowledgeBaseRules();
    }

    @Override
    public void showResolveIterationsList(List<ResolveIteration> resolveIterations) {
        Log.w("Conflict", "Resolve iters null: {" + (resolveIterations == null) + "}");
        Log.w("Conflict", "Adapter null: {" + (adapter == null) + "}");
        Log.w("Conflict", "Presenter null: {" + (presenter == null) + "}");
        Log.w("Conflict", "ListView null: {" + (resolveIterationsListView == null) + "}");
        adapter = new ResolveIterAdapter(this, resolveIterations);
        resolveIterationsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoRulesWarning() {
        Toast.makeText(KnowledgeBaseResultActivity.this,
                "There are no rules in current base. Please, fill it with rules to proceed.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
