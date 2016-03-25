package com.app.knowledgebase.ui.sections.rules.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.app.knowledgebase.R;
import com.app.knowledgebase.ui.sections.abs.BaseDialogFragment;
import com.app.knowledgebase.ui.sections.rules.presenters.ConditionOperators;
import com.app.knowledgebase.ui.sections.rules.presenters.EditConditionPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditConditionDialogFragment extends BaseDialogFragment {
    private static final String EXTRA_CONDITION_OPERATOR = "EXTRA_CONDITION_OPERATOR";
    private static final String EXTRA_CONDITION_FACT = "EXTRA_CONDITION_FACT";
    private static final String EXTRA_CONDITION_UNIQUE_ID = "EXTRA_CONDITION_UNIQUE_ID";
    private static final String EXTRA_CONDITION_POS_IN_RULE = "EXTRA_CONDITION_POS_IN_RULE";

    private int conditionPosInRule;
    private String conditionUniqueId = "";
    private String newConditionOperator = ConditionOperators.NONE;
    private String newFactSelection = "";

    private EditConditionPresenter presenter;

    @Bind(R.id.edit_condition_operator) Spinner editConditionOperatorSpinner;
    @Bind(R.id.text_condition) AutoCompleteTextView textCondition;
    @Bind(R.id.btn_save) Button buttonSaveCondition;
    @Bind(R.id.btn_cancel) Button buttonCancel;

    public static EditConditionDialogFragment newInstance(String uniqueId, int positionInRule, String conditionOperator, String conditionTitle) {
        EditConditionDialogFragment dialog = new EditConditionDialogFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_CONDITION_UNIQUE_ID, uniqueId);
        args.putInt(EXTRA_CONDITION_POS_IN_RULE, positionInRule);
        args.putString(EXTRA_CONDITION_FACT, conditionTitle);
        args.putString(EXTRA_CONDITION_OPERATOR, conditionOperator);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new EditConditionPresenter(getContext());

        if (getArguments() != null) {
            conditionPosInRule = getArguments().getInt(EXTRA_CONDITION_POS_IN_RULE);
            conditionUniqueId = getArguments().getString(EXTRA_CONDITION_UNIQUE_ID);

            newFactSelection = getArguments().getString(EXTRA_CONDITION_FACT);
            newConditionOperator = getArguments().getString(EXTRA_CONDITION_OPERATOR);
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
        textCondition.setListSelection(allFacts.indexOf(conditionUniqueId));
        newFactSelection = textCondition.getText().toString();
        textCondition.setOnItemClickListener((parent, view, position, id) -> {
            newFactSelection = (String) parent.getItemAtPosition(position);
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, presenter.getConditionOperators());
        editConditionOperatorSpinner.setAdapter(spinnerAdapter);
        editConditionOperatorSpinner.setSelection(presenter.getConditionOperators().indexOf(newConditionOperator));
        editConditionOperatorSpinner.setOnItemClickListener((parent, view, position, id) ->
                newConditionOperator = presenter.getConditionOperators().get(position)
        );

        buttonSaveCondition.setOnClickListener(v -> {
            presenter.onSaveNewConditionClicked(conditionPosInRule, conditionUniqueId, newConditionOperator, newFactSelection);
        });

        buttonCancel.setOnClickListener(v -> dismissDialog());

        return rootView;
    }
}
