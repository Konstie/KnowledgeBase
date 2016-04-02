package com.app.knowledgebase.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class KnowledgeBase extends RealmObject implements Serializable {
    @PrimaryKey private int id;
    @Required private String title;
    private RealmList<Fact> startFacts;
    private RealmList<Strategy> strategies;
    private RealmList<Rule> rules;
    private RealmList<ResolveIteration> resolveIterations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<Fact> getStartFacts() {
        return startFacts;
    }

    public void setStartFacts(RealmList<Fact> startFacts) {
        this.startFacts = startFacts;
    }

    public RealmList<Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(RealmList<Strategy> strategies) {
        this.strategies = strategies;
    }

    public RealmList<Rule> getRules() {
        return rules;
    }

    public void setRules(RealmList<Rule> rules) {
        this.rules = rules;
    }

    public RealmList<ResolveIteration> getResolveIterations() {
        return resolveIterations;
    }

    public void setResolveIterations(RealmList<ResolveIteration> resolveIterations) {
        this.resolveIterations = resolveIterations;
    }
}
