package edu.oregonstate.cope.intellijListener.listeners;

import com.google.gson.Gson;
import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.BeforeRunTaskProvider;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jetbrains.annotations.Nullable;
import org.json.XML;
import org.json.simple.JSONObject;

import java.util.List;


/**
 * Created by michaelhilton on 10/2/15.
 */
public class COPEBeforeRunTaskProvider extends BeforeRunTaskProvider<COPEBeforeRunTask> {

    public static final String EXTENSION_NAME = "COPE Run Recorder";

    private Key<COPEBeforeRunTask> launchProvider = new Key<COPEBeforeRunTask>("edu.oregonstate.cope.intellij.launchprovider");

    public COPEBeforeRunTaskProvider() {
    }

    @Override
    public Key<COPEBeforeRunTask> getId() {
        return launchProvider;
    }

    @Override
    public String getName() {
        return EXTENSION_NAME;
    }

    @Override
    public String getDescription(COPEBeforeRunTask task) {
        return "COPE Run Recorder";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Nullable
    @Override
    public COPEBeforeRunTask createTask(RunConfiguration runConfiguration) {
        return new COPEBeforeRunTask(launchProvider);
    }

    @Override
    public boolean configureTask(RunConfiguration runConfiguration, COPEBeforeRunTask task) {

        return true;
    }

    @Override
    public boolean canExecuteTask(RunConfiguration configuration, COPEBeforeRunTask task) {

        return true;
    }

    @Override
    public boolean executeTask(DataContext context, final RunConfiguration configuration, ExecutionEnvironment env, COPEBeforeRunTask task) {
        Element element = new Element("launchRecording");
        String executionType = env.getExecutor().getStartActionText().toString();
        try {
            configuration.writeExternal(element);
            String xmlString = new XMLOutputter().outputString(element);
            org.json.JSONObject soapDatainJsonObject = XML.toJSONObject(xmlString);
            JSONObject obj=new JSONObject();
            obj.put("evn",executionType);
            obj.put("element",soapDatainJsonObject);
            System.out.println(obj);

        } catch (WriteExternalException e) {
            e.printStackTrace();
        }
        return true;
    }
}
