package com.app.knowledgebase.models;

import java.util.List;

public class ResolveIteration {
    private int number;
    private List<String> facts;
    private List<String> rulesUsed;
    private String conflict;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getFacts() {
        return facts;
    }

    public void setFacts(List<String> facts) {
        this.facts = facts;
    }

    public List<String> getRulesUsed() {
        return rulesUsed;
    }

    public void setRulesUsed(List<String> rulesUsed) {
        this.rulesUsed = rulesUsed;
    }

    public String getConflict() {
        return conflict;
    }

    public void setConflict(String conflict) {
        this.conflict = conflict;
    }
}
