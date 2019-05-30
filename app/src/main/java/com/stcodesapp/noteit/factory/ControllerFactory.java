package com.stcodesapp.noteit.factory;

import com.stcodesapp.noteit.controllers.HomeScreenController;
import com.stcodesapp.noteit.controllers.activityController.MainActivityController;
import com.stcodesapp.noteit.controllers.activityController.ManualContactController;
import com.stcodesapp.noteit.controllers.activityController.ManualEmailActivityController;
import com.stcodesapp.noteit.controllers.activityController.NoteFieldController;
import com.stcodesapp.noteit.controllers.commons.NavigationDrawerController;
import com.stcodesapp.noteit.controllers.fragmentController.ContactFragmentController;
import com.stcodesapp.noteit.controllers.fragmentController.EmailFragmentController;
import com.stcodesapp.noteit.ui.activities.ManualEmailActivity;

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

    public NoteFieldController getNoteFieldController()
    {
        return new NoteFieldController(tasksFactory);
    }

    public ManualContactController getManualContactController()
    {
        return new ManualContactController(tasksFactory);
    }

    public ManualEmailActivityController getManualEmailActivityController()
    {
        return new ManualEmailActivityController(tasksFactory);
    }

    public NavigationDrawerController getNavigationDrawerController()
    {
        return new NavigationDrawerController(tasksFactory.getFragmentNavigationTasks());
    }

    public ContactFragmentController getContactFragmentController()
    {
        return new ContactFragmentController(tasksFactory);
    }

    public EmailFragmentController getEmailFragmentController()
    {
        return new EmailFragmentController(tasksFactory);
    }
}
