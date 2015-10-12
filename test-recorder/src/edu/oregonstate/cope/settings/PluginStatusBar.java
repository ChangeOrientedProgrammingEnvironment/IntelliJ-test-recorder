package edu.oregonstate.cope.settings;

import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Created by nelsonni on 10/2/15.
 */
public class PluginStatusBar implements StatusBarWidget, StatusBarWidget.IconPresentation {

    public enum State {
        ACTIVE, INACTIVE, FAULT
    }
    private final State state;
    private static final Icon PLUGIN_ACTIVE_DARK_ICON = IconLoader.getIcon("resources/logo_active_dark.png");
    private static final Icon PLUGIN_ACTIVE_LITE_ICON = IconLoader.getIcon("resources/logo_active_lite.png");
    private static final Icon PLUGIN_INACTIVE_DARK_ICON = IconLoader.getIcon("resources/logo_inactive_dark.png");
    private static final Icon PLUGIN_INACTIVE_LITE_ICON = IconLoader.getIcon("resources/logo_inactive_lite.png");
    private static final Icon PLUGIN_FAULT_DARK_ICON = IconLoader.getIcon("resources/logo_fault_dark.png");
    private static final Icon PLUGIN_FAULT_LITE_ICON = IconLoader.getIcon("resources/logo_fault_lite.png");

    public PluginStatusBar() {
        this(State.ACTIVE);
    }

    public PluginStatusBar(State state) {
        this.state = state;
    }

    @Override
    public void dispose() {

    }

    @NotNull
    @Override
    public Icon getIcon() {
        switch (state) {
            case INACTIVE:
                if (UIUtil.isUnderDarcula()) {
                    return PLUGIN_INACTIVE_LITE_ICON;
                } else {
                    return PLUGIN_INACTIVE_DARK_ICON;
                }
            case FAULT:
                if (UIUtil.isUnderDarcula()) {
                    return PLUGIN_FAULT_LITE_ICON;
                } else {
                    return PLUGIN_FAULT_DARK_ICON;
                }
            case ACTIVE:
                // continue to default
            default:
                if (UIUtil.isUnderDarcula()) {
                    return PLUGIN_ACTIVE_LITE_ICON;
                } else {
                    return PLUGIN_ACTIVE_DARK_ICON;
                }
        }
    }

    @NotNull
    @Override
    public String ID() {
        return "COPE STATUS ID";
    }

    @Nullable
    @Override
    public WidgetPresentation getPresentation(@NotNull PlatformType platformType) {
        return this;
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {

    }

    @Nullable
    @Override
    public String getTooltipText() {
        return null;
    }

    @Nullable
    @Override
    public Consumer<MouseEvent> getClickConsumer() {
        if(state == State.ACTIVE) return mouseEvent -> ShowSettingsUtil.getInstance().showSettingsDialog(null, "COPE");
        else { return null; }
    }
}
