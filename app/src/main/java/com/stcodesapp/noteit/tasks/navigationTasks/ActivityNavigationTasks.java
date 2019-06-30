package com.stcodesapp.noteit.tasks.navigationTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.ui.activities.CheckListActivity;
import com.stcodesapp.noteit.ui.activities.ManualContactActivity;
import com.stcodesapp.noteit.ui.activities.ManualEmailActivity;
import com.stcodesapp.noteit.ui.activities.NoteFieldActivity;
import com.stcodesapp.noteit.ui.activities.PrivacyPolicyActivity;
import com.stcodesapp.noteit.ui.activities.InAppPurchaseActivity;

import static android.app.Activity.RESULT_OK;

public class ActivityNavigationTasks {

    private Activity activity;

    public ActivityNavigationTasks(Activity activity) {
        this.activity = activity;
    }

    public void toSettingsScreen(Bundle args)
    {
        Intent intent = new Intent(activity, NoteFieldActivity.class);
        intent.putExtras(args);
        activity.startActivity(intent);
    }

    public void toNoteFieldScreen(Bundle args,boolean isUpdating)
    {
        Intent intent = new Intent(activity, NoteFieldActivity.class);
        intent.putExtras(args);
        if(isUpdating)
            activity.startActivityForResult(intent,RequestCode.UPDATE_NOTE);
        else activity.startActivityForResult(intent,RequestCode.ADD_NEW_NOTE);
    }

    public void toManualContactScreen(Bundle args)
    {
        Intent intent = new Intent(activity, ManualContactActivity.class);
        intent.putExtras(args);
        activity.startActivityForResult(intent, RequestCode.ADD_MANUAL_CONTACT);
    }

    public void toManualEmailScreen(Bundle args)
    {
        Intent intent = new Intent(activity, ManualEmailActivity.class);
        intent.putExtras(args);
        activity.startActivityForResult(intent, RequestCode.ADD_MANUAL_EMAIL);
    }

    public void toCheckListScreen(Bundle args)
    {
        Intent intent = new Intent(activity, CheckListActivity.class);
        intent.putExtras(args);
        activity.startActivityForResult(intent, RequestCode.ADD_SINGLE_CHECKLIST);
    }

    public void toPrivacyPolicyScreen()
    {
        Intent intent = new Intent(activity, PrivacyPolicyActivity.class);
        activity.startActivity(intent);
    }

    public void closeScreen()
    {
        activity.finish();
    }

    public void sendResultBack(Intent result)
    {
        activity.setResult(RESULT_OK, result);
        activity.finish();
    }

    public void toProVersionScreen() {
        Intent intent = new Intent(activity, InAppPurchaseActivity.class);
        activity.startActivity(intent);
    }
}
