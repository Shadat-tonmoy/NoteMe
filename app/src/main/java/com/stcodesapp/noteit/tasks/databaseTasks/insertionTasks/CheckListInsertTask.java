package com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;

import java.util.List;

public class CheckListInsertTask extends AsyncTask<CheckList, Void, CheckList> {

    public interface Listener{
        void onCheckListInserted(CheckList checkList);
    }


    private final Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;


    public CheckListInsertTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected CheckList doInBackground(CheckList... checkLists) {
        Log.e("InsertingCheckList",checkLists.toString());
        if(checkLists.length==1)
        {
            checkLists[0].setCheckListId(noteDatabase.checkListDao().insertSingleCheckList(checkLists[0]));
            List<ChecklistItem> checklistItems = checkLists[0].getChecklistItems();
            long checkListId = checkLists[0].getCheckListId();
            for(ChecklistItem checklistItem:checklistItems)
            {
                checklistItem.setCheckListId(checkListId);
            }
        }
        else
        {
            long[] checkListIds = noteDatabase.checkListDao().insertCheckList(checkLists);
            for(int i=0;i<checkListIds.length;i++)
            {
                checkLists[i].setCheckListId(checkListIds[i]);
                List<ChecklistItem> checklistItems = checkLists[i].getChecklistItems();
                for(ChecklistItem checklistItem:checklistItems)
                {
                    checklistItem.setCheckListId(checkListIds[i]);
                }
            }
        }
        return checkLists[0];
    }


    @Override
    protected void onPostExecute(CheckList result) {
        super.onPostExecute(result);
        listener.onCheckListInserted(result);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
