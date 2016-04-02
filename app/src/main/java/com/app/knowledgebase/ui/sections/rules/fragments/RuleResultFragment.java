package com.app.knowledgebase.ui.sections.rules.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.dao.FactsDao;
import com.app.knowledgebase.events.RuleResultSetEvent;
import com.app.knowledgebase.events.SaveRuleRequestedEvent;
import com.app.knowledgebase.events.SwipedRulePanelEvent;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.rules.SwipeDirection;
import com.app.knowledgebase.ui.sections.rules.presenters.RuleResultPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RuleResultFragment extends Fragment {
    private static final String KEY_RULE = "currentRule";
    private static final String KEY_IS_NEW = "isNewRule";

    private int currentRuleId;
    private boolean isNewRule;

    private RuleResultPresenter presenter;

    @Bind(R.id.btn_swipe_right) ImageButton buttonSwipeRight;
    @Bind(R.id.edit_result) AutoCompleteTextView editResultFact;

    public static RuleResultFragment newInstance(int currentRuleId, boolean isNewRule) {
        RuleResultFragment fragment = new RuleResultFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_RULE, currentRuleId);
        args.putBoolean(KEY_IS_NEW, isNewRule);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentRuleId =  getArguments().getInt(KEY_RULE);
            isNewRule = getArguments().getBoolean(KEY_IS_NEW);
        }
        presenter = new RuleResultPresenter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rule_result, container, false);
        ButterKnife.bind(this, rootView);

        buttonSwipeRight.setOnClickListener(v -> {
            EventBus.getDefault().post(new SwipedRulePanelEvent(SwipeDirection.RIGHT));
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, presenter.getPossibleResultFactsList());
        editResultFact.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!isNewRule) {
            editResultFact.setText(presenter.getResultFactDescription(currentRuleId));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onSaveRuleRequested(SaveRuleRequestedEvent event) {
        EventBus.getDefault().post(new RuleResultSetEvent(editResultFact.getText().toString(), event.getConditions()));
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}