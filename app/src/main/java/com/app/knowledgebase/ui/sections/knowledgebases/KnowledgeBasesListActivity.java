package com.app.knowledgebase.ui.sections.knowledgebases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.helpers.IntentHelper;
import com.app.knowledgebase.models.KnowledgeBase;
import com.app.knowledgebase.ui.sections.knowledgebases.adapters.KnowledgeBaseListAdapter;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.IConflictsListView;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.KnowledgeBasesListPresenter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class KnowledgeBasesListActivity extends AppCompatActivity implements IConflictsListView {
    private KnowledgeBaseListAdapter adapter;
    private KnowledgeBasesListPresenter presenter;

    @Bind(R.id.knowledge_bases_list_view) ListView knowledgeBasesListView;
    @Bind(R.id.btn_action_database_menu) FloatingActionsMenu buttonActionsMenu;
    @Bind(R.id.btn_open_facts) FloatingActionButton buttonOpenFacts;
    @Bind(R.id.btn_add_new_database) FloatingActionButton buttonAddNewDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bases_list);
        ButterKnife.bind(this);

        presenter = new KnowledgeBasesListPresenter(getBaseContext(), this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonAddNewDatabase.setTitle("Add new base");
        buttonAddNewDatabase.setOnClickListener(v -> presenter.onAddNewDatabase());

        buttonOpenFacts.setTitle("Open facts list");
        buttonOpenFacts.setOnClickListener(v -> IntentHelper.get().openFactsList(this));

        presenter.onCreate();
        presenter.obtainDatabasesList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        buttonActionsMenu.collapse();
    }

    @Override
    public void fillConflictsList(RealmResults<KnowledgeBase> knowledgeBases) {
        adapter = new KnowledgeBaseListAdapter(this, knowledgeBases, true);
        knowledgeBasesListView.setAdapter(adapter);
        knowledgeBasesListView.setOnItemClickListener((parent, view, position, id) -> {
            IntentHelper.get().openKnowledgeBaseDetails(KnowledgeBasesListActivity.this,
                    knowledgeBases.get(position).getId(), knowledgeBases.get(position).getTitle()
            );
        });
    }

    @Override
    public void showKnowledgeBaseMenu() {

    }

    @Override
    public void addNewBase() {
        IntentHelper.get().openNewKnowledgeBaseActivity(this);
    }
}
