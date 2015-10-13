package edu.oregonstate.cope.settings;

import com.google.common.base.Strings;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.Messages;
import edu.oregonstate.cope.intellijListener.listeners.RESTInterface;

import javax.swing.*;
import java.io.IOException;

/**
 * UI form for configuring {@link CopeGlobalSettings}.
 *
 * @author Denis Bogdanas <bogdanad@oregonstate.edu>
 *         Created on 10/3/2015.
 */
public class CopeSettingsPanel {

    private static final Logger LOG = Logger.getInstance(CopeSettingsPanel.class);

    JPanel panel;
    JTextField serverUrlTextField;
    JTextField userIdTextField;
    private JButton testButton;

    public CopeSettingsPanel() {
        testButton.addActionListener(e -> {
            try {
                String response = RESTInterface.testConnection(serverUrlTextField.getText());
                LOG.info("Test connection successful: " + response);
                Messages.showInfoMessage(panel, "Connection successful: " + response, "Success");
            } catch (IOException ex) {
                LOG.info("Test connection failed", ex);
                Messages.showErrorDialog(panel, "Connection failure: "
                        + (Strings.isNullOrEmpty(ex.getMessage()) ? ex.getClass().getSimpleName() : ex.getMessage()),
                        "Connection Failure");
            } catch (IllegalArgumentException ex) {
                LOG.info("Invalid URL", ex);
                Messages.showErrorDialog(panel, "Invalid URL: " + ex.getMessage(), "Invalid URL");
            }
        });
    }
}
