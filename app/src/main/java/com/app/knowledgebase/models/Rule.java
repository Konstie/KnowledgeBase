package com.app.knowledgebase.models;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Rule extends RealmObject implements Serializable {
    private int id;
    private boolean activated;
    private RealmList<Condition> conditions;
    private Fact resultFact;
    private Date dateAdded;
//    private KnowledgeBase knowledgeBase;
    private int factsCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(RealmList<Condition> conditions) {
        this.conditions = conditions;
    }

    public Fact getResultFact() {
        return resultFact;
    }

    public void setResultFact(Fact resultFact) {
        this.resultFact = resultFact;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

//    public KnowledgeBase getKnowledgeBase() {
//        return knowledgeBase;
//    }
//
//    public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
//        this.knowledgeBase = knowledgeBase;
//    }

    public int getFactsCount() {
        return factsCount;
    }

    public void setFactsCount(int factsCount) {
        this.factsCount = factsCount;
    }
}
