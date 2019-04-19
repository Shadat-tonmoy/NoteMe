package com.stcodesapp.noteit.controllers.activityController;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.ManualContactScreenManipulationTasks;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.ManualContactScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.ManualContactScreen;

public class ManualContactController implements ManualContactScreen.Listener {


    private TasksFactory tasksFactory;
    private ManualContactScreenView manualContactScreenView;
    private ManualContactScreenManipulationTasks manualContactScreenManipulationTasks;
    private ActivityNavigationTasks activityNavigationTasks;

    public ManualContactController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        this.manualContactScreenManipulationTasks= tasksFactory.getManualContactScreenManipulationTasks();
        this.activityNavigationTasks = tasksFactory.getActivityNavigationTasks();
    }

    public void bindView(ManualContactScreenView manualContactScreenView) {
        this.manualContactScreenView = manualContactScreenView;
        manualContactScreenManipulationTasks.bindView(manualContactScreenView);

    }

    public void onStart()
    {
        manualContactScreenView.registerListener(this);

    }

    public void onStop()
    {
        manualContactScreenView.unregisterListener(this);

    }

    public void onDestroy()
    {

    }

    public  void onPostCreate()
    {
        manualContactScreenManipulationTasks.initToolbar();

    }


    @Override
    public void onDoneButtonClicked() {

    }

    @Override
    public void onNavigateUp() {
        activityNavigationTasks.closeScreen();
    }
}
