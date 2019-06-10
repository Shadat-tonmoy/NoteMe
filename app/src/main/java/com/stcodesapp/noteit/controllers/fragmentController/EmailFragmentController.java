package com.stcodesapp.noteit.controllers.fragmentController;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllContactSelectionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllEmailSelectionTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.EmailScreenManipulationTask;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.ContactFragmentScreen;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.EmailFragmentScreen;

import java.util.List;

public class EmailFragmentController implements EmailFragmentScreen.Listener, AllEmailSelectionTasks.Listener, MaterialSearchView.OnQueryTextListener {

    private TasksFactory tasksFactory;
    private EmailFragmentScreenView emailFragmentScreenView;
    private EmailScreenManipulationTask emailScreenManipulationTask;


    public EmailFragmentController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        this.emailScreenManipulationTask = tasksFactory.getEmailScreenManipulationTask();
    }

    public void onStart()
    {
        emailFragmentScreenView.registerListener(this);
        startFetchingEmails();
    }

    public void onStop()
    {
        emailFragmentScreenView.unregisterListener(this);
    }

    public void bindView(EmailFragmentScreenView emailFragmentScreenView) {
        this.emailFragmentScreenView = emailFragmentScreenView;
        emailScreenManipulationTask.bindView(emailFragmentScreenView);
        emailScreenManipulationTask.loadBannerAd();
    }

    private void startFetchingEmails()
    {
        tasksFactory.getDatabaseTasks().getAllEmailSelectionTasks(this).execute();

    }

    public boolean onBackPressed()
    {
        return emailScreenManipulationTask.closeSearchView();
    }

    @Override
    public void onAllEmailFetched(List<Email> emails) {
        emailScreenManipulationTask.bindEmails(emails);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        emailScreenManipulationTask.performFilter(newText);
        return false;
    }
}
