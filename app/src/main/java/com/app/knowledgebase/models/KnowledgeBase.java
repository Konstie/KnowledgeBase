package com.app.knowledgebase.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class KnowledgeBase extends RealmObject {
    @PrimaryKey @Required private String title;
    private RealmList<IteratedFact> startFacts;
    private RealmList<Strategy> strategies;
    private RealmList<Rule> rules;
    private RealmList<ResolveIteration> resolveIterations;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<IteratedFact> getStartFacts() {
        return startFacts;
    }

    public void setStartFacts(RealmList<IteratedFact> startFacts) {
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
