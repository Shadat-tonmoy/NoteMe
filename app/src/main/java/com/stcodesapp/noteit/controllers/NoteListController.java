package com.stcodesapp.noteit.controllers;

import android.os.Bundle;
import android.util.Log;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.ui.screenViews.NoteListScreenView;
import com.stcodesapp.noteit.ui.screens.NoteListScreen;

public class NoteListController implements NoteListScreen.Listener {

    private final TasksFactory tasksFactory;
    private NoteListScreenView noteListScreenView;

    public NoteListController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
    }

    public void bindView(NoteListScreenView noteListScreenView) {
        this.noteListScreenView = noteListScreenView;
    }

    public void onStart()
    {
        noteListScreenView.registerListener(this);
    }

    public void onStop()
    {
        noteListScreenView.unregisterListener(this);
    }

    @Override
    public void onNoteAddFabClick() {
        tasksFactory.getScreenNavigationTasks().toNoteFieldScreen(new Bundle());
    }
}
