package com.stcodesapp.noteit.controllers.activityController;

import android.app.Activity;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.SingleCheckList;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.CheckListScreenManipulationTask;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.CheckListScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.CheckListScreen;

import java.util.ArrayList;
import java.util.List;

public class CheckListActivityController implements CheckListScreen.Listener {


    private TasksFactory tasksFactory;
    private Activity activity;
    private CheckListScreenView checkListScreenView;
    private CheckListScreenManipulationTask checkListScreenManipulationTask;

    public CheckListActivityController(TasksFactory tasksFactory, Activity activity) {
        this.tasksFactory = tasksFactory;
        this.activity = activity;
        this.checkListScreenManipulationTask = tasksFactory.getCheckListScreenManipulationTask();
    }

    public void bindView(CheckListScreenView checkListScreenView) {
        this.checkListScreenView = checkListScreenView;
        checkListScreenManipulationTask.bindView(checkListScreenView);
    }

    public void onStart()
    {
        checkListScreenView.registerListener(this);
    }

    public void onStop()
    {
        checkListScreenView.unregisterListener(this);
    }


    public  void onPostCreate()
    {
        checkListScreenManipulationTask.initToolbar();
        fetchCheckListItems();
    }

    public void fetchCheckListItems()
    {
        List<SingleCheckList> singleCheckLists = new ArrayList<>();
        singleCheckLists.add(new SingleCheckList("test1",false));
        singleCheckLists.add(new SingleCheckList("test2",true));
        singleCheckLists.add(new SingleCheckList("test3",true));
        singleCheckLists.add(new SingleCheckList("test4",false));
        singleCheckLists.add(new SingleCheckList("test5",true));
        checkListScreenManipulationTask.bindCheckListObject(singleCheckLists);
    }

    @Override
    public void onNavigateUp() {
        activity.finish();
    }
}
