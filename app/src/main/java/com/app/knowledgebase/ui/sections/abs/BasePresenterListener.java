package com.app.knowledgebase.ui.sections.abs;

import io.realm.Realm;

public interface BasePresenterListener {
    Realm getDatabase(String dbName);
}
