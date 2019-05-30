package com.stcodesapp.noteit.controllers.fragmentController;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllContactSelectionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllEmailSelectionTasks;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.ContactFragmentScreen;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.EmailFragmentScreen;

import java.util.List;

public class EmailFragmentController implements EmailFragmentScreen.Listener, AllEmailSelectionTasks.Listener {

    private TasksFactory tasksFactory;
    private EmailFragmentScreenView emailFragmentScreenView;


    public EmailFragmentController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
    }

    public void onStart()
    {
        emailFragmentScreenView.registerListener(this);
        startFetchingContacts();
    }

    public void onStop()
    {
        emailFragmentScreenView.unregisterListener(this);
    }

    public void bindView(EmailFragmentScreenView emailFragmentScreenView) {
        this.emailFragmentScreenView = emailFragmentScreenView;
    }

    private void startFetchingContacts()
    {
        tasksFactory.getDatabaseTasks().getAllEmailSelectionTasks(this).execute();

    }

    @Override
    public void onAllEmailFetched(List<Email> emails) {
        emailFragmentScreenView.bindEmails(emails);

    }
}
