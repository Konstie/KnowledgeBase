package com.app.knowledgebase.ui.sections.abs;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BasePresenter implements BasePresenterListener {
    private Context context;

    public BasePresenter(Context context) {
        this.context = context;
    }

    @Override
    public Realm getDatabase(String dbName) {
        return Realm.getInstance(
                new RealmConfiguration.Builder(context)
                        .name(dbName)
                        .build()
        );
    }
}
