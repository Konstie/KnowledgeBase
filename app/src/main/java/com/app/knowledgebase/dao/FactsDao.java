package com.app.knowledgebase.dao;

import com.app.knowledgebase.models.Fact;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class FactsDao {
    private static FactsDao instance;

    private FactsDao() {}

    public static FactsDao get() {
        if (instance == null) {
            instance = new FactsDao();
        }
        return instance;
    }

    public Fact findFactByDescription(Realm database, String descriptionQuery) {
        RealmQuery<Fact> query = database.where(Fact.class);
        query.equalTo("description", descriptionQuery);
        return query.findFirst();
    }

    public List<String> getAllFacts(Realm realm) {
        RealmResults<Fact> allFacts = realm.where(Fact.class).findAll();
        List<String> allFactsStrings = new ArrayList<>();
        for (Fact fact : allFacts) {
            allFactsStrings.add(fact.getDescription());
        }
        return allFactsStrings;
    }

    public void updateFact(Realm database, Fact fact, String newDescription) {
        database.beginTransaction();
        fact.setDescription(newDescription);
        database.commitTransaction();
    }

    public void removeFact(Realm database, Fact fact) {
        database.beginTransaction();
        fact.removeFromRealm();
        database.commitTransaction();
    }
}
