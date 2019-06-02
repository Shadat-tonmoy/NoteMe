package com.stcodesapp.noteit.controllers.dialogController;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView.SortingOptionDialogScreenView;
import com.stcodesapp.noteit.ui.views.screens.dialogScreen.SortingOptionDialogScreen;

public class SortingOptionDialogController implements SortingOptionDialogScreen.Listener {

    private TasksFactory tasksFactory;
    private SortingOptionDialogScreenView sortingOptionDialogScreenView;


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
}
