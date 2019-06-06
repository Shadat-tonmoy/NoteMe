package com.stcodesapp.noteit.controllers.activityController;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.ManualEmailScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.ClipboardTasks;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.ManualEmailScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.ManualEmailScreen;

public class ManualEmailActivityController implements ManualEmailScreen.Listener {

    private TasksFactory tasksFactory;
    private ManualEmailScreen manualEmailScreenView;
    private ManualEmailScreenManipulationTasks manualEmailScreenManipulationTasks;
    private ClipboardTasks clipboardTasks;


    public ManualEmailActivityController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        this.manualEmailScreenManipulationTasks = tasksFactory.getManualEmailScreenManipulationTasks();
        this.clipboardTasks = tasksFactory.getClipboardTasks();
    }


    public void bindView(ManualEmailScreen manualEmailScreenView) {
        this.manualEmailScreenView = manualEmailScreenView;
        manualEmailScreenManipulationTasks.bindView(manualEmailScreenView);
    }

    public void onStart()
    {
        manualEmailScreenView.registerListener(this);
    }

    public void onStop()
    {
        manualEmailScreenView.unregisterListener(this);
    }

    public void onPostCreate()
    {
        manualEmailScreenManipulationTasks.initToolbar();
    }

    @Override
    public void onDoneButtonClicked() {
        clipboardTasks.hideKeyBoard();
        if(manualEmailScreenManipulationTasks.sendResultBack())
            manualEmailScreenManipulationTasks.closeScreen();
        else manualEmailScreenManipulationTasks.showNoEmailAddressMessage();

    }

    @Override
    public void onNavigateUp() {
        clipboardTasks.hideKeyBoard();
        manualEmailScreenManipulationTasks.closeScreen();

    }
}
