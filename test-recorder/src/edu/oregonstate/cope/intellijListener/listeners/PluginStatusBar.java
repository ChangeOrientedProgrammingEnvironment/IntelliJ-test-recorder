package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Created by nelsonni on 10/2/15.
 */
public class PluginStatusBar implements StatusBarWidget, StatusBarWidget.IconPresentation {

    private final Boolean updateReady;

    public PluginStatusBar(Boolean updateReady) {
        this.updateReady = updateReady;
    }

    @Override
    public void dispose() {

    }

    @NotNull
    @Override
    public Icon getIcon() {
        if(updateReady){
            Icon test = IconLoader.getIcon("copeLogo_UpdateReady.png");
            return test;
        }else{
            Icon test = IconLoader.getIcon("copeLogo.png");
            return test;
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
        if(updateReady) {
            return new Consumer<MouseEvent>() {
                public void consume(MouseEvent mouseEvent) {
                    Messages.showMessageDialog("Your version of COPE is out of date.  Please update your plugin!", "COPE", Messages.getInformationIcon());
                }
            };
        }else{
            return null;
        }
    }
}
