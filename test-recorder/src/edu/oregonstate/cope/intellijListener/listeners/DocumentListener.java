package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by nelsonni on 10/19/15.
 */
public class DocumentListener implements FileDocumentManagerListener {
    @Override
    public void beforeAllDocumentsSaving() {
        System.out.println("before all documents saving");
    }

    @Override
    public void beforeDocumentSaving(@NotNull Document document) {
        System.out.println("before document saving");
    }

    @Override
    public void beforeFileContentReload(VirtualFile virtualFile, @NotNull Document document) {
        System.out.println("before file content reloaded: " + virtualFile.getName());
    }

    @Override
    public void fileWithNoDocumentChanged(@NotNull VirtualFile virtualFile) {
        System.out.println("file with no document changed: " + virtualFile.getName());
    }

    @Override
    public void fileContentReloaded(@NotNull VirtualFile virtualFile, @NotNull Document document) {
        System.out.println("file content reloaded: " + virtualFile.getName());
    }

    @Override
    public void fileContentLoaded(@NotNull VirtualFile virtualFile, @NotNull Document document) {
        System.out.println("file content loaded: " + virtualFile.getName());
    }

    @Override
    public void unsavedDocumentsDropped() {
        System.out.println("unsaved documents dropped");
    }
}
