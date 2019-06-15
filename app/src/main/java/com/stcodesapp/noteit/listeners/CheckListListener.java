package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;
import com.stcodesapp.noteit.ui.activities.NoteFieldActivity;

public class CheckListListener implements View.OnClickListener {

    private CheckList checkList;
    private Activity activity;
    private DatabaseTasks databaseTasks;
    private View checkListHolder;
    private TasksFactory tasksFactory;

    public CheckListListener(CheckList checkList, Activity activity, DatabaseTasks databaseTasks, View checkListHolder, TasksFactory tasksFactory) {
        this.checkList = checkList;
        this.activity = activity;
        this.databaseTasks = databaseTasks;
        this.checkListHolder = checkListHolder;
        this.tasksFactory = tasksFactory;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.check_list_remove_btn:
                removeCheckList();
                break;
            case R.id.checklist_holder_main_layout:
                showCheckList();
                break;
        }

    }

    private void showCheckList()
    {
        ActivityNavigationTasks activityNavigationTasks = tasksFactory.getActivityNavigationTasks();
        Bundle args = new Bundle();
        args.putSerializable(Tags.CHECK_LIST,checkList);
        activityNavigationTasks.toCheckListScreen(args);
    }

    private void removeCheckList()
    {
        checkListHolder.setVisibility(View.GONE);
//        databaseTasks.getEmailDeleteTask(((NoteFieldActivity)activity).getNoteFieldController()).execute(email);
    }
}
