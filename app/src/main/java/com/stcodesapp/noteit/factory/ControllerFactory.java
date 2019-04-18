package com.stcodesapp.noteit.factory;

import com.stcodesapp.noteit.controllers.HomeScreenController;
import com.stcodesapp.noteit.controllers.activityController.MainActivityController;
import com.stcodesapp.noteit.controllers.activityController.NoteFieldController;
import com.stcodesapp.noteit.controllers.commons.NavigationDrawerController;

public class ControllerFactory {

    private TasksFactory tasksFactory;

    public ControllerFactory(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
    }

    public HomeScreenController getHomeScreenController()
    {
        return new HomeScreenController(tasksFactory);
    }

    public MainActivityController getMainActivityController()
    {
        return new MainActivityController(tasksFactory);
    }


    public NoteFieldController getSecondActivityController()
    {
        return new NoteFieldController(tasksFactory);
    }

    public NavigationDrawerController getNavigationDrawerController()
    {
        return new NavigationDrawerController(tasksFactory.getFragmentNavigationTasks());
    }
}
