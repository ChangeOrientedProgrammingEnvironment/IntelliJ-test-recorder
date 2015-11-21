package edu.oregonstate.cope.core;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiTreeChangeAdapter;
import com.intellij.util.messages.MessageBusConnection;
import edu.oregonstate.cope.intellijListener.listeners.FileEditorListener;
import edu.oregonstate.cope.intellijListener.listeners.PsiTreeListener;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 10/2/15.
 */
public class PluginComponent implements ProjectComponent {

    private Project project;
    private final PsiManager psiManager;
    private final MessageBusConnection msgBus;

    public PsiTreeChangeAdapter psiListener;
    public FileEditorListener fileListener;

    private Timer timer;
    PluginStatusBar statusbar;

    public PluginComponent(Project project, PsiManager psiManager) {
        this.psiManager = psiManager;
        this.project = project;
        this.msgBus = project.getMessageBus().connect(project);
        this.timer = new Timer();
    }

    public void initComponent() {
        System.out.println("adding FileEditorManagerListener");
        msgBus.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, fileListener = new FileEditorListener());

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                NodeActivityTracker tracker = NodeActivityTracker.getInstance();
                System.out.println("methods: " + tracker.getAll());
            }
        }, 1*15*1000, 1*15*1000);
    }

    public void disposeComponent() {
        if (fileListener != null) {
            System.out.println("removing FileEditorManagerListener");
            msgBus.disconnect();
        }
        timer.cancel();
    }

    @NotNull
    public String getComponentName() {
        return "COPE Plugin Component";
    }

    public void projectOpened() {
        System.out.println("adding PsiTreeChangeListener");
        psiManager.addPsiTreeChangeListener(psiListener = new PsiTreeListener());

        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
        if (statusBar != null) {
            statusbar = new PluginStatusBar();
            statusBar.addWidget(statusbar);
        }
    }

    public void projectClosed() {
        if (psiListener != null) {
            System.out.println("removing PsiTreeChangeListener");
            psiManager.removePsiTreeChangeListener(psiListener);
        }

        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
        statusBar.removeWidget(statusbar.ID());
    }
}
