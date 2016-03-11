package com.app.knowledgebase.models;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Rule extends RealmObject {
    @Required private int id;
    @Required private boolean activated;
    private List<Fact> facts;
    private Fact resultFact;
    private Date dateAdded;
    private KnowledgeBase knowledgeBase;

    public Rule(int id, List<Fact> facts, Fact resultFact, boolean activated, Date dateAdded) {
        this.id = id;
        this.facts = facts;
        this.resultFact = resultFact;
        this.activated = activated;
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
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
