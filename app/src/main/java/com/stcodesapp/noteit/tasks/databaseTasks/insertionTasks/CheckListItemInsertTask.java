package com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;

public class CheckListItemInsertTask extends AsyncTask<ChecklistItem, Void, Integer> {

    public interface Listener{
        void onCheckListItemInserted(int numberOfCheckListItem);
    }


    private final Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;


    public CheckListItemInsertTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected Integer doInBackground(ChecklistItem... checklistItems) {
        if(checklistItems.length==1)
            checklistItems[0].setId(noteDatabase.checkListItemDao().insertSingleCheckListItem(checklistItems[0]));
        else noteDatabase.checkListItemDao().insertCheckListItem(checklistItems);

        return checklistItems.length;
    }

    @Override
    protected void onPostExecute(Integer numberOfCheckListItem) {
        super.onPostExecute(numberOfCheckListItem);
        if(listener!=null)
            listener.onCheckListItemInserted(numberOfCheckListItem);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
