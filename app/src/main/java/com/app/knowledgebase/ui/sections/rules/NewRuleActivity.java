package com.app.knowledgebase.ui.sections.rules;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.events.SwipedRulePanelEvent;
import com.app.knowledgebase.ui.sections.abs.BaseActivity;
import com.app.knowledgebase.ui.sections.rules.adapters.ConditionsAdapter;
import com.app.knowledgebase.ui.sections.rules.presenters.AddRulePresenter;
import com.app.knowledgebase.ui.sections.rules.presenters.IAddRuleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewRuleActivity extends BaseActivity implements IAddRuleView {
    private static final int PAGE_RESULT = 0;
    private static final int PAGE_DATE = 1;

    private HashMap<Integer, List<String>> conditionsMap;

    private AddRulePresenter presenter;
    private ConditionsAdapter adapter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.pager_rule_main_info) ViewPager panelPager;
    @Bind(R.id.list_facts) ListView listConditions;
    @Bind(R.id.btn_add_rule) FloatingActionButton buttonAddRule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rule);
        ButterKnife.bind(this);
        setToolbar(toolbar, R.string.rule_category, true);

        conditionsMap = new HashMap<>();
        conditionsMap.put(0, new ArrayList<String>(Arrays.asList("", "")));
        presenter = new AddRulePresenter(this, this);

        adapter = new ConditionsAdapter(this, conditionsMap);
        listConditions.setAdapter(adapter);
        View listFooter = getLayoutInflater().inflate(R.layout.footer_add_condition, null, false);
        listFooter.findViewById(R.id.btn_add_condition).setOnClickListener(v -> {
            presenter.onAddConditionClicked();
        });
        listConditions.addFooterView(listFooter);

        buttonAddRule.setOnClickListener(v -> {
            presenter.onAddRuleClicked();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onSwipedRulePanel(SwipedRulePanelEvent event) {
        if (event.getSwipeDirection() == SwipeDirection.LEFT) {
            panelPager.setCurrentItem(PAGE_RESULT);
        } else {
            panelPager.setCurrentItem(PAGE_DATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void addNewCondition() {
        int currentConditionPos = adapter.getCount();
        conditionsMap.put(currentConditionPos, new ArrayList<String>(Arrays.asList("", "")));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addNewRule() {

    }
}
