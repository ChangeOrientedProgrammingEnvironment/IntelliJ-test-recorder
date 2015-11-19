package edu.oregonstate.cope.intellijListener.listeners;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Change-Oriented Programming Environment (COPE) project
 * URL: http://cope.eecs.oregonstate.edu/
 * Created by nelsonni on 11/18/15.
 */
public class FileEditorListener extends FileEditorManagerAdapter {

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        if (event.getOldFile() == null && event.getNewFile() == null) {
            System.out.println("selectionChanged: context switching without old or new file");
        } else if (event.getOldFile() == null) {
            System.out.println("selectionChanged: newly opened file " + event.getNewFile().getName());
        } else if (event.getNewFile() == null) {
            System.out.println("selectionChanged: closed file " + event.getOldFile().getName());
        } else {
            String oldFile = event.getOldFile().getName();
            String newFile = event.getNewFile().getName();
            System.out.println("selectionChanged: switched from " + oldFile + " to " + newFile);
        }
    }

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        System.out.println("fileOpened: " + file.getName());
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        System.out.println("fileClosed: " + file.getName());
    }
}