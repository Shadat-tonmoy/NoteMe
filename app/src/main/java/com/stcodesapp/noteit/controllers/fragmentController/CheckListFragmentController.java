package com.stcodesapp.noteit.controllers.fragmentController;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllCheckListSelectionTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.CheckListScreenManipulationTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.EmailScreenManipulationTask;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.CheckListFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.CheckListFragmentScreen;

import java.util.List;

public class CheckListFragmentController implements CheckListFragmentScreen.Listener, AllCheckListSelectionTasks.Listener, MaterialSearchView.OnQueryTextListener  {

    private TasksFactory tasksFactory;
    private CheckListFragmentScreenView checkListFragmentScreenView;
    private CheckListScreenManipulationTask checkListScreenManipulationTask;


    public CheckListFragmentController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        this.checkListScreenManipulationTask = tasksFactory.getCheckListScreenManipulationTask();
    }

    public void onStart()
    {
        checkListFragmentScreenView.registerListener(this);
        startFetchingChecklists();
    }

    public void onStop()
    {
        checkListFragmentScreenView.unregisterListener(this);
    }

    public void bindView(CheckListFragmentScreenView checkListFragmentScreenView) {
        this.checkListFragmentScreenView = checkListFragmentScreenView;
        checkListScreenManipulationTask.bindView(checkListFragmentScreenView);
        checkListScreenManipulationTask.loadBannerAd();
    }

    private void startFetchingChecklists()
    {
        tasksFactory.getDatabaseTasks().getAllCheckListSelectionTasks(this).execute();

    }

    @Override
    public void onAllCheckListFetched(List<CheckList> checkLists) {
        checkListScreenManipulationTask.bindCheckLists(checkLists);


    }

    public boolean onBackPressed()
    {
        return checkListScreenManipulationTask.closeSearchView();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        checkListScreenManipulationTask.performFilter(newText);
        return false;
    }
}
