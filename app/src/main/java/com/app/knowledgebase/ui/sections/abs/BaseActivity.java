package com.app.knowledgebase.ui.sections.abs;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setToolbar(Toolbar toolbar, @StringRes int titleRes, boolean isHomeEnabled) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleRes);
            actionBar.setDisplayHomeAsUpEnabled(isHomeEnabled);
        }
    }
}
