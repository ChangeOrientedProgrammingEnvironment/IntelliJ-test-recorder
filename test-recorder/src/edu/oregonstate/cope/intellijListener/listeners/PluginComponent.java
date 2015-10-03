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
    PluginStatusBar status;

    public PluginComponent(Project project) {
        this.project = project;
    }

    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "PluginComponent";
    }

    public void projectOpened() {
        Boolean updateReady = Boolean.TRUE;
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);

        if (statusBar != null) {
            status = new PluginStatusBar(updateReady);
            statusBar.addWidget(status);
        }
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
