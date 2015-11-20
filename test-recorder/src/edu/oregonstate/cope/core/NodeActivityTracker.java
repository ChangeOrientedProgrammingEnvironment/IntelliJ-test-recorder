package edu.oregonstate.cope.core;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public Integer get(PsiClass psiClass, PsiMethod psiMethod) {
        String qualifiedMethod = psiClass.getQualifiedName() + psiMethod.getName();
        return methodMap.get(qualifiedMethod);
    }

    public Integer get(String qualifiedMethod) { return methodMap.get(qualifiedMethod); }

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