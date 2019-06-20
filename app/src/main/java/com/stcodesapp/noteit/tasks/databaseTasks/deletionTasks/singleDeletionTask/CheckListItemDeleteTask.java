package com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;

public class CheckListItemDeleteTask extends BaseDeletionTasks<ChecklistItem> {

    public interface Listener{
        void onCheckListItemDeleted(ChecklistItem checklistItem);
    }

    private Listener listener;

    public CheckListItemDeleteTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(ChecklistItem checklistItem) {
        super.onPostExecute(checklistItem);
        if(listener!=null)
            listener.onCheckListItemDeleted(checklistItem);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
