package com.app.knowledgebase.ui.sections.rules.fragments;

import android.support.v4.app.Fragment;

public abstract class AbsRulePartsFragment extends Fragment {
    protected abstract void openConditionDialog(long conditionId, int position);
}
