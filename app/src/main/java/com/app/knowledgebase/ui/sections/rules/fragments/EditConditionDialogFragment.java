package com.app.knowledgebase.ui.sections.rules.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.app.knowledgebase.R;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.ui.sections.abs.BaseDialogFragment;
import com.app.knowledgebase.ui.sections.rules.presenters.EditConditionPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditConditionDialogFragment extends BaseDialogFragment {
    private static final String EXTRA_CONDITION_TITLE = "EXTRA_CONDITION_TITLE";

    private int conditionPosInBase;
    private Fact factForCondition;
    private String currentKnowledgeBaseTitle;
    private String conditionTitle = "";
    private String newFactSelection = "";

    private EditConditionPresenter presenter;

    @Bind(R.id.text_condition) AutoCompleteTextView textCondition;
    @Bind(R.id.btn_save) Button buttonSaveCondition;
    @Bind(R.id.btn_cancel) Button buttonCancel;

    public EditConditionDialogFragment newInstance(String conditionTitle) {
        EditConditionDialogFragment dialog = new EditConditionDialogFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_CONDITION_TITLE, conditionTitle);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new EditConditionPresenter(getContext());

        if (getArguments() != null) {
            conditionTitle = getArguments().getString(EXTRA_CONDITION_TITLE);
            factForCondition = presenter.getFactByTitle(conditionTitle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_change_fact, container, false);
        ButterKnife.bind(this, rootView);

        List<String> allFacts = presenter.getAllFactsInText();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, presenter.getAllFactsInText()
        );
        textCondition.setAdapter(adapter);
        textCondition.setListSelection(allFacts.indexOf(conditionTitle));
        textCondition.setOnItemClickListener((parent, view, position, id) -> {
            newFactSelection = (String) parent.getItemAtPosition(position);
        });

        buttonSaveCondition.setOnClickListener(v -> {
            presenter.onSaveConditionClicked(currentKnowledgeBaseTitle, newFactSelection, conditionPosInBase);
        });

        buttonCancel.setOnClickListener(v -> dismissDialog());

        return rootView;
    }
}
