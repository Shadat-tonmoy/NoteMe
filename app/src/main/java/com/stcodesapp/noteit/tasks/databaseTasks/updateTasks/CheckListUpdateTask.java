package com.stcodesapp.noteit.tasks.databaseTasks.updateTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.AudioInsertTask;

public class CheckListUpdateTask extends AsyncTask<CheckList, Void, Void> {


    private final Context context;
    private NoteDatabase noteDatabase;


    public CheckListUpdateTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected Void doInBackground(CheckList... checkLists) {
        noteDatabase.checkListDao().updateCheckList(checkLists[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void res) {

    }
}