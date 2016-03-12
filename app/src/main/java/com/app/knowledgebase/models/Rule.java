package com.app.knowledgebase.models;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Rule extends RealmObject {
    private boolean activated;
    private RealmList<Fact> facts;
    private Fact resultFact;
    private Date dateAdded;
    private KnowledgeBase knowledgeBase;

    public RealmList<Fact> getFacts() {
        return facts;
    }

    public void setFacts(RealmList<Fact> facts) {
        this.facts = facts;
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

    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }
}
