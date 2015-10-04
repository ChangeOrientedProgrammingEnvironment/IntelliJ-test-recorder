package edu.oregonstate.cope.settings;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;

/**
 * Persistent global settings object for COPE plugin.
 *
 * @author Denis Bogdanas <bogdanad@oregonstate.edu>
 *         Created on 10/3/2015.
 */
@State(
        name = "cope.settings", reloadable = false,
        storages = @Storage(id = "other", file = StoragePathMacros.APP_CONFIG + "/cope.settings.xml")
)
public class CopeGlobalSettings implements PersistentStateComponent<CopeGlobalSettings> {

    public String url = "http://localhost:3000/loopback/testPost";
    public String userId;

    public static CopeGlobalSettings getInstance() {
        return ServiceManager.getService(CopeGlobalSettings.class);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public CopeGlobalSettings getState() {
        return this;
    }

    @Override
    public void loadState(CopeGlobalSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
