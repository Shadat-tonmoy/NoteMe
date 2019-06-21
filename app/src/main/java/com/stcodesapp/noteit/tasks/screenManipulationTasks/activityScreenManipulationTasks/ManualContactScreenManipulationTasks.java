package com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.ManualContactScreenView;

public class ManualContactScreenManipulationTasks {

    private Activity activity;
    private ManualContactScreenView manualContactScreenView;

    public ManualContactScreenManipulationTasks(Activity activity) {
        this.activity = activity;
    }

    public void bindView(ManualContactScreenView manualContactScreenView) {
        this.manualContactScreenView = manualContactScreenView;
    }

    public void initToolbar() {
        manualContactScreenView.initUserInteractions();
    }

    public boolean sendResultBack()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.MANUAL_CONTACT,grabContactValue());
        activity.setResult(Activity.RESULT_OK, resultIntent);
        return grabContactValue() != null;
    }

    private Contact grabContactValue()
    {
        String nameValue  = manualContactScreenView.getNameField().getText().toString();
        String phoneNoValue = manualContactScreenView.getPhoneNoField().getText().toString();
        if(UtilityTasks.isValidString(phoneNoValue))
            return new Contact(phoneNoValue,nameValue);
        return null;
    }

    public void showNoPhoneNumberMessage() {
        Toast.makeText(activity, activity.getResources().getString(R.string.no_phone_number), Toast.LENGTH_SHORT).show();
    }
}
