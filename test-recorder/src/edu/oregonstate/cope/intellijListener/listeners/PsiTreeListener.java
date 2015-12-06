package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.psi.PsiTreeChangeAdapter;
import com.intellij.psi.PsiTreeChangeEvent;
import edu.oregonstate.cope.mActivity.PsiEvents;
import edu.oregonstate.cope.mActivity.mTracker;
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

    private mTracker tracker = mTracker.getInstance();

    @Override
    public void beforeChildrenChange(@NotNull PsiTreeChangeEvent psiEvent) {
        // is triggered on every small change of any file
        tracker.add(PsiEvents.BEFORE_CHANGE, psiEvent);
    }

    @Override
    public void beforeChildAddition(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.BEFORE_ADDITION, psiEvent);
    }

    @Override
    public void beforeChildRemoval(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.BEFORE_REMOVAL, psiEvent);
    }

    @Override
    public void beforeChildReplacement(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.BEFORE_REPLACEMENT, psiEvent);
    }

    @Override
    public void beforeChildMovement(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.BEFORE_MOVEMENT, psiEvent);
    }

    @Override
    public void beforePropertyChange(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.BEFORE_PROPERTY, psiEvent);
    }

    @Override
    public void childrenChanged(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.CHANGED, psiEvent);
    }

    @Override
    public void childAdded(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.ADDED, psiEvent);
    }

    @Override
    public void childMoved(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.MOVED, psiEvent);
    }

    @Override
    public void childRemoved(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.REMOVED, psiEvent);
    }

    @Override
    public void childReplaced(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.REPLACED, psiEvent);
    }

    @Override
    public void propertyChanged(@NotNull PsiTreeChangeEvent psiEvent) {
        tracker.add(PsiEvents.PROPERTY, psiEvent);
    }

}
