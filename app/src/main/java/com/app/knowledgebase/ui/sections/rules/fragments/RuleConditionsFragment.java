package com.app.knowledgebase.ui.sections.rules.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.events.RuleDetailsReadyEvent;
import com.app.knowledgebase.events.SaveRuleRequestedEvent;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.ui.sections.rules.adapters.ConditionsAdapter;
import com.app.knowledgebase.ui.sections.rules.presenters.IConditionsListView;
import com.app.knowledgebase.ui.sections.rules.presenters.RuleConditionsPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RuleConditionsFragment extends Fragment implements IConditionsListView {
    private static final String KEY_RULE = "currentRule";
    private static final String KEY_TYPE = "ruleDetailsType";

    private int currentRuleId;
    private RuleDetailsType ruleDetailsType;

    private RuleConditionsPresenter presenter;
    private ConditionsAdapter conditionsAdapter;

    @Bind(R.id.conditions_list_view)
    ListView conditionsListView;

    public static RuleConditionsFragment newInstance(RuleDetailsType ruleDetailsType, int currentRuleId) {
        RuleConditionsFragment fragment = new RuleConditionsFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_TYPE, ruleDetailsType);
        args.putInt(KEY_RULE, currentRuleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentRuleId = getArguments().getInt(KEY_RULE);
            ruleDetailsType = (RuleDetailsType) getArguments().getSerializable(KEY_TYPE);
            Log.w("RuleDateFragment", "RuleId: " + currentRuleId);
        }

        presenter = new RuleConditionsPresenter(getActivity(), this, currentRuleId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rule_conditions, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        presenter.onConditionsInitialized(ruleDetailsType);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (conditionsAdapter != null) {
            conditionsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setupConditionsForRule(RealmResults<Condition> conditions) {
        View listFooter = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.footer_add_condition, null, false);
        ImageButton buttonAddCondition = (ImageButton) listFooter.findViewById(R.id.btn_add_condition);
        buttonAddCondition.setOnClickListener(v -> presenter.onAddConditionClicked());
        conditionsListView.addFooterView(listFooter);

        conditionsAdapter = new ConditionsAdapter(getActivity(), conditions, true);
        conditionsListView.setAdapter(conditionsAdapter);
        conditionsListView.setOnItemClickListener((parent, view, position, id) -> {
            presenter.onEditConditionClicked(position);
        });
    }

    @Override
    public void addNewCondition() {
        int currentConditionPos = conditionsAdapter.getCount();
        openConditionDialog(-1, currentConditionPos);
    }

    @Override
    public void onEditCondition(int position) {
        openConditionDialog(conditionsAdapter.getItem(position).getId(), position);
    }

    private void openConditionDialog(long conditionId, int position) {
        EditConditionDialogFragment
                .newInstance(ruleDetailsType, conditionId, currentRuleId, position, position == 0)
                .show(getChildFragmentManager(), "");
    }

    @Subscribe
    public void onConditionsListRequested(SaveRuleRequestedEvent event) {
        RealmResults<Condition> matchingConditions = (ruleDetailsType == RuleDetailsType.CONDITIONS) ? presenter.getConditionsForRule() : presenter.getConsequentsForRule();
        EventBus.getDefault().post(new RuleDetailsReadyEvent(ruleDetailsType, presenter.getConditionsList(matchingConditions)));
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
