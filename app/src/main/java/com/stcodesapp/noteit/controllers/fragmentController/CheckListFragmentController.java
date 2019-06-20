package com.stcodesapp.noteit.controllers.fragmentController;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllEmailSelectionTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.EmailScreenManipulationTask;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.CheckListFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.CheckListFragmentScreen;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.EmailFragmentScreen;

import java.util.List;

public class CheckListFragmentController implements CheckListFragmentScreen.Listener {

    private TasksFactory tasksFactory;
    private CheckListFragmentScreenView checkListFragmentScreenView;
    private EmailScreenManipulationTask emailScreenManipulationTask;


    public CheckListFragmentController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        this.emailScreenManipulationTask = tasksFactory.getEmailScreenManipulationTask();
    }

    public void onStart()
    {
        checkListFragmentScreenView.registerListener(this);
//        startFetchingEmails();
    }

    public void onStop()
    {
        checkListFragmentScreenView.unregisterListener(this);
    }

    public void bindView(CheckListFragmentScreenView checkListFragmentScreenView) {
        this.checkListFragmentScreenView = checkListFragmentScreenView;
//        emailScreenManipulationTask.bindView(checkListFragmentScreenView);
//        emailScreenManipulationTask.loadBannerAd();
    }

    private void startFetchingEmails()
    {
//        tasksFactory.getDatabaseTasks().getAllEmailSelectionTasks(this).execute();

    }

//    public boolean onBackPressed()
//    {
//        return emailScreenManipulationTask.closeSearchView();
//    }
}
