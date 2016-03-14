package com.app.knowledgebase.ui.sections.rules.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.knowledgebase.models.Rule;
import com.app.knowledgebase.ui.sections.rules.fragments.RuleDateFragment;
import com.app.knowledgebase.ui.sections.rules.fragments.RuleResultFragment;

public class RuleInfoPagerAdapter extends FragmentStatePagerAdapter {
    private static final int TYPE_RESULT = 0;
    private static final int TYPE_DATE = 1;
    private static final int RULE_INFO_PAGES_COUNT = 2;

    private Rule currentRule;

    public RuleInfoPagerAdapter(FragmentManager fm, Rule currentRule) {
        super(fm);
        this.currentRule = currentRule;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == TYPE_RESULT) {
            return RuleResultFragment.newInstance(currentRule);
        } else if (position == TYPE_DATE) {
            return RuleDateFragment.newInstance(currentRule);
        } else {
            return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return RULE_INFO_PAGES_COUNT;
    }
}
