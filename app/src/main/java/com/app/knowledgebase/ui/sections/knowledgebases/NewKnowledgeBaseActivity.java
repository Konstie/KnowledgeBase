package com.app.knowledgebase.ui.sections.knowledgebases;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.app.knowledgebase.R;
import com.app.knowledgebase.ui.sections.abs.BaseActivity;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.INewKnowledgeBaseView;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.NewKnowledgeBasePresenter;
import com.app.knowledgebase.ui.views.MultiSelectionSpinner;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewKnowledgeBaseActivity extends BaseActivity
        implements INewKnowledgeBaseView {

    private boolean titleEmpty = true;

    private NewKnowledgeBasePresenter presenter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edit_base_name) EditText newBaseNameEditText;
    @Bind(R.id.strategies_spinner) MultiSelectionSpinner strategiesSpinner;
    @Bind(R.id.start_facts_spinner) MultiSelectionSpinner startFactsSpinner;
    @Bind(R.id.btn_add_base) FloatingActionButton buttonSaveBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_base);
        ButterKnife.bind(this);
        presenter = new NewKnowledgeBasePresenter(this, this);

        setToolbar(toolbar, R.string.kbase_toolbar_title, true);

        presenter.onStrategiesInitialized();
        presenter.onFactsInitialized();

        buttonSaveBase.setEnabled(false);
        newBaseNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence title, int start, int before, int count) {
               titleEmpty = title.toString().isEmpty();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonSaveBase.setOnClickListener(v -> {
            presenter.onSaveNewKnowledgeBaseClicked(
                    newBaseNameEditText.getText().toString(),
                    presenter.getSelectedStrategies(),
                    presenter.getStartFacts()
            );
            finish();
        });
    }

    @Override
    public void fillFactsSpinner(List<String> factsTitles) {
        startFactsSpinner.setItems(factsTitles, R.string.start_facts_none, selected -> {
            if (selected.length > 0 && !titleEmpty) {
                buttonSaveBase.setEnabled(true);
                presenter.setCheckedStartFacts(selected);
            } else {
                buttonSaveBase.setEnabled(false);
            }
        });
    }

    @Override
    public void fillStrategiesSpinner(List<String> strategiesTitles) {
        strategiesSpinner.setItems(strategiesTitles, R.string.strategies_none, selected -> {
            if (selected.length > 0 && !titleEmpty) {
                buttonSaveBase.setEnabled(true);
                presenter.setCheckedStrategies(selected);
            } else {
                buttonSaveBase.setEnabled(false);
            }
        });
    }
}