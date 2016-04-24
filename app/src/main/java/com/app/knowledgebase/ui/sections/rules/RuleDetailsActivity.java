package com.app.knowledgebase.ui.sections.rules;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.knowledgebase.R;
import com.app.knowledgebase.constants.Constants;
import com.app.knowledgebase.dao.RulesDao;
import com.app.knowledgebase.events.RuleDetailsReadyEvent;
import com.app.knowledgebase.events.SaveRuleRequestedEvent;
import com.app.knowledgebase.events.SwipedRulePanelEvent;
import com.app.knowledgebase.models.Condition;
import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.abs.BaseActivity;
import com.app.knowledgebase.ui.sections.rules.adapters.ConditionsAdapter;
import com.app.knowledgebase.ui.sections.rules.adapters.RuleInfoPagerAdapter;
import com.app.knowledgebase.ui.sections.rules.fragments.RuleDetailsType;
import com.app.knowledgebase.ui.sections.rules.presenters.AddRulePresenter;
import com.app.knowledgebase.ui.sections.rules.presenters.IAddRuleView;
import com.app.knowledgebase.ui.sections.rules.presenters.IRuleDateView;
import com.app.knowledgebase.ui.sections.rules.presenters.RuleDatePresenter;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class RuleDetailsActivity extends BaseActivity implements IAddRuleView, IRuleDateView {
    private static final int RULE_FILLED_COUNTER = 2;

    private boolean newRule = true;
    private int baseId = -1;
    private int ruleId = -1;
    private int rulesCountInBase;
    private int ruleCounter = 0;
    private Date currentRuleDate;
    private RealmList<Condition> conditions;
    private RealmList<Condition> consequents;

    private AddRulePresenter presenter;
    private RuleDatePresenter datePresenter;
    private RuleInfoPagerAdapter ruleInfoPagerAdapter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.pager_rule_main_info) ViewPager ruleDetailsPager;
    @Bind(R.id.btn_add_rule) FloatingActionButton buttonSaveRule;
    @Bind(R.id.text_date) TextView textDate;
    @Bind(R.id.btn_edit_date) ImageButton buttonEditDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rule_2);
        ButterKnife.bind(this);
        setToolbar(toolbar, R.string.rule_category, true);

        conditions = new RealmList<>();
        consequents = new RealmList<>();

        if (getIntent() != null) {
            ruleId = getIntent().getIntExtra(Constants.EXTRA_RULE_ID, -1);
            baseId = getIntent().getIntExtra(Constants.EXTRA_KNOWLEDGE_BASE_ID, -1);
            rulesCountInBase = getIntent().getIntExtra(Constants.EXTRA_RULES_COUNT_IN_BASE, -1);
        }

        presenter = new AddRulePresenter(this, this);
        datePresenter = new RuleDatePresenter(this, this, ruleId);
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
        }

        datePresenter.setInitialDateForRule();
        textDate.setText(datePresenter.getFormattedDate());

        buttonEditDate.setOnClickListener(v -> {
            datePresenter.onDateChangeClicked();
        });

        setupRuleConfigPager(currentRule.getId());

        buttonSaveRule.setOnClickListener(v -> {
            EventBus.getDefault().post(new SaveRuleRequestedEvent());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void setupRuleConfigPager(int currentRuleId) {
        ruleInfoPagerAdapter = new RuleInfoPagerAdapter(getSupportFragmentManager(), currentRuleId);
        ruleDetailsPager.setAdapter(ruleInfoPagerAdapter);
        ruleDetailsPager.setCurrentItem(RuleInfoPagerAdapter.TYPE_CONDITIONS_LIST);
    }

    @Override
    public void onSaveThisRule() {
        onBackPressed();
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

    @Override
    public void showDatePickerDialog() {
        CalendarDatePickerDialogFragment.OnDateSetListener dateSetListener = (dialog, year, monthOfYear, dayOfMonth) -> {
            Calendar date = Calendar.getInstance();
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            datePresenter.saveNewDateForRule(year, monthOfYear, dayOfMonth);
            textDate.setText(datePresenter.getFormattedDate());
            currentRuleDate = datePresenter.getRuleDate();
        };

        CalendarDatePickerDialogFragment.OnDialogDismissListener dismissListener = dialoginterface -> {

        };

        CalendarDatePickerDialogFragment dialog = new CalendarDatePickerDialogFragment();
        dialog.setOnDateSetListener(dateSetListener);
        dialog.setOnDismissListener(dismissListener);
        dialog.show(getSupportFragmentManager(), "DATE_PICKER_TAG");
    }

    @Subscribe
    public void onRuleDetailsReadyEvent(RuleDetailsReadyEvent event) {
        ruleCounter++;
        if (event.getRuleDetailsType() == RuleDetailsType.CONDITIONS) {
            conditions = event.getConditions();
        } else {
            consequents = event.getConditions();
        }

        if (ruleCounter == RULE_FILLED_COUNTER) {
            presenter.onSaveRuleClicked(baseId, ruleId, conditions, consequents, currentRuleDate, newRule);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
