package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.UtilityTasks;

public class EmailListener implements View.OnClickListener {

    private Email email;
    private Activity activity;

    public EmailListener(Email email, Activity activity) {
        this.email = email;
        this.activity = activity;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.email_copy_btn:
                copyEmail();
                break;
            case R.id.email_send_btn:
                sendEmail();
                break;
        }

    }

    private void copyEmail() {
        UtilityTasks.copyToClipboard(activity,email.getEmailID());
    }

    private void sendEmail() {
        UtilityTasks.sendEmail(activity,email.getEmailID());
    }
}
