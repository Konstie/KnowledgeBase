package com.app.knowledgebase.ui.sections.abs.presenter;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public abstract class BasePresenter implements IBasePresenter {
    private static final String DB_NAME = "knowledge.realm";

    private Context context;

    public BasePresenter(Context context) {
        this.context = context;
    }

    @Override
    public Realm getDatabase() {
        return Realm.getInstance(
                new RealmConfiguration.Builder(context)
                        .name(DB_NAME)
                        .build()
        );
    }
}