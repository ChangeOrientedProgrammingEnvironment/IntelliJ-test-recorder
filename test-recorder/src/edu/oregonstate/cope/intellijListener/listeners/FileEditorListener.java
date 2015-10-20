package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.PsiShortNamesCache;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

/**
 * Created by nelsonni on 10/19/15.
 */
public class FileEditorListener implements FileEditorProvider {

    @Override
    public boolean accept(Project project, VirtualFile virtualFile) {
        int count = 0;
        System.out.println("accept project: " + project.getName());
        for (String s : PsiShortNamesCache.getInstance(project).getAllClassNames()) {
            if (s.compareTo("constructQualifiedName") == 0) {
                System.out.println("class: " + s);
            }
            count += 1;
        }
        System.out.println("classes in project: " + count);
        return false;
    }

    @NotNull
    @Override
    public FileEditor createEditor(Project project, VirtualFile virtualFile) {
        System.out.println("createEditor project: " + project.getName());
        return null;
    }

    @Override
    public void disposeEditor(@NotNull FileEditor fileEditor) {
    }

    @NotNull
    @Override
    public FileEditorState readState(@NotNull Element element, @NotNull Project project, @NotNull VirtualFile virtualFile) {
        System.out.println("readState project: " + project.getName());
        return null;
    }

    @Override
    public void writeState(@NotNull FileEditorState fileEditorState, @NotNull Project project, @NotNull Element element) {
        System.out.println("writeState project: " + project.getName());
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return "COPE-monitor";
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return null;
    }
}
