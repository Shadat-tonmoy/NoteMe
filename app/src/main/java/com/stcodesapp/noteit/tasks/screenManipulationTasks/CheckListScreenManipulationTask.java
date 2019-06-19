package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.Tags;
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
        checkListScreenView.getCheckList().scrollToPosition(lastIndex);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkListScreenView.getCheckList().findViewHolderForAdapterPosition(lastIndex).itemView.findViewById(R.id.check_item_title_field).requestFocus();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                clipboardTasks.showKeyBoard();
            }
        },1);


    }

    public boolean sendResultBack(CheckList checkList,int checkListPosition, boolean isInvalidNote)
    {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.SINGLE_CHECKLIST, (Serializable) checkList);
        resultIntent.putExtra(Constants.SINGLE_CHECKLIST,bundle);
        resultIntent.putExtra(Tags.CHECK_LIST_POSITION,checkListPosition);
        resultIntent.putExtra(Tags.INVALID_NOTE,isInvalidNote);
        activity.setResult(Activity.RESULT_OK, resultIntent);
        return grabCheckListValue() != null;
    }

    public void showCheckListUpdatedToast()
    {
        Toast.makeText(activity, activity.getResources().getString(R.string.check_list_updated), Toast.LENGTH_SHORT).show();
    }

    public CheckList grabCheckListValue()
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
        try {
            for(int i=0;i<totalItems;i++)
            {
                String fieldValue = ((EditText)checkListScreenView.getLayoutManager().findViewByPosition(i).findViewById(R.id.check_item_title_field)).getText().toString();
                checkListScreenView.getCheckListAdapter().getCheckListObjects().get(i).setField1(fieldValue);
            }
            ((EditText)checkListScreenView.getLayoutManager().getFocusedChild().findViewById(R.id.check_item_title_field)).setImeOptions(EditorInfo.IME_ACTION_DONE);
            String currentValue = ((EditText)checkListScreenView.getLayoutManager().getFocusedChild().findViewById(R.id.check_item_title_field)).getText().toString();
            Log.e("CurrentValue",currentValue);
        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("Errot",e.getMessage());
        }
        /*((EditText)checkListScreenView.getLayoutManager().getFocusedChild().findViewById(R.id.check_item_title_field)).setImeOptions(EditorInfo.IME_ACTION_DONE);
        String currentValue = ((EditText)checkListScreenView.getLayoutManager().getFocusedChild().findViewById(R.id.check_item_title_field)).getText().toString();
        Log.e("CurrentValue",currentValue);*/
        return checkList;
    }



    public void initToolbar() {
        checkListScreenView.initUserInteractions();
    }

}
