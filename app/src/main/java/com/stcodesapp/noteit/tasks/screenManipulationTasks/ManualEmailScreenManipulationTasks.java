package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.UtilityTasks;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.ManualEmailScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.ManualEmailScreen;

public class ManualEmailScreenManipulationTasks {

    private Activity activity;
    private ManualEmailScreen manualEmailScreen;

    public ManualEmailScreenManipulationTasks(Activity activity) {
        this.activity = activity;
    }

    public void bindView(ManualEmailScreen manualEmailScreenView) {
        this.manualEmailScreen= manualEmailScreenView;
    }

    public void initToolbar() {
        manualEmailScreen.initUserInteractions();
    }

    public void closeScreen()
    {
        activity.finish();
    }

    public boolean sendResultBack()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.MANUAL_EMAIL,grabEmailValue());
        activity.setResult(Activity.RESULT_OK, resultIntent);
        return grabEmailValue() != null;
    }

    private Email grabEmailValue()
    {
        String emailName = manualEmailScreen.getNameField().getText().toString();
        String emailAddress = manualEmailScreen.getEmailAddressField().getText().toString();
        if(UtilityTasks.isValidString(emailAddress))
            return new Email(emailName,emailAddress);
        return null;

    }

    public void showNoEmailAddressMessage() {
        Toast.makeText(activity, activity.getResources().getString(R.string.no_email_address), Toast.LENGTH_SHORT).show();
    }
}
