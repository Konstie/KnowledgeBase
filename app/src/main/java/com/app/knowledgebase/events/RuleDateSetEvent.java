package com.app.knowledgebase.events;

import java.util.Date;

public class RuleDateSetEvent {
    private Date ruleDate;

    public RuleDateSetEvent(Date ruleDate) {
        this.ruleDate = ruleDate;
    }

    public Date getRuleDate() {
        return ruleDate;
    }
}
