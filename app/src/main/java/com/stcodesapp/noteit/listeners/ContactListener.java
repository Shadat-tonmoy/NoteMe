package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.tasks.UtilityTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.ui.activities.NoteFieldActivity;

public class ContactListener implements View.OnClickListener {

    private Contact contact;
    private Activity activity;
    private DatabaseTasks databaseTasks;
    private View contactHolder;

    public ContactListener(Contact contact, Activity activity, DatabaseTasks databaseTasks, View contactHolder) {
        this.contact = contact;
        this.activity = activity;
        this.databaseTasks = databaseTasks;
        this.contactHolder = contactHolder;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.contact_call_btn:
                callContact();
                break;
            case R.id.contact_copy_btn:
                copyContact();
                break;
            case R.id.contact_remove_btn:
                removeContact();
                break;
        }

    }

    private void copyContact() {
        UtilityTasks.copyToClipboard(activity,contact.getPhoneNumber());
    }

    private void callContact() {
        UtilityTasks.makeCall(activity,contact.getPhoneNumber());
    }

    private void removeContact()
    {
        contactHolder.setVisibility(View.GONE);
        databaseTasks.getContactDeleteTask(((NoteFieldActivity)activity).getNoteFieldController()).execute(contact);


    }
}
