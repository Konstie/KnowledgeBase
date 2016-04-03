package com.app.knowledgebase.models;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Rule extends RealmObject implements Serializable {
    @PrimaryKey private int id;
    private int knowledgeBaseId;
    private int positionInBase;
    @Ignore private boolean expired;
    @Ignore private boolean used;
    private RealmList<Condition> conditions;
    private Fact consequentFact;
    private Date dateAdded;
    private int factsCount;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionInBase() {
        return positionInBase;
    }

    public void setPositionInBase(int positionInBase) {
        this.positionInBase = positionInBase;
    }

    public int getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public void setKnowledgeBaseId(int knowledgeBaseId) {
        this.knowledgeBaseId = knowledgeBaseId;
    }

    public RealmList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(RealmList<Condition> conditions) {
        this.conditions = conditions;
    }

    public Fact getConsequentFact() {
        return consequentFact;
    }

    public void setConsequentFact(Fact consequentFact) {
        this.consequentFact = consequentFact;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getFactsCount() {
        return factsCount;
    }

    public void setFactsCount(int factsCount) {
        this.factsCount = factsCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
