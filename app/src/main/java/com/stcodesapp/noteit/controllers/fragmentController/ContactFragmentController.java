package com.stcodesapp.noteit.controllers.fragmentController;

import android.app.Activity;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllContactSelectionTasks;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.ContactFragmentScreen;

import java.util.List;

public class ContactFragmentController implements ContactFragmentScreen.Listener, AllContactSelectionTasks.Listener {

    private TasksFactory tasksFactory;
    private ContactFragmentScreenView contactFragmentScreenView;


    public ContactFragmentController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
    }

    public void onStart()
    {
        contactFragmentScreenView.registerListener(this);
        startFetchingContacts();
    }

    public void onStop()
    {
        contactFragmentScreenView.unregisterListener(this);
    }

    public void bindView(ContactFragmentScreenView contactFragmentScreenView) {
        this.contactFragmentScreenView = contactFragmentScreenView;
    }

    private void startFetchingContacts()
    {
        tasksFactory.getDatabaseTasks().getAllContactSelectionTasks(this).execute();

    }

    @Override
    public void onCallButtonPressed() {

    }

    @Override
    public void onCopyButtonPressed() {

    }

    @Override
    public void onAllContactFetched(List<Contact> contacts) {
        contactFragmentScreenView.bindContacts(contacts);

    }
}
