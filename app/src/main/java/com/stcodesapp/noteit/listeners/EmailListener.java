package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.ui.activities.NoteFieldActivity;

public class EmailListener implements View.OnClickListener {

    private Email email;
    private Activity activity;
    private DatabaseTasks databaseTasks;
    private View emailHolder;

    public EmailListener(Email email, Activity activity, DatabaseTasks databaseTasks, View emailHolder) {
        this.email = email;
        this.activity = activity;
        this.databaseTasks = databaseTasks;
        this.emailHolder = emailHolder;
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
            case R.id.email_remove_btn:
                removeEmail();
                break;
        }

    }

    private void copyEmail() {
        UtilityTasks.copyToClipboard(activity,email.getEmailID());
    }

    private void sendEmail() {
        UtilityTasks.sendEmail(activity,email.getEmailID());
    }

    private void removeEmail()
    {
        emailHolder.setVisibility(View.GONE);
        databaseTasks.getEmailDeleteTask(((NoteFieldActivity)activity).getNoteFieldController()).execute(email);
    }
}
