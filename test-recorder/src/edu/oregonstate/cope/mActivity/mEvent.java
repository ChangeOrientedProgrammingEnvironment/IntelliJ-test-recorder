package edu.oregonstate.cope.mActivity;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.util.PsiTreeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 11/20/15.
 */
public class mEvent {

    protected final Date timestamp;
    protected PsiClass myClass = null;
    protected PsiMethod myMethod = null;
    protected List<Map.Entry<PsiEvents,PsiTreeChangeEvent>> eventList = new ArrayList<>();
    protected Integer weight = 0;

    public mEvent(PsiEvents type, PsiTreeChangeEvent event) throws IllegalArgumentException {
        if (type != PsiEvents.BEFORE_CHANGE) throw new IllegalArgumentException("type argument is not allowed");

        this.timestamp = new Date(System.currentTimeMillis());
        this.eventList.add(new AbstractMap.SimpleEntry<>(type, event));
    }

    public void add(PsiEvents type, PsiTreeChangeEvent event) throws Exception {

        PsiClass aClass = PsiTreeUtil.getParentOfType(event.getChild(), PsiClass.class, false);
        if (aClass != null && this.myClass == null) {
            this.myClass = aClass;
        } else if (aClass != null && aClass.getName().equals(myClass.getName())) {
            throw new Exception("class mismatch: " + aClass.getName() + " -> " + myClass.getName());
        }

        PsiMethod aMethod = PsiTreeUtil.getParentOfType(event.getChild(), PsiMethod.class, false);
        if (aMethod != null && this.myMethod == null) {
            this.myMethod = aMethod;
        } else if (aMethod != null && aMethod.getName().equals(myMethod.getName())) {
            throw new Exception("method mismatch: " + aMethod.getName() + " -> " + myMethod.getName());
        }

        try {
            String nodeType = event.getElement().getNode().getElementType().toString();
            if (nodeType.equals("WHITE_SPACE")) return;
        } catch (NullPointerException e) {
            // do nothing; since this event doesn't have either a PsiElement, ASTNode, or IElementType
        }

        eventList.add(new AbstractMap.SimpleEntry<>(type, event));
        if (type.isCountable()) weight += 1;
    }

    public void print() {
        System.out.println(getTimestamp() + " | " + myClass.getName() + "." + myMethod.getName() + " | " + weight);
    }

    public void print_state() {
        for (Map.Entry<PsiEvents, PsiTreeChangeEvent> aEvent : this.eventList) {
            System.out.println("\t" + aEvent.getKey().getType());
        }
    }

    public String getTimestamp(DateFormat formatter) {
        return formatter.format(this.timestamp);
    }

    public String getTimestamp() {
        DateFormat formatter = new SimpleDateFormat("Y/M/d HH:mm:ss:SSS");
        return this.getTimestamp(formatter);
    }

}
