package com.app.knowledgebase;

import android.app.Application;
import android.content.Context;

public class KnowledgeBaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        KnowledgeBaseApplication.context = getApplicationContext();
    }

    public static Context getContext() {
        return KnowledgeBaseApplication.context;
    }
}
