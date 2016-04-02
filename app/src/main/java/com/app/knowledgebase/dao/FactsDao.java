package com.app.knowledgebase.dao;

import com.app.knowledgebase.helpers.IdHelper;
import com.app.knowledgebase.models.Fact;
import com.app.knowledgebase.models.IteratedFact;

import java.util.ArrayList;
import java.util.Collections;
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

    public Fact findFactById(Realm database, long id) {
        RealmQuery<Fact> query = database.where(Fact.class);
        query.equalTo("id", id);
        return query.findFirst();
    }

    public Fact findFactByDescription(Realm database, String descriptionQuery) {
        RealmQuery<Fact> query = database.where(Fact.class);
        query.equalTo("description", descriptionQuery);
        return query.findFirst();
    }

    public RealmResults<Fact> findAllFacts(Realm database) {
        return database.where(Fact.class).findAll();
    }

    private List<String> getAllFacts(Realm realm) {
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
        database.executeTransaction(realm -> fact.removeFromRealm());
    }

    public List<String> getAllSortedFactsInText(Realm database) {
        List<String> allFacts = getAllFacts(database);
        Collections.sort(allFacts);
        return allFacts;
    }

    public Fact createNewFact(Realm database, String factDescription) {
        database.beginTransaction();
        Fact newFact = database.createObject(Fact.class);
        newFact.setId(IdHelper.get().getGeneratedUniqueIdForFact(database));
        newFact.setDescription(factDescription);
        database.commitTransaction();
        return newFact;
    }

    public IteratedFact createIteratedFact(Realm database, Fact fact, boolean activated) {
        database.beginTransaction();
        IteratedFact newIteratedFact = database.createObject(IteratedFact.class);
        newIteratedFact.setUniqueId(IdHelper.get().getGeneratedUniqueIdForIteratedFact(database));
        newIteratedFact.setFact(fact);
        newIteratedFact.setActivated(activated);
        database.commitTransaction();
        return newIteratedFact;
    }

    public Fact createFact(Realm database, String description) {
        database.beginTransaction();
        Fact newFact = database.createObject(Fact.class);
        newFact.setId(IdHelper.get().getGeneratedUniqueIdForFact(database));
        newFact.setDescription(description);
        database.commitTransaction();
        return newFact;
    }
}
