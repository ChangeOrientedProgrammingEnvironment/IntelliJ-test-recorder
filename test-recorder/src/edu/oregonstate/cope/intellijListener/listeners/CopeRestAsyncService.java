package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import org.json.simple.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static edu.oregonstate.cope.intellijListener.listeners.RESTInterface.sampleRESTCall;

/**
 * @author Denis Bogdanas <bogdanad@oregonstate.edu>
 *         Created on 10/10/2015.
 */
public class CopeRestAsyncService extends ApplicationComponent.Adapter {

    private static final Logger LOG = Logger.getInstance(CopeRestAsyncService.class);
    public static final int SHUTDOWN_TIMEOUT = 2;

    private ExecutorService executor;

    public static CopeRestAsyncService getInstance() {
        return ApplicationManager.getApplication().getComponent(CopeRestAsyncService.class);
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    void submitJSON(JSONObject testJSON) {
        executor.submit((Runnable) () -> {
            sampleRESTCall(testJSON);
        });
    }

    @Override
    public void initComponent() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void disposeComponent() {
        try {
            executor.shutdown();
            executor.awaitTermination(SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error(e);
        } finally {
            if (!executor.isTerminated()) {
                LOG.error("Killing COPE executor service, with unfinished tasks");
            }
            executor.shutdownNow();
        }
    }
}
