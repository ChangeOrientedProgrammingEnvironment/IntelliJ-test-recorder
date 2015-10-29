package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.psi.PsiTreeChangeAdapter;
import com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 10/28/15.
 */
public class PsiTreeListener extends PsiTreeChangeAdapter {

    private void logEvent(@NotNull String eventType, PsiTreeChangeEvent event) {
        String fileName;

        if (event.getFile() == null) {
            fileName = "[not a file event]";
        } else {
            fileName = event.getFile().getName();
        }

        System.out.println(eventType + " event on PsiTreeChangeAdapter invoked on " + fileName);
    }

    @Override
    public void childAdded(@NotNull PsiTreeChangeEvent event) {
        logEvent("childAdded", event);
    }

    @Override
    public void childMoved(@NotNull PsiTreeChangeEvent event) {
        logEvent("childMoved", event);
    }

    @Override
    public void childRemoved(@NotNull PsiTreeChangeEvent event) {
        logEvent("childRemoved", event);
    }

    @Override
    public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
        logEvent("childrenChanged", event);
    }

    @Override
    public void childReplaced(@NotNull PsiTreeChangeEvent event) {
        logEvent("childReplaced", event);
    }

    @Override
    public void propertyChanged(@NotNull PsiTreeChangeEvent event) {
        logEvent("propertyChanged", event);
    }

}
