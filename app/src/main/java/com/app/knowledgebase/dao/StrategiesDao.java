package com.app.knowledgebase.dao;

import com.app.knowledgebase.models.Strategy;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmList;

public class StrategiesDao {
    private static StrategiesDao instance;

    private StrategiesDao() {}

    public static StrategiesDao get() {
        if (instance == null) {
            instance = new StrategiesDao();
        }
        return instance;
    }

    /**
     * All strategies will be copied during MainActivity launch
     * @param database
     */
    public void setAllStrategies(Realm database) {
        database.executeTransaction(realm -> {
            RealmList<Strategy> allStrategies = new RealmList<>();

            Strategy strategyLex = database.createObject(Strategy.class);
            strategyLex.setName("LEX");
            Strategy firstActivated = database.createObject(Strategy.class);
            firstActivated.setName("First activated");
            Strategy lastActivated = database.createObject(Strategy.class);
            lastActivated.setName("Last activated");
            Strategy complicated = database.createObject(Strategy.class);
            complicated.setName("Complicated");
            Strategy simplified = database.createObject(Strategy.class);
            simplified.setName("Simplified");
            Strategy strategyMea = database.createObject(Strategy.class);
            strategyMea.setName("MEA");

            allStrategies.addAll(Arrays.asList(strategyLex, strategyMea, simplified, complicated, firstActivated, lastActivated));
            database.copyToRealm(allStrategies);
        });
    }
}
