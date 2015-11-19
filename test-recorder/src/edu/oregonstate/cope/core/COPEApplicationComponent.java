package edu.oregonstate.cope.core;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 11/18/15.
 */
public class COPEApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent() {
        System.out.println("COPEApplicationComponent: initComponent");
    }

    @Override
    public void disposeComponent() {
        System.out.println("COPEApplicationComponent: disposeComponent");
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "COPE Application Component";
    }
}
