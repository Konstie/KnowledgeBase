package com.app.knowledgebase.ui.sections.rules.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.knowledgebase.ui.sections.rules.fragments.RuleConditionsFragment;
import com.app.knowledgebase.ui.sections.rules.fragments.RuleDetailsType;

public class RuleInfoPagerAdapter extends FragmentStatePagerAdapter {
    public static final int TYPE_CONDITIONS_LIST = 0;
    public static final int TYPE_CONSEQUENCES_LIST = 1;
    private static final int RULE_INFO_PAGES_COUNT = 2;

    private int currentRuleId;

    public RuleInfoPagerAdapter(FragmentManager fm, int currentRuleId) {
        super(fm);
        this.currentRuleId = currentRuleId;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == TYPE_CONDITIONS_LIST) {
            return RuleConditionsFragment.newInstance(RuleDetailsType.CONDITIONS, currentRuleId);
        } else if (position == TYPE_CONSEQUENCES_LIST) {
            return RuleConditionsFragment.newInstance(RuleDetailsType.CONSEQUENTS, currentRuleId);
        } else {
            return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return RULE_INFO_PAGES_COUNT;
    }
}
