package com.stcodesapp.noteit.tasks.databaseTasks.updateTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;

public class CheckListItemUpdateTask extends AsyncTask<ChecklistItem, Void, Void> {


    private final Context context;
    private NoteDatabase noteDatabase;


    public CheckListItemUpdateTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected Void doInBackground(ChecklistItem... checklistItems) {
        noteDatabase.checkListItemDao().updateCheckListItems(checklistItems);
        return null;
    }

    @Override
    protected void onPostExecute(Void res) {

    }
}