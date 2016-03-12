package com.app.knowledgebase.ui.sections.facts;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.knowledgebase.R;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.ui.sections.abs.BaseActivity;
import com.app.knowledgebase.ui.sections.facts.adapters.FactsListAdapter;
import com.app.knowledgebase.ui.sections.facts.presenter.FactsListPresenter;
import com.app.knowledgebase.ui.sections.facts.presenter.IFactsView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class FactsListActivity extends BaseActivity implements IFactsView {
    private FactsListPresenter presenter;
    private FactsListAdapter adapter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.list_view_facts) ListView listViewFacts;
    @Bind(R.id.edit_new_fact) EditText editNewFact;
    @Bind(R.id.btn_add_new_fact) ImageView buttonAddNewFact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts_list);
        ButterKnife.bind(this);
        setToolbar(toolbar, R.string.facts_category, true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter = new FactsListPresenter(this, this);

        presenter.initializeDatabase();
        presenter.showAllFacts();

        buttonAddNewFact.setOnClickListener(l -> {
            presenter.onAddFactClicked(editNewFact.getText().toString());
            adapter.notifyDataSetChanged();
            editNewFact.setText("");
        });

        listViewFacts.setOnItemClickListener((parent, view, position, id) -> {
            presenter.onFactItemClicked(position);
        });
    }

    @Override
    public void addItems(RealmResults<Fact> facts) {
        adapter = new FactsListAdapter(this, presenter.getAllFacts(), true);
        listViewFacts.setAdapter(adapter);
    }

    @Override
    public void showEditFactDialog(int position) {
        Fact currentFact = adapter.getItem(position);

        new MaterialDialog.Builder(this)
                .title(R.string.facts_edit_fact)
                .content(R.string.facts_description)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(getString(R.string.facts_hint), currentFact.getDescription(), (dialog, input) -> {})
                .positiveText(R.string.action_save)
                .negativeText(R.string.action_remove)
                .neutralText(R.string.action_cancel)
                .onNegative((dialog, which) ->
                    presenter.onRemoveFactClicked(currentFact)
                )
                .onPositive((dialog, which) -> {
                    String newDescription = (dialog.getInputEditText() != null) ? dialog.getInputEditText().getText().toString() : "";
                    presenter.onSaveFactClicked(currentFact, newDescription);
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}