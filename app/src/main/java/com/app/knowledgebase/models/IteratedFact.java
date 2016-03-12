package com.app.knowledgebase.models;

import io.realm.RealmObject;

public class IteratedFact extends RealmObject {
    private Fact fact;
    private boolean activated;

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
