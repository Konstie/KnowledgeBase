package com.app.knowledgebase.models;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class KnowledgeBase extends RealmObject {
    private int id;
    @Required private String title;
    @Required private List<Fact> startFacts;
    @Required private List<Strategy> strategies;
    private List<Rule> rules;
    private List<ResolveIteration> resolveIterations;

    public KnowledgeBase(int id, String title, List<Fact> startFacts, List<Strategy> strategies, List<Rule> rules) {
        this.id = id;
        this.title = title;
        this.startFacts = startFacts;
        this.strategies = strategies;
        this.rules = rules;
    }

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

    public List<Fact> getStartFacts() {
        return startFacts;
    }

    public void setStartFacts(List<Fact> startFacts) {
        this.startFacts = startFacts;
    }

    public List<Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<Strategy> strategies) {
        this.strategies = strategies;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<ResolveIteration> getResolveIterations() {
        return resolveIterations;
    }

    public void setResolveIterations(List<ResolveIteration> resolveIterations) {
        this.resolveIterations = resolveIterations;
    }
}
