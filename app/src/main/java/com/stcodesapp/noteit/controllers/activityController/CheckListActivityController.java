package com.stcodesapp.noteit.controllers.activityController;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.CheckListAdapter;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.CheckListItemDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.CheckListItemInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.CheckListItemSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.updateTasks.CheckListItemUpdateTask;
import com.stcodesapp.noteit.tasks.databaseTasks.updateTasks.CheckListUpdateTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.CheckListScreenManipulationTask;
import com.stcodesapp.noteit.tasks.utilityTasks.ClipboardTasks;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.CheckListScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.CheckListScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class CheckListActivityController implements CheckListScreen.Listener, CheckListItemSelectTask.Listener, CheckListAdapter.Listener{

    private TasksFactory tasksFactory;
    private Activity activity;
    private CheckListScreenView checkListScreenView;
    private ClipboardTasks clipboardTasks;
    private CheckListScreenManipulationTask checkListScreenManipulationTask;
    private boolean isUpdating = false;
    private CheckList checkListFromIntent;
    private long noteId;
    private int checkListPosition;



    public CheckListActivityController(TasksFactory tasksFactory, Activity activity) {
        this.tasksFactory = tasksFactory;
        this.activity = activity;
        this.checkListScreenManipulationTask = tasksFactory.getCheckListScreenManipulationTask();
        this.clipboardTasks = tasksFactory.getClipboardTasks();
    }

    public void bindView(CheckListScreenView checkListScreenView) {
        this.checkListScreenView = checkListScreenView;
        checkListScreenView.setCheckListAdapterListener(this);
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
    }

    public void fetchCheckListItems()
    {
        List<ChecklistItem> checklistItems = new ArrayList<>();
        /*for(char a = 'a';a<='z';a++)
            checklistItems.add(new ChecklistItem(Constants.EMPTY_STRING+a,false));*/

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
        clipboardTasks.hideKeyBoard();
        if(isUpdating)
        {
//            Log.e("NoteId",noteId+" is isisisi");
            if(noteId==Constants.ZERO)
            {
                CheckList checkList = checkListScreenManipulationTask.grabCheckListValue();
                if(checkListScreenManipulationTask.sendResultBack(checkList,checkListPosition,true))
                    activity.finish();
                return;

            }
            updateCheckList();
            checkListScreenManipulationTask.showCheckListUpdatedToast();
            activity.finish();
            return;
        }
        else {
            CheckList checkList = checkListScreenManipulationTask.grabCheckListValue();
            checkList.setNoteId(noteId);
            if(checkListScreenManipulationTask.sendResultBack(checkList,checkListPosition,false))
                activity.finish();
        }

    }

    private void updateCheckList()
    {
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        CheckList checkList = checkListScreenManipulationTask.grabCheckListValue();
        checkList.setCheckListId(checkListFromIntent.getCheckListId());
        checkList.setNoteId(checkListFromIntent.getNoteId());
        CheckListUpdateTask checkListUpdateTask = databaseTasks.getCheckListUpdateTask();
        checkListUpdateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,checkList);
        List<ChecklistItem> checklistItems = checkList.getChecklistItems();
        List<ChecklistItem> existingChecklistItems = new ArrayList<>();
        List<ChecklistItem> newChecklistItems = new ArrayList<>();
        for(ChecklistItem checklistItem:checklistItems)
        {
            checklistItem.setCheckListId(checkList.getCheckListId());
            if(checklistItem.getId()==Constants.ZERO)
                newChecklistItems.add(checklistItem);
            else existingChecklistItems.add(checklistItem);
        }
        CheckListItemUpdateTask checkListItemUpdateTask = databaseTasks.getCheckListItemUpdateTask();
        ChecklistItem[] existingChecklistItemsArray = existingChecklistItems.toArray(new ChecklistItem[0]);
        checkListItemUpdateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,existingChecklistItemsArray);

        CheckListItemInsertTask checkListItemInsertTask = databaseTasks.getCheckListItemInsertTask(null);
        ChecklistItem[] newChecklistItemsArray = newChecklistItems.toArray(new ChecklistItem[0]);
        checkListItemInsertTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,newChecklistItemsArray);
    }

    public void checkIntentForExtra(Intent intent) {
        CheckList checkList = (CheckList) intent.getSerializableExtra(Tags.CHECK_LIST);
        isUpdating = intent.getBooleanExtra(Tags.CHECK_LIST_UPDATING,false);
        Log.e("ChekListUpdating",isUpdating+" is value");
        noteId = intent.getLongExtra(Tags.NOTE_ID,Constants.ZERO);
        checkListPosition = intent.getIntExtra(Tags.CHECK_LIST_POSITION,Constants.ZERO);
        if(checkList!=null)
        {
            checkListFromIntent = checkList;
            List<ChecklistItem> checklistItems = checkList.getChecklistItems();
            if(checkList.getCheckListId()==Constants.ZERO)
            {
                if(checklistItems!=null && checklistItems.size()>Constants.ZERO)
                {
                    Log.e("FetchingItems","FromComponents");
                    checkListScreenManipulationTask.bindCheckListObject(checklistItems);
                    checkListScreenManipulationTask.bindCheckListTitle(checkList.getCheckListTitle());
                }
            }
            else
            {
                Log.e("FetchingItems","FromDataase");
                CheckListItemSelectTask checkListItemSelectTask = tasksFactory.getDatabaseTasks().getCheckListItemSelectTask(this);
                checkListItemSelectTask.execute(checkList.getCheckListId());
                checkListScreenManipulationTask.bindCheckListTitle(checkList.getCheckListTitle());
            }
        }
        else fetchCheckListItems();

    }

    @Override
    public void onCheckListItemFetched(List<ChecklistItem> fetchedCheckListItems)
    {
        checkListScreenManipulationTask.bindCheckListObject(fetchedCheckListItems);

    }

    @Override
    public void onDeleteCheckListItemClicked(ChecklistItem checklistItem) {
        CheckListItemDeleteTask checkListItemDeleteTask = tasksFactory.getDatabaseTasks().getCheckListItemDeleteTask(null);
        checkListItemDeleteTask.execute(checklistItem);


    }
}
