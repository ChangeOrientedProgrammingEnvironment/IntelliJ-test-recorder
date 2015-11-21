package edu.oregonstate.cope.core;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.util.PsiTreeUtil;
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
public class ActivityEvent {

    @Nullable
    private final ASTNode node;
    private final Date timestamp;
    private final String simpleFileName;
    private final String qualifiedFileName;
    private final String className;
    private final String methodName;

    public ActivityEvent(@NotNull PsiTreeChangeEvent event) {
        this(new Date(System.currentTimeMillis()), event);
    }

    public ActivityEvent(@NotNull Date timestamp, @NotNull PsiTreeChangeEvent event) {
        PsiElement element = event.getChild();
        @Nullable
        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class, false);
        @Nullable
        PsiMethod psiMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class, false);

        this.timestamp = timestamp;
        this.node = (element == null) ? null : element.getNode();
        this.simpleFileName = (event.getFile() == null) ? null : event.getFile().getName();
        this.qualifiedFileName = (event.getFile() == null) ? null : event.getFile().getVirtualFile().getCanonicalPath();
        this.className = (psiClass == null) ? "" : psiClass.getName();
        this.methodName = (psiMethod == null) ? "" : psiMethod.getName();
    }

    public String getTimestamp() {
        DateFormat formatter = new SimpleDateFormat("M/d/Y HH:mm:ss:SSS");
        return formatter.format(this.timestamp);
    }

    public String getTimestamp(DateFormat formatter) {
        return formatter.format(this.timestamp);
    }

    @Nullable
    public String getSimpleFileName() {
        return this.simpleFileName;
    }

    @Nullable
    public String getQualifiedFileName() {
        return this.qualifiedFileName;
    }

    public String getClassName() {
        return this.className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public int getNodeCount() {
        if (node != null) {
            return node.getChildren(null).length;
        } else {
            return 0;
        }
    }

}
