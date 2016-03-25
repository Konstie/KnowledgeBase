package com.app.knowledgebase.dao;

import com.app.knowledgebase.helpers.IdHelper;
import com.app.knowledgebase.models.Strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

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
            strategyLex.setUniqueId(IdHelper.get().getGeneratedUniqueId(database, Strategy.class));
            Strategy firstActivated = database.createObject(Strategy.class);
            firstActivated.setName("First activated");
            firstActivated.setUniqueId(IdHelper.get().getGeneratedUniqueId(database, Strategy.class));
            Strategy lastActivated = database.createObject(Strategy.class);
            lastActivated.setName("Last activated");
            lastActivated.setUniqueId(IdHelper.get().getGeneratedUniqueId(database, Strategy.class));
            Strategy complicated = database.createObject(Strategy.class);
            complicated.setName("Complicated");
            complicated.setUniqueId(IdHelper.get().getGeneratedUniqueId(database, Strategy.class));
            Strategy simplified = database.createObject(Strategy.class);
            simplified.setName("Simplified");
            simplified.setUniqueId(IdHelper.get().getGeneratedUniqueId(database, Strategy.class));
            Strategy strategyMea = database.createObject(Strategy.class);
            strategyMea.setName("MEA");
            strategyMea.setUniqueId(IdHelper.get().getGeneratedUniqueId(database, Strategy.class));

            allStrategies.addAll(Arrays.asList(strategyLex, strategyMea, simplified, complicated, firstActivated, lastActivated));
            database.copyToRealm(allStrategies);
        });
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
