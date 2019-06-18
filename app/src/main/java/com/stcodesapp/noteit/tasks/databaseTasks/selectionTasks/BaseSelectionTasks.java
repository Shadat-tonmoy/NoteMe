package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;

import java.util.List;

public class BaseSelectionTasks<ReturnTye> extends AsyncTask<Long, Void, List<ReturnTye>>{



    private Context context;
    private NoteDatabase noteDatabase;
    private ComponentType componentType;

    public BaseSelectionTasks(Context context, ComponentType componentType)
    {
        this.context = context;
        noteDatabase = NoteDatabase.getInstance(context);
        this.componentType = componentType;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<ReturnTye> doInBackground(Long... longs) {
        return getListOfComponents(longs[0]);
    }

    @Override
    protected void onPostExecute(List<ReturnTye> results) {
        super.onPostExecute(results);
    }

    private List<ReturnTye> getListOfComponents(long noteId)
    {
        switch (componentType)
        {
            case AUDIO:
                return (List<ReturnTye>) noteDatabase.audioDao().getAllAudioForNote(noteId);
            case IMAGE:
                return (List<ReturnTye>) noteDatabase.imageDao().getAllImageForNote(noteId);
            case EMAIL:
                return (List<ReturnTye>) noteDatabase.emailDao().getAllEmailForNote(noteId);
            case CONTACT:
                return (List<ReturnTye>) noteDatabase.contactDao().getAllContactsForNote(noteId);
            case CHECKLIST:
                List<CheckList> checkLists = noteDatabase.checkListDao().getAllCheckListForNote(noteId);
                /*for(CheckList checkList:checkLists)
                {
                    checkList.setChecklistItems(noteDatabase.checkListItemDao().getCheckListItems(checkList.getCheckListId()));
                }*/
                return (List<ReturnTye>) checkLists;
            case CHECKLIST_ITEM:
                //here note id is passed as checklist id
                List<ChecklistItem> checklistItems = noteDatabase.checkListItemDao().getCheckListItems(noteId);
                return (List<ReturnTye>) checklistItems;
            default:
                return null;
        }
    }
}
