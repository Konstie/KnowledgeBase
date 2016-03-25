package com.app.knowledgebase.ui.sections.knowledgebases;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.helpers.IntentHelper;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.ui.sections.knowledgebases.adapters.KnowledgeBaseListAdapter;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.IConflictsListView;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.KnowledgeBasesListPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class KnowledgeBasesListActivity extends AppCompatActivity implements IConflictsListView {

    private KnowledgeBasesListPresenter presenter;
    private KnowledgeBaseListAdapter adapter;

    @Bind(R.id.knowledge_bases_list_view) ListView knowledgeBasesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bases_list);
        ButterKnife.bind(this);

        presenter = new KnowledgeBasesListPresenter(getBaseContext(), this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter.obtainDatabasesList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> presenter.onAddNewDatabase());
    }

    @Override
    public void fillConflictsList(RealmResults<KnowledgeBase> knowledgeBases) {
        adapter = new KnowledgeBaseListAdapter(this, knowledgeBases, true);
        knowledgeBasesListView.setAdapter(adapter);
    }

    @Override
    public void addNewBase() {
        IntentHelper.get().openNewKnowledgeBaseActivity(this);
    }
}
