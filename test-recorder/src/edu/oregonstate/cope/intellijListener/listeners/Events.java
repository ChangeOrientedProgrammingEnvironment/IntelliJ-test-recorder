package edu.oregonstate.cope.intellijListener.listeners;

public enum Events {
	testConnection,testRun,
	//currently unsupported
	debugLaunch, normalLaunch, fileOpen, fileClose, textChange, refresh, snapshot, fileSave, launchEnd, refactoringLaunch,
	refactoringUndo, refactoringEnd, copy, resourceAdded, resourceRemoved, gitEvent, externalLibraryAdd,
}