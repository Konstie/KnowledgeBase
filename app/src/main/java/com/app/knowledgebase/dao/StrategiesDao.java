package com.app.knowledgebase.dao;

import com.app.knowledgebase.models.Strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class StrategiesDao {
    public static final int ID_LEX = 1;
    public static final int ID_MEA = 2;
    public static final int ID_FIRST_ACTIVATION = 3;
    public static final int ID_LAST_ACTIVATION = 4;
    public static final int ID_SIMPLICITY = 5;
    public static final int ID_COMPLEXITY = 6;
    
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
            strategyLex.setName(StrategiesTitles.LEX);
            strategyLex.setUniqueId(ID_LEX);
            Strategy strategyMea = database.createObject(Strategy.class);
            strategyMea.setName(StrategiesTitles.MEA);
            strategyMea.setUniqueId(ID_MEA);
            Strategy firstActivated = database.createObject(Strategy.class);
            firstActivated.setName(StrategiesTitles.FIRST_ACTIVATED);
            firstActivated.setUniqueId(ID_FIRST_ACTIVATION);
            Strategy lastActivated = database.createObject(Strategy.class);
            lastActivated.setName(StrategiesTitles.LAST_ACTIVATED);
            lastActivated.setUniqueId(ID_LAST_ACTIVATION);
            Strategy simplified = database.createObject(Strategy.class);
            simplified.setName(StrategiesTitles.SIMPLICITY);
            simplified.setUniqueId(ID_SIMPLICITY);
            Strategy complicated = database.createObject(Strategy.class);
            complicated.setName(StrategiesTitles.COMPLEXITY);
            complicated.setUniqueId(ID_COMPLEXITY);

            allStrategies.addAll(Arrays.asList(strategyLex, strategyMea, simplified, complicated, firstActivated, lastActivated));
            database.copyToRealm(allStrategies);
        });
    }

    public RealmResults<Strategy> getAllStrategies(Realm database) {
        return database.where(Strategy.class).findAll();
    }

    public List<String> getAllStrategiesTitles(Realm database) {
        List<String> allStrategies = new ArrayList<>();
        RealmResults<Strategy> strategies = database.where(Strategy.class).findAll();
        for (Strategy strategy : strategies) {
            allStrategies.add(strategy.getName());
        }
        return allStrategies;
    }

    public Strategy getStrategyById(Realm database, int uniqueId) {
        return database.where(Strategy.class)
                .equalTo("uniqueId", uniqueId)
                .findFirst();
    }

    public Strategy getStrategyByName(Realm database, String strategyName) {
        return database.where(Strategy.class)
                .equalTo("name", strategyName)
                .findFirst();
    }
}
