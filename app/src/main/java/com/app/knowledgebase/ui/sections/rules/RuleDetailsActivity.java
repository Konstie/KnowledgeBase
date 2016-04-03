package com.app.knowledgebase.ui.sections.rules;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.dao.ConditionsDao;
import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.events.RuleDateSetEvent;
import com.app.knowledgebase.events.RuleResultSetEvent;
import com.app.knowledgebase.events.SaveRuleRequestedEvent;
import com.app.knowledgebase.events.SwipedRulePanelEvent;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.BaseActivity;
import com.app.knowledgebase.ui.sections.rules.adapters.ConditionsAdapter;
import com.app.knowledgebase.ui.sections.rules.adapters.RuleInfoPagerAdapter;
import com.app.knowledgebase.ui.sections.rules.fragments.EditConditionDialogFragment;
import com.app.knowledgebase.ui.sections.rules.presenters.AddRulePresenter;
import com.app.knowledgebase.ui.sections.rules.presenters.IAddRuleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class RuleDetailsActivity extends BaseActivity implements IAddRuleView {
    private static final int PAGE_RESULT = 0;
    private static final int PAGE_DATE = 1;

    private boolean newRule = true;
    private int baseId = -1;
    private int ruleId = -1;
    private int rulesCountInBase;
    private Date currentRuleDate;
    private String resultFactTitle;

    private AddRulePresenter presenter;
    private ConditionsAdapter conditionsAdapter;
    private RuleInfoPagerAdapter ruleInfoPagerAdapter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.pager_rule_main_info) ViewPager panelPager;
    @Bind(R.id.list_facts) ListView listConditions;
    @Bind(R.id.btn_add_rule) FloatingActionButton buttonSaveRule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rule);
        ButterKnife.bind(this);
        setToolbar(toolbar, R.string.rule_category, true);

        if (getIntent() != null) {
            ruleId = getIntent().getIntExtra(Constants.EXTRA_RULE_ID, -1);
            baseId = getIntent().getIntExtra(Constants.EXTRA_KNOWLEDGE_BASE_ID, -1);
            rulesCountInBase = getIntent().getIntExtra(Constants.EXTRA_RULES_COUNT_IN_BASE, -1);
        }

        presenter = new AddRulePresenter(this, this);
        Rule currentRule = presenter.getCurrentRule(ruleId);
        currentRuleDate = (currentRule == null || currentRule.getDateAdded() == null)
                ? new Date(System.currentTimeMillis()) : currentRule.getDateAdded();
        if (currentRule == null) {
            Log.w("RuleDetails", "Creating new rule");
            RulesDao.get().createNewRule(presenter.getDatabase(), rulesCountInBase);
            currentRule = presenter.getLastCreatedRule();
            ruleId = currentRule.getId();
        } else {
            newRule = false;
            resultFactTitle = currentRule.getConsequentFact().getDescription();
        }

        presenter.onConditionsInitialized(ruleId);

        setupRuleConfigPager(currentRule.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        conditionsAdapter.notifyDataSetChanged();
    }

    private void setupRuleConfigPager(int currentRuleId) {
        ruleInfoPagerAdapter = new RuleInfoPagerAdapter(getSupportFragmentManager(), currentRuleId, newRule);
        panelPager.setAdapter(ruleInfoPagerAdapter);
        panelPager.setCurrentItem(PAGE_RESULT);
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
    public void setupConditionsForRule(RealmList<Condition> conditions) {
        View listFooter = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.footer_add_condition, null, false);
        ImageButton buttonAddCondition = (ImageButton) listFooter.findViewById(R.id.btn_add_condition);
        buttonAddCondition.setOnClickListener(v -> presenter.onAddConditionClicked());
        listConditions.addFooterView(listFooter);

        conditionsAdapter = new ConditionsAdapter(this, ConditionsDao.get().findConditionsByRuleId(
                presenter.getDatabase(), ruleId), true
        );
        listConditions.setAdapter(conditionsAdapter);
        listConditions.setOnItemClickListener((parent, view, position, id) -> {
            presenter.onEditConditionClicked(position);
        });

        Log.w("RuleDetails", "Conditions size: " + conditions.size());

        buttonSaveRule.setOnClickListener(v -> {
            Log.w("RuleDetails", "Conditions size: " + conditions.size());
            EventBus.getDefault().post(new SaveRuleRequestedEvent(conditions));
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
                .newInstance(conditionId, ruleId, position)
                .show(getSupportFragmentManager(), "");
    }


    @Subscribe
    public void onDateChanged(RuleDateSetEvent event) {
        currentRuleDate = event.getRuleDate();
    }

    @Subscribe
    public void onResultFactSet(RuleResultSetEvent event) {
        resultFactTitle = event.getResultFactTitle();
        Log.w("RuleDetails", "Conditions result size: " + event.getConditions().size());
        presenter.onSaveRuleClicked(baseId, ruleId, event.getConditions(), resultFactTitle, currentRuleDate, newRule);
    }

    @Override
    public void onSaveThisRule() {
        onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
