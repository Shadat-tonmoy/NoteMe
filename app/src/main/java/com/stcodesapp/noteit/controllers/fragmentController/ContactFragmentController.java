package com.stcodesapp.noteit.controllers.fragmentController;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllContactSelectionTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.ContactScreenManipulationTask;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.ContactFragmentScreen;

import java.util.List;

public class ContactFragmentController implements ContactFragmentScreen.Listener, AllContactSelectionTasks.Listener, MaterialSearchView.OnQueryTextListener  {

    private TasksFactory tasksFactory;
    private ContactFragmentScreenView contactFragmentScreenView;
    private ContactScreenManipulationTask contactScreenManipulationTask;


    public ContactFragmentController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        this.contactScreenManipulationTask = tasksFactory.getContactScreenManipulationTask();
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
        contactScreenManipulationTask.bindView(contactFragmentScreenView);
        contactScreenManipulationTask.loadBannerAd();
    }

    private void startFetchingContacts()
    {
        tasksFactory.getDatabaseTasks().getAllContactSelectionTasks(this).execute();

    }

    public boolean onBackPressed()
    {
        return contactScreenManipulationTask.closeSearchView();
    }
    @Override
    public void onAllContactFetched(List<Contact> contacts) {
        contactScreenManipulationTask.bindContacts(contacts);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        contactScreenManipulationTask.performFilter(newText);
        return false;
    }
}
