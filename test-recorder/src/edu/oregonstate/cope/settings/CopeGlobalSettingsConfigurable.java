package edu.oregonstate.cope.settings;

import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

/**
 * Configuration interface for {@link CopeGlobalSettings}.
 *
 * @author Denis Bogdanas <bogdanad@oregonstate.edu>
 *         Created on 10/3/2015.
 */
public class CopeGlobalSettingsConfigurable implements SearchableConfigurable {

    protected CopeGlobalSettings globalSettings;
    protected CopeSettingsPanel settingsPanel;

    public CopeGlobalSettingsConfigurable() {
        globalSettings = CopeGlobalSettings.getInstance();
    }

    @NotNull
    @Override
    public String getId() {
        return "COPE";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return getId();
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        if (settingsPanel == null) settingsPanel = new CopeSettingsPanel();
        reset();
        return settingsPanel.panel;
    }

    @Override
    public void disposeUIResources() {
        settingsPanel = null;
    }

    @Override
    public boolean isModified() {
        return settingsPanel == null
                || !Objects.equals(globalSettings.getUrl(), settingsPanel.serverUrlTextField.getText())
                || !Objects.equals(globalSettings.getUserId(), settingsPanel.userIdTextField.getText());
    }

    @Override
    public void apply() {
        if (settingsPanel != null) {
            globalSettings.setUrl(settingsPanel.serverUrlTextField.getText());
            globalSettings.setUserId(settingsPanel.userIdTextField.getText());
        }
    }

    @Override
    public void reset() {
        if (settingsPanel != null) {
            settingsPanel.serverUrlTextField.setText(globalSettings.getUrl());
            settingsPanel.userIdTextField.setText(globalSettings.getUserId());
        }
    }
}
