package edu.oregonstate.cope.mActivity;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

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
    private Map<String, Integer> methodMap = new HashMap<>();

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

    /**
     * Adds new event to the end of eventLog and updates methodMap accordingly
     * @param event Encapsulated PsiTreeChangeEvent that includes timestamp and context information
     * @return TRUE if event contains a complete set of file/class/method names, else FALSE
     */
    public Boolean addEvent(@NotNull mEvent event) {
        eventLog.add(event);

        if (event.getSimpleFileName() != null &&
                 event.getClassName() != null &&
                event.getMethodName() != null) {
            String key = event.getSimpleFileName() + "." + event.getClassName() + "." + event.getMethodName();
            Integer count = methodMap.getOrDefault(key, 0);
            methodMap.remove(key);
            methodMap.put(key, (count + 1));

            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public Integer get(PsiClass psiClass, PsiMethod psiMethod) {
        String filename = FilenameUtils.removeExtension(psiClass.getContainingFile().getName());
        String key = filename + "." + psiClass.getName() + "." + psiMethod.getName();
        return methodMap.get(key);
    }

    @NotNull
    public Set getAll() { return methodMap.entrySet(); }

    public void update(String key, Integer count) {
        Integer old = methodMap.getOrDefault(key, 0);
        methodMap.remove(key);
        methodMap.put(key, (old + count));
    }

//    public void collectMethods(Project project, VirtualFile vFile) {
//        PsiFile pfile = PsiManager.getInstance(project).findFile(vFile);
//        PsiElement root = pfile.findElementAt(0);
//
//        Collection<PsiMethod> methods = PsiTreeUtil.findChildrenOfType(root, PsiMethod.class);
//        for (PsiMethod method : methods) { update(method, 0); };
//    }

}