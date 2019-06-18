package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;

import java.util.List;

public class CheckListItemSelectTask extends BaseSelectionTasks<ChecklistItem> {

    public interface Listener{
        void onCheckListItemFetched(List<ChecklistItem> fetchedCheckListItems);
    }

    private Listener listener;

    public CheckListItemSelectTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(List<ChecklistItem> results) {
        listener.onCheckListItemFetched(results);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
