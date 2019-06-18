package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;
import com.stcodesapp.noteit.tasks.utilityTasks.ClipboardTasks;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.CheckListScreenView;

import java.io.Serializable;
import java.util.List;

public class CheckListScreenManipulationTask {

    private Activity activity;
    private CheckListScreenView checkListScreenView;
    private ClipboardTasks clipboardTasks;

    public CheckListScreenManipulationTask(Activity activity,ClipboardTasks clipboardTasks) {
        this.activity = activity;
        this.clipboardTasks = clipboardTasks;
    }

    public void bindView(CheckListScreenView checkListScreenView) {
        this.checkListScreenView = checkListScreenView;
    }


    public void bindCheckListObject(List<ChecklistItem> checklistItems) {
        if (checklistItems.size() > Constants.ZERO) {
            checkListScreenView.getCheckListAdapter().bindCheckListObjects(checklistItems);
        }
    }

    public void bindCheckListTitle(String checkListTitle) {
        checkListScreenView.getCheckListTitleField().setText(checkListTitle);
    }

    public void addEmptyChecklistItem() {
            checkListScreenView.getCheckListAdapter().addEmptyChecklistItem();
            final int lastIndex = checkListScreenView.getCheckListAdapter().getItemCount()-1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkListScreenView.getCheckList().findViewHolderForAdapterPosition(lastIndex).itemView.findViewById(R.id.check_item_title_field).requestFocus();
                clipboardTasks.showKeyBoard();
            }
        },1);


    }

    public boolean sendResultBack()
    {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.SINGLE_CHECKLIST, (Serializable) grabCheckListValue());
        resultIntent.putExtra(Constants.SINGLE_CHECKLIST,bundle);
        activity.setResult(Activity.RESULT_OK, resultIntent);
        return grabCheckListValue() != null;
    }

    private CheckList grabCheckListValue()
    {
        CheckList checkList = new CheckList(checkListScreenView.getCheckListTitleField().getText().toString());
        checkList.setChecklistItems(checkListScreenView.getCheckListAdapter().getCheckListObjects());
        int totalItems = checkListScreenView.getCheckListAdapter().getItemCount();
        try {
            String lastFieldValue = ((EditText)checkListScreenView.getLayoutManager().findViewByPosition(totalItems-1).findViewById(R.id.check_item_title_field)).getText().toString();
            checkListScreenView.getCheckListAdapter().getCheckListObjects().get(totalItems-1).setField1(lastFieldValue);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return checkList;
    }



    public void initToolbar() {
        checkListScreenView.initUserInteractions();
    }

}
