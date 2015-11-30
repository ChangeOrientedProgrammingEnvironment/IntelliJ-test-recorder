package edu.oregonstate.cope.mActivity;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.util.PsiTreeUtil;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 11/20/15.
 */
public class mEvent {

    @NotNull private String psiEventType;
    @NotNull private final Date timestamp;
    @Nullable private final ASTNode node;
    @Nullable private final String simpleFileName;
    @Nullable private final String qualifiedFileName;
    @Nullable private final String className;
    @Nullable private final String methodName;

    public mEvent(@NotNull PsiTreeChangeEvent event, String eventType) {
        this(new Date(System.currentTimeMillis()), event, eventType);
    }

    public mEvent(@NotNull Date timestamp, @NotNull PsiTreeChangeEvent event, @NotNull String eventType) {
        PsiElement element = event.getChild();
        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class, false);
        PsiMethod psiMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class, false);

        this.psiEventType = eventType;
        this.timestamp = timestamp;
        this.node = (element == null) ? null : element.getNode();
        this.simpleFileName = (event.getFile() == null) ? null : FilenameUtils.removeExtension(event.getFile().getName());
        this.qualifiedFileName = (event.getFile() == null) ? null : event.getFile().getVirtualFile().getCanonicalPath();
        this.className = (psiClass == null) ? "" : psiClass.getName();
        this.methodName = (psiMethod == null) ? "" : psiMethod.getName();
    }

    public void print() {
        System.out.println(getTimestamp() + ": " + psiEventType);
        System.out.println("\tfile: " + simpleFileName);
        System.out.println("\tclass: " + className);
        System.out.println("\tmethod: " + methodName);

        if (node != null) {
            System.out.println("\ttype: " + node.getElementType().toString());
            System.out.println("\ttext: '" + node.getText() + "'");
            System.out.println("\ttextOffset: " + node.getStartOffset());

            if (node.getTreePrev() != null) {
                System.out.println("\tbeforeTree count: " + node.getTreePrev().getChildren(null).length);
            } else {
                System.out.println("\tbeforeTree count: (blank)");
            }
            if (node.getTreeNext() != null) {
                System.out.println("\tafterTree count: " + node.getTreeNext().getChildren(null).length);
            } else {
                System.out.println("\tafterTree count: (blank)");
            }
            if (node.getTreeParent() != null) {
                System.out.println("\tparentTree count: " + node.getTreeParent().getChildren(null).length);
            } else {
                System.out.println("\tparentTree count: (blank)");
            }
        } else {
            System.out.println("\ttype: (blank)");
            System.out.println("\ttext: (blank)");
        }

    }

    @NotNull
    public String getPsiEventType() {
        return this.psiEventType;
    }

    public String getTimestamp() {
        DateFormat formatter = new SimpleDateFormat("Y/M/d HH:mm:ss:SSS");
        return this.getTimestamp(formatter);
    }

    public String getTimestamp(DateFormat formatter) {
        return formatter.format(this.timestamp);
    }

    @Nullable
    public ASTNode getNode() { return this.node; }

    @Nullable
    public String getSimpleFileName() {
        return this.simpleFileName;
    }

    @Nullable
    public String getQualifiedFileName() {
        return this.qualifiedFileName;
    }

    @Nullable
    public String getClassName() {
        return this.className;
    }

    @Nullable
    public String getMethodName() {
        return this.methodName;
    }

}
