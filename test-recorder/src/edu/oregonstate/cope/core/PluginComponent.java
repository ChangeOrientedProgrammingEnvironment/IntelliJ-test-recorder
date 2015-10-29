package edu.oregonstate.cope.core;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiTreeChangeAdapter;
import edu.oregonstate.cope.intellijListener.listeners.PsiTreeListener;
import org.jetbrains.annotations.NotNull;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 10/2/15.
 */
public class PluginComponent implements ProjectComponent {

    private Project project;
    PluginStatusBar statusbar;
    private final PsiManager psiManager;

    public PsiTreeChangeAdapter listener;

    public PluginComponent(Project project, PsiManager psiManager) {
        this.psiManager = psiManager;
        this.project = project;
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "PluginComponent";
    }

    public void projectOpened() {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);

        System.out.println("adding PsiTreeChangeListener");
        psiManager.addPsiTreeChangeListener(listener = new PsiTreeListener());

        if (statusBar != null) {
            statusbar = new PluginStatusBar();
            statusBar.addWidget(statusbar);
        }
    }

    public void projectClosed() {
        if (listener != null) {
            System.out.println("removing PsiTreeChangeListener");
            psiManager.removePsiTreeChangeListener(listener);
        }
    }
}
