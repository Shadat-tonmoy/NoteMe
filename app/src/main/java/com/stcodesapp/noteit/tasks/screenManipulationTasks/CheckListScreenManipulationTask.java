package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.os.Handler;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.ChecklistItem;
import com.stcodesapp.noteit.tasks.utilityTasks.ClipboardTasks;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.CheckListScreenView;

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


    public void initToolbar() {
        checkListScreenView.initUserInteractions();
    }

}
