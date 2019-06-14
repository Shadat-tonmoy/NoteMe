package com.stcodesapp.noteit.controllers.activityController;

import android.app.Activity;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.ChecklistItem;
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
        List<ChecklistItem> checklistItems = new ArrayList<>();
        checklistItems.add(new ChecklistItem(Constants.EMPTY_STRING,false));
        checkListScreenManipulationTask.bindCheckListObject(checklistItems);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.check_item_add:
                return addCheckItem();
        }
        return false;
    }

    private boolean addCheckItem()
    {
        checkListScreenManipulationTask.addEmptyChecklistItem();
        return true;
    }

    @Override
    public void onNavigateUp() {
        activity.finish();
    }

    @Override
    public void onCheckListDoneButtonClicked() {

    }
}
