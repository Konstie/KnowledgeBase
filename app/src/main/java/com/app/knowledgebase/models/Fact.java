package com.app.knowledgebase.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Fact extends RealmObject {
    @PrimaryKey private int id;
    private boolean enabled;
    @Required private String description;

    public Fact(int id, String description, boolean enabled) {
        this.id = id;
        this.description = description;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
