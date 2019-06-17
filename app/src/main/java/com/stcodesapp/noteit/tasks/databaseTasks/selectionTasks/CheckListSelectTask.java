package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.CheckList;

import java.util.List;

public class CheckListSelectTask extends BaseSelectionTasks<CheckList> {

    public interface Listener{
        void onCheckListFetched(List<CheckList> fetchedCheckList);
    }

    private Listener listener;

    public CheckListSelectTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(List<CheckList> results) {
        listener.onCheckListFetched(results);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
