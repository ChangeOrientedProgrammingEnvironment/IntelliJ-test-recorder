package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

/**
 * Created by nelsonni on 10/2/15.
 */
public class PluginComponent implements ProjectComponent {

    private Project project;
    PluginStatusBar statusbar;

    public PluginComponent(Project project) {
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

        if (statusBar != null) {
            statusbar = new PluginStatusBar();
            statusBar.addWidget(statusbar);
        }
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
