package edu.oregonstate.cope.intellijListener.listeners;

import edu.oregonstate.cope.settings.PluginStatusBar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by nelsonni on 10/12/15.
 */
public class PluginStatusBarTest {

    private PluginStatusBar statusbar;

    @Before
    public void setUp() throws Exception {
        statusbar = new PluginStatusBar();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDispose() throws Exception {

    }

    /**
     * Verify that PluginStatusBar displays an Object icon both when updates are available, and
     * when there are no updates.
     * @throws Exception
     */
    @Test
    public void testGetIcon() throws Exception {
        assertThat(statusbar.getIcon(), instanceOf(Icon.class));
    }

    @Test
    public void testID() throws Exception {
        assertEquals(statusbar.ID(), "COPE STATUS ID");
    }

    @Test
    public void testGetPresentation() throws Exception {

    }

    @Test
    public void testInstall() throws Exception {

    }

    @Test
    public void testGetTooltipText() throws Exception {

    }

    @Test
    public void testGetClickConsumer() throws Exception {

    }
}