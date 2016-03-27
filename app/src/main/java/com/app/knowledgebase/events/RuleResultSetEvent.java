package com.app.knowledgebase.events;

public class RuleResultSetEvent {
    private String resultFactTitle;

    public RuleResultSetEvent(String resultFactTitle) {
        this.resultFactTitle = resultFactTitle;
    }

    public String getResultFact() {
        return resultFactTitle;
    }
}
