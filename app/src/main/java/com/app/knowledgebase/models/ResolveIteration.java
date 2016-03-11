package com.app.knowledgebase.models;

import java.util.List;

import io.realm.annotations.Required;

public class ResolveIteration {
    @Required private int number;
    @Required private List<Fact> facts;
    private List<Rule> rulesUsed;
    private Integer conflict;

    public ResolveIteration(int number, List<Fact> facts, List<Rule> rulesUsed, Integer conflict) {
        this.number = number;
        this.facts = facts;
        this.rulesUsed = rulesUsed;
        this.conflict = conflict;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }

    public List<Rule> getRulesUsed() {
        return rulesUsed;
    }

    public void setRulesUsed(List<Rule> rulesUsed) {
        this.rulesUsed = rulesUsed;
    }

    public Integer getConflict() {
        return conflict;
    }

    public void setConflict(Integer conflict) {
        this.conflict = conflict;
    }
}
