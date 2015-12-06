package edu.oregonstate.cope.mActivity;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 12/5/15.
 *
 * Supported PsiTreeChangeEvents:
 * beforeChildrenChange    ->  childrenChanged
 * beforeChildAddition     ->  childAdded
 * beforeChildRemoval      ->  childRemoved
 * beforeChildReplacement  ->  childReplaced
 * beforeChildMovement     ->  childMoved
 * beforePropertyChange    ->  propertyChanged
 */
public enum PsiEvents {
    BEFORE_CHANGE ("beforeChildrenChange", false),
    BEFORE_ADDITION ("beforeChildAddition", false),
    BEFORE_REMOVAL ("beforeChildRemoval", false),
    BEFORE_REPLACEMENT ("beforeChildReplacement", false),
    BEFORE_MOVEMENT ("beforeChildMovement", false),
    BEFORE_PROPERTY ("beforePropertyChange", false),
    CHANGED ("childrenChanged", false),
    ADDED ("childAdded", true),
    REMOVED ("childrenRemoved", true),
    REPLACED ("childReplaced", true),
    MOVED ("childMoved", true),
    PROPERTY ("propertyChanged", false);

    private final String type;
    private final boolean countableEvent;

    PsiEvents(String type, boolean countableEvent) {
        this.type = type;
        this.countableEvent = countableEvent;
    }

    public String getType() { return type; }

    public boolean isCountable() { return countableEvent; }
}
