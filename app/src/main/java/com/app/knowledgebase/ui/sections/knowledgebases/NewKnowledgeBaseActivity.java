package com.app.knowledgebase.ui.sections.knowledgebases;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.app.knowledgebase.R;
import com.app.knowledgebase.ui.sections.abs.BaseActivity;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.INewKnowledgeBaseView;
import com.app.knowledgebase.ui.sections.knowledgebases.presenters.NewKnowledgeBasePresenter;
import com.app.knowledgebase.ui.views.MultiSelectionSpinner;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewKnowledgeBaseActivity extends BaseActivity implements INewKnowledgeBaseView {

    private NewKnowledgeBasePresenter presenter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edit_base_name) EditText newBaseNameEditText;
    @Bind(R.id.strategies_spinner) MultiSelectionSpinner strategiesSpinner;
    @Bind(R.id.btn_add_base) FloatingActionButton buttonSaveBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_base);
        ButterKnife.bind(this);
        presenter = new NewKnowledgeBasePresenter(this, this);

        setToolbar(toolbar, R.string.kbase_toolbar_title, true);

        presenter.onStrategiesInitialized();

        buttonSaveBase.setOnClickListener(v -> {
            presenter.onSaveNewKnowledgeBaseClicked(newBaseNameEditText.getText().toString(), presenter.getSelectedStrategies(strategiesSpinner.getSelectedStrings()));
            onBackPressed();
        });
    }

    @Override
    public void fillStrategiesSpinner(List<String> strategiesTitles) {
        strategiesSpinner.setItems(strategiesTitles);
        int[] selectedStrategies = presenter.getSelectedStrategiesIdsArray(strategiesSpinner.getSelectedIndices());
        strategiesSpinner.setSelection(selectedStrategies);
    }
}