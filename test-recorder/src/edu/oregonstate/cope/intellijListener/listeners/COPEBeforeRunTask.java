package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.execution.BeforeRunTask;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;

/**
 * Created by michaelhilton on 10/2/15.
 */
public class COPEBeforeRunTask extends BeforeRunTask<COPEBeforeRunTask> {
    protected COPEBeforeRunTask(@NotNull Key<COPEBeforeRunTask> providerId) {
        super(providerId);
        setEnabled(true);
    }
}
