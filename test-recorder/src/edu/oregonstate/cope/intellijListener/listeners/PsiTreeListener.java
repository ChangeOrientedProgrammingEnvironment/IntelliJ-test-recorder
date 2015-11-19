package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.lang.ASTNode;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import edu.oregonstate.cope.core.NodeActivityTracker;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        PsiElement element = event.getChild();
        PsiFile file = event.getFile();
        PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class, false);

        ASTNode node = (element == null) ? null : element.getNode();
        String fileName = (file == null) ? "" : file.getName();
        String methodName = (method == null) ? "" : method.getName();
        String text = (element == null || element.getText() == null) ? "" : element.getText();
        text = (text.length() >= 10) ? text.substring(0,10): text;
        int prevSize = (node == null || node.getTreePrev() == null) ? 0 : node.getTreePrev().getChildren(null).length;
        int nextSize = (node == null || node.getTreeNext() == null) ? 0 : node.getTreeNext().getChildren(null).length;

        Date date = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("M/d/Y HH:mm:ss:SSS");
        String dateFormatted = formatter.format(date);

        System.out.println(dateFormatted + ": " + eventType);
        System.out.println("\tfile: " + fileName);
        System.out.println("\tmethod: " + methodName);
        System.out.println("\ttext: '" + text + "'");

        if (prevSize >= 1 || nextSize >= 1) {
            ASTNode[] prevNodes = node.getTreePrev().getChildren(null);

            System.out.println("\tprevNodes: (" + prevSize + ")");
            for (int i = 0; i < prevNodes.length; i++) {
                System.out.println("\t" + (i+1) + ": " + prevNodes[i].getElementType().toString());
            }
        } else {
            System.out.println("\tprevNodes: blank");
        }

        if (nextSize >= 1) {
            ASTNode[] nextNodes = node.getTreeNext().getChildren(null);

            System.out.println("\tnextNodes: (" + nextSize + ")");
            for (int i = 0; i < nextNodes.length; i++) {
                System.out.println("\t" + (i+1) + ": " + nextNodes[i].getElementType().toString());
            }
        } else {
            System.out.println("\tnextNodes: blank");
        }
    }

    private void recordActivity(@NotNull String eventType, @NotNull PsiTreeChangeEvent event) {
        PsiElement psiElement = event.getChild();
        PsiClass psiClass = PsiTreeUtil.getParentOfType(psiElement, PsiClass.class, false);
        PsiMethod psiMethod = PsiTreeUtil.getParentOfType(psiElement, PsiMethod.class, false);

        Date date = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("M/d/Y HH:mm:ss:SSS");
        String dateFormatted = formatter.format(date);

        System.out.println(dateFormatted + ": " + eventType);

        if (psiElement != null && psiMethod != null) {
            NodeActivityTracker tracker = NodeActivityTracker.getInstance();
            ASTNode astNode = psiElement.getNode();
            int modASTNodes = (astNode.getTreeNext() == null) ? 0 : astNode.getTreeNext().getChildren(null).length;
            tracker.update(psiMethod, modASTNodes);
            String location = psiClass.getName() + "." + psiMethod.getName();
            System.out.println("\tmethod activity: " + location + " - " + tracker.get(psiMethod));
        }
    }

    @Override
    public void childAdded(@NotNull PsiTreeChangeEvent event) {
        recordActivity("childAdded", event);
    }

    @Override
    public void childMoved(@NotNull PsiTreeChangeEvent event) {
        recordActivity("childMoved", event);
    }

    @Override
    public void childRemoved(@NotNull PsiTreeChangeEvent event) {
        recordActivity("childRemoved", event);
    }

    @Override
    public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
        recordActivity("childrenChanged", event);
    }

    @Override
    public void childReplaced(@NotNull PsiTreeChangeEvent event) {
        recordActivity("childReplaced", event);
    }

    @Override
    public void propertyChanged(@NotNull PsiTreeChangeEvent event) {
        PsiElement element = event.getChild();
        PsiFile file = event.getFile();
        PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class, false);

        String fileName = (file == null) ? "" : file.getName();
        String methodName = (method == null) ? "" : method.getName();
        String propertyName = (event.getPropertyName() == null) ? "" : event.getPropertyName();

        //System.out.println("6. property changed");
        //System.out.println("   property: " + propertyName);
        //System.out.println("   method: " + methodName);
        //System.out.println("   file: " + fileName);
    }

}
