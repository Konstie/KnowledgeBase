package com.app.knowledgebase.ui.sections.rules.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.ui.sections.abs.BaseDialogFragment;
import com.app.knowledgebase.ui.sections.rules.presenters.EditConditionPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditConditionDialogFragment extends BaseDialogFragment {
    private static final String EXTRA_CONDITION_ID = "EXTRA_CONDITION_ID";
    private static final String EXTRA_RULE_ID = "EXTRA_RULE_ID";
    private static final String EXTRA_IS_FIRST = "EXTRA_IS_FIRST";
    private static final String EXTRA_RULE_TYPE = "EXTRA_RULE_TYPE";
    private static final String EXTRA_CONDITION_POS_IN_RULE = "EXTRA_CONDITION_POS_IN_RULE";

    private boolean newCondition = true;
    private boolean isFirst = false;
    private int ruleId;
    private long conditionId;
    private int positionInRule;
    private RuleDetailsType ruleDetailsType;
    private String newFactSelection;
    private String newConditionOperator;
    private Condition currentCondition;

    private EditConditionPresenter presenter;

    @Bind(R.id.edit_condition_operator) Spinner editConditionOperatorSpinner;
    @Bind(R.id.text_condition) AutoCompleteTextView textCondition;
    @Bind(R.id.if_label_text_view) TextView textIfClause;
    @Bind(R.id.btn_save) Button buttonSaveCondition;
    @Bind(R.id.btn_cancel) Button buttonCancel;

    public static EditConditionDialogFragment newInstance(RuleDetailsType ruleDetailsType, long conditionId, int ruleId, int positionInRule, boolean isFirst) {
        EditConditionDialogFragment dialog = new EditConditionDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CONDITION_ID, conditionId);
        args.putInt(EXTRA_RULE_ID, ruleId);
        args.putBoolean(EXTRA_IS_FIRST, isFirst);
        args.putSerializable(EXTRA_RULE_TYPE, ruleDetailsType);
        args.putLong(EXTRA_CONDITION_POS_IN_RULE, positionInRule);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new EditConditionPresenter(getContext(), ruleDetailsType);

        if (getArguments() != null) {
            ruleId = getArguments().getInt(EXTRA_RULE_ID);
            conditionId = getArguments().getLong(EXTRA_CONDITION_ID);
            isFirst = getArguments().getBoolean(EXTRA_IS_FIRST);
            ruleDetailsType = (RuleDetailsType) getArguments().getSerializable(EXTRA_RULE_TYPE);
            positionInRule = getArguments().getInt(EXTRA_CONDITION_POS_IN_RULE);
        }

        if (conditionId == -1) {
            newCondition = true;
        } else {
            currentCondition = ConditionsDao.get().findConditionById(presenter.getDatabase(), conditionId);
            newCondition = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_change_fact, container, false);
        ButterKnife.bind(this, rootView);

        List<String> allFacts = presenter.getAllFactsInText();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.item_facts_autocomplete, presenter.getAllFactsInText()
        );
        textCondition.setAdapter(adapter);

        newFactSelection = textCondition.getText().toString();
        textCondition.setOnItemClickListener((parent, view, position, id) -> {
            newFactSelection = (String) parent.getItemAtPosition(position);
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, presenter.getConditionOperators());
        editConditionOperatorSpinner.setAdapter(spinnerAdapter);
        if (!newCondition) {
            textCondition.setListSelection(allFacts.indexOf(currentCondition.getConditionItem().getConditionFact().getDescription()));
            newConditionOperator = currentCondition.getConditionItem().getConditionOperator();
            editConditionOperatorSpinner.setSelection(presenter.getConditionOperators().indexOf(newConditionOperator));
        } else {
            textCondition.setListSelection(0);
            editConditionOperatorSpinner.setSelection(0);
            newConditionOperator = editConditionOperatorSpinner.getSelectedItem().toString();
        }

        editConditionOperatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newConditionOperator = presenter.getConditionOperators().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonSaveCondition.setOnClickListener(v -> {
            if (newCondition) {
                presenter.onSaveNewConditionClicked(ruleId, -1, positionInRule, newConditionOperator, textCondition.getText().toString());
            } else {
                presenter.onUpdateNewConditionClicked(conditionId, ruleId, positionInRule, newConditionOperator, textCondition.getText().toString());
            }
            EditConditionDialogFragment.this.dismissAllowingStateLoss();
        });

        if (isFirst) {
            textIfClause.setVisibility(View.VISIBLE);
            editConditionOperatorSpinner.setVisibility(View.INVISIBLE);
        } else {
            textIfClause.setVisibility(View.GONE);
            editConditionOperatorSpinner.setVisibility(View.VISIBLE);
        }

        buttonCancel.setOnClickListener(v -> dismissDialog());

        return rootView;
    }
}
