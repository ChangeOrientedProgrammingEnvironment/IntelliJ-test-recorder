package edu.oregonstate.cope.core;

import com.intellij.psi.PsiMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 11/18/15.
 */
public final class NodeActivityTracker {

    private static NodeActivityTracker instance = null;
    private Map<String, Integer> methodMap = new HashMap<>();

    private NodeActivityTracker() {
        // Exists only to defeat instantiation.
    }

    public static NodeActivityTracker getInstance() {
        if(instance == null) {
            instance = new NodeActivityTracker();
        }
        return instance;
    }

    public Integer get(PsiMethod method) {
        return methodMap.get(method.getName());
    }

    public void update(PsiMethod method, Integer count) {
        String key = method.getName();
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