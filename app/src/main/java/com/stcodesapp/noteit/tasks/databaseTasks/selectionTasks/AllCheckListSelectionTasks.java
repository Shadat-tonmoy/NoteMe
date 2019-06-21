package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Email;

import java.util.List;

public class AllCheckListSelectionTasks extends AsyncTask<Void, Void, List<CheckList>>{



    public interface Listener{
        void onAllCheckListFetched(List<CheckList> checkLists);
    }

    private Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;

    public AllCheckListSelectionTasks(Context context, Listener listener)
    {
        this.context = context;
        noteDatabase = NoteDatabase.getInstance(context);
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<CheckList> doInBackground(Void... voids) {
        return noteDatabase.checkListDao().getAllCheckList();
    }

    @Override
    protected void onPostExecute(List<CheckList> results) {
        super.onPostExecute(results);
        if(listener!=null)
            listener.onAllCheckListFetched(results);
    }
}
