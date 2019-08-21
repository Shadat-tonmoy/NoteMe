package com.stcodesapp.noteit.factory;

import android.app.Activity;

import com.stcodesapp.noteit.common.adController.FullScreenAdController;
import com.stcodesapp.noteit.controllers.HomeScreenController;
import com.stcodesapp.noteit.controllers.activityController.CheckListActivityController;
import com.stcodesapp.noteit.controllers.activityController.IAPActivityController;
import com.stcodesapp.noteit.controllers.activityController.MainActivityController;
import com.stcodesapp.noteit.controllers.activityController.ManualContactController;
import com.stcodesapp.noteit.controllers.activityController.ManualEmailActivityController;
import com.stcodesapp.noteit.controllers.activityController.NoteFieldController;
import com.stcodesapp.noteit.controllers.adController.NoteFieldAdController;
import com.stcodesapp.noteit.controllers.commons.NavigationDrawerController;
import com.stcodesapp.noteit.controllers.dialogController.SortingOptionDialogController;
import com.stcodesapp.noteit.controllers.fragmentController.BackupFragmentController;
import com.stcodesapp.noteit.controllers.fragmentController.CheckListFragmentController;
import com.stcodesapp.noteit.controllers.fragmentController.ContactFragmentController;
import com.stcodesapp.noteit.controllers.fragmentController.EmailFragmentController;
import com.stcodesapp.noteit.controllers.fragmentController.FileSaveScreenController;

public class ControllerFactory {

    private TasksFactory tasksFactory;
    private Activity activity;

    public ControllerFactory(TasksFactory tasksFactory,Activity activity) {
        this.tasksFactory = tasksFactory;
        this.activity = activity;
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
        return new NavigationDrawerController(tasksFactory);
    }

    public ContactFragmentController getContactFragmentController()
    {
        return new ContactFragmentController(tasksFactory);
    }

    public EmailFragmentController getEmailFragmentController()
    {
        return new EmailFragmentController(tasksFactory);
    }

    public CheckListFragmentController getCheckListFragmentController()
    {
        return new CheckListFragmentController(tasksFactory);
    }

    public SortingOptionDialogController getSortingOptionDialogController()
    {
        return new SortingOptionDialogController(tasksFactory);
    }

    public NoteFieldAdController getNoteFieldAdController()
    {
        return new NoteFieldAdController(activity, tasksFactory);
    }
    public CheckListActivityController getCheckListActivityController()
    {
        return new CheckListActivityController(tasksFactory,activity);
    }

    public FileSaveScreenController getFileSaveScreenController()
    {
        return new FileSaveScreenController(tasksFactory);
    }

    public IAPActivityController getIAPActivityController()
    {
        return new IAPActivityController(activity,tasksFactory);
    }

    public FullScreenAdController getFullScreenAdController()
    {
        return new FullScreenAdController(activity,tasksFactory);
    }

    public BackupFragmentController getBackupFragmentController()
    {
        return new BackupFragmentController(activity,tasksFactory);
    }


}
