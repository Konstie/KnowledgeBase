package com.app.knowledgebase.ui.sections.abs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.app.knowledgebase.R;

public class BaseDialogFragment extends DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.App_Theme_DialogTransparentStyle);
    }

    protected void dismissDialog() {
        dismissAllowingStateLoss();
    }

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        fragmentManager.beginTransaction().add(this, tag).commitAllowingStateLoss();
    }
}