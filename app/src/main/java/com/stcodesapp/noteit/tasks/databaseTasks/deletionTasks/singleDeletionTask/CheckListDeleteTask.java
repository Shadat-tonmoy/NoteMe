package com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Contact;

public class CheckListDeleteTask extends BaseDeletionTasks<CheckList> {

    public interface Listener{
        void onCheckListDeleted(CheckList checkList);
    }

    private Listener listener;

    public CheckListDeleteTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(CheckList checkList) {
        super.onPostExecute(checkList);
        if(listener!=null)
            listener.onCheckListDeleted(checkList);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
