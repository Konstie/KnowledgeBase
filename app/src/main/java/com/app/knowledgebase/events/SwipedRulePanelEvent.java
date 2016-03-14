package com.app.knowledgebase.events;

import com.app.knowledgebase.ui.sections.rules.SwipeDirection;

public class SwipedRulePanelEvent {
    private SwipeDirection swipeDirection;

    public SwipedRulePanelEvent(SwipeDirection swipeDirection) {
        this.swipeDirection = swipeDirection;
    }

    public SwipeDirection getSwipeDirection() {
        return swipeDirection;
    }
}
