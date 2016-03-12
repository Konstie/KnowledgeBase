package com.app.knowledgebase.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ResolveIteration extends RealmObject {
    private int number;
    private RealmList<Fact> facts;
    private RealmList<Rule> rulesUsed;
    private Integer conflict;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public RealmList<Fact> getFacts() {
        return facts;
    }

    public void setFacts(RealmList<Fact> facts) {
        this.facts = facts;
    }

    public RealmList<Rule> getRulesUsed() {
        return rulesUsed;
    }

    public void setRulesUsed(RealmList<Rule> rulesUsed) {
        this.rulesUsed = rulesUsed;
    }

    public Integer getConflict() {
        return conflict;
    }

    public void setConflict(Integer conflict) {
        this.conflict = conflict;
    }
}
