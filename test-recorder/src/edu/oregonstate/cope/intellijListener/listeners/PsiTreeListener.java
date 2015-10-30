package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTreeChangeAdapter;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 10/28/15.
 *
 * Note: This listener is not instantiated through hooks exposed in META-INF/PlatformExtensionPoints.xml. Since no
 * hooks are provided to PsiManager or PsiTreeChangeAdapter, they must be instantiated in PluginComponent.
 */
public class PsiTreeListener extends PsiTreeChangeAdapter {

    private void logEvent(@NotNull String eventType, PsiTreeChangeEvent event) {
        String fileName = (event.getFile() == null) ? "" : event.getFile().getName();
        String propertyName = (event.getPropertyName() == null) ? "" : event.getPropertyName();
        String oldValue = (event.getOldValue() == null) ? "" : event.getOldValue().toString();
        String newValue = (event.getNewValue() == null) ? "" : event.getNewValue().toString();

        System.out.println(eventType + " event on PsiTreeChangeAdapter invoked on:");
        if (!fileName.isEmpty()) System.out.println("   file: " + fileName);
        if (!propertyName.isEmpty()) System.out.println("   property: " + propertyName);
        if (!oldValue.isEmpty()) System.out.println("   old value: " + oldValue);
        if (!newValue.isEmpty()) System.out.println("   new value: " + newValue);

        if (event.getFile() != null) {
            if (event.getFile().getNode() != null) {
                System.out.println("nodeElementType: " + event.getFile().getNode().getElementType().toString());
            }
        }
    }

    @Override
    public void childAdded(@NotNull PsiTreeChangeEvent event) {
        PsiElement element = event.getChild();
        PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class, false);
        String methodName = (method == null) ? "" : method.getName();
        System.out.println("child added to method: " + methodName);
    }

    @Override
    public void childMoved(@NotNull PsiTreeChangeEvent event) {
        PsiElement element = event.getChild();
        PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class, false);
        String methodName = (method == null) ? "" : method.getName();
        System.out.println("child moved in method: " + methodName);
    }

    @Override
    public void childRemoved(@NotNull PsiTreeChangeEvent event) {
        logEvent("childRemoved", event);
    }

    @Override
    public void childrenChanged(@NotNull PsiTreeChangeEvent event) { logEvent("childrenChanged", event); }

    @Override
    public void childReplaced(@NotNull PsiTreeChangeEvent event) {
        logEvent("childReplaced", event);
    }

    @Override
    public void propertyChanged(@NotNull PsiTreeChangeEvent event) { logEvent("childMoved", event); }

}
