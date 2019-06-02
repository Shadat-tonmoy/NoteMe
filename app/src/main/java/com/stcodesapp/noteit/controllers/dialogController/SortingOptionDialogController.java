package com.stcodesapp.noteit.controllers.dialogController;

import android.util.Log;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView.SortingOptionDialogScreenView;
import com.stcodesapp.noteit.ui.views.screens.dialogScreen.SortingOptionDialogScreen;

public class SortingOptionDialogController implements SortingOptionDialogScreen.Listener {

    private TasksFactory tasksFactory;
    private SortingOptionDialogScreenView sortingOptionDialogScreenView;
    private Listener listener;

    public interface Listener{
        void onNoteTitleOptionSelected(int position);
        void onNoteTextOptionSelected(int position);
        void onNoteTimeOptionSelected(int position);
        void onNoteImportantOptionSelected(int position);
    }


    public SortingOptionDialogController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
    }

    public void bindView(SortingOptionDialogScreenView sortingOptionDialogScreenView) {
        this.sortingOptionDialogScreenView = sortingOptionDialogScreenView;
    }

    public void onStart()
    {
        sortingOptionDialogScreenView.registerListener(this);
    }

    public void onStop()
    {
        sortingOptionDialogScreenView.unregisterListener(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onNoteTitleOptionSelected(int position) {
        listener.onNoteTitleOptionSelected(position);

    }

    @Override
    public void onNoteTextOptionSelected(int position) {
        listener.onNoteTextOptionSelected(position);

    }

    @Override
    public void onNoteTimeOptionSelected(int position) {
        listener.onNoteTimeOptionSelected(position);

    }

    @Override
    public void onNoteImportantOptionSelected(int position) {
        listener.onNoteImportantOptionSelected(position);
    }
}
