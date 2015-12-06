package edu.oregonstate.cope.mActivity;

import com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 11/18/15.
 *
 * Note: This class follows the Singleton model in order to act as sole aggregator for events across a project.
 */
public final class mTracker {

    private static mTracker instance = null;
    private TreeSet<mEvent> eventLog = new TreeSet<>(new mEventComparator());
//    private Map<String, Integer> methodMap = new HashMap<>();

    private mTracker() {
        // Exists only to defeat instantiation.
    }

    public static mTracker getInstance() {
        if(instance == null) {
            instance = new mTracker();
        }
        return instance;
    }

    class mEventComparator implements Comparator<mEvent> {
        @Override
        public int compare(mEvent e1, mEvent e2) {
            return e1.getTimestamp().compareTo(e2.getTimestamp());
        }
    }

    DateFormat formatter = new SimpleDateFormat("Y/M/d HH:mm:ss:SSS");

    public void add(PsiEvents type, PsiTreeChangeEvent event) {

        switch (type) {
            case BEFORE_CHANGE:
                try {
                    eventLog.add(new mEvent(type, event));
                } catch (IllegalArgumentException e) {
                    System.out.println("ERROR 1010: " + e.getMessage());
                }
                return;
            case BEFORE_PROPERTY:
                return;
            case PROPERTY:
                return;
            default:
                try {
                    eventLog.last().add(type, event);
                } catch (Exception e) {
                    System.out.println("ERROR 1032: " + e.getMessage());
                }
        }
    }

    @NotNull
    public Set getAllEvents() { return eventLog.descendingSet(); }

    public void printAllEvents() {
        eventLog.forEach(mEvent::print);
    }

//    @NotNull
//    public Set getAllMethods() { return methodMap.entrySet(); }

//    public void update(String key, Integer count) {
//        Integer old = methodMap.getOrDefault(key, 0);
//        methodMap.remove(key);
//        methodMap.put(key, (old + count));
//    }

//    public Integer get(PsiClass psiClass, PsiMethod psiMethod) {
//        String filename = FilenameUtils.removeExtension(psiClass.getContainingFile().getName());
//        String key = filename + "." + psiClass.getName() + "." + psiMethod.getName();
//        return methodMap.get(key);
//    }

//    public void collectMethods(Project project, VirtualFile vFile) {
//        PsiFile pfile = PsiManager.getInstance(project).findFile(vFile);
//        PsiElement root = pfile.findElementAt(0);
//
//        Collection<PsiMethod> methods = PsiTreeUtil.findChildrenOfType(root, PsiMethod.class);
//        for (PsiMethod method : methods) { update(method, 0); };
//    }

}