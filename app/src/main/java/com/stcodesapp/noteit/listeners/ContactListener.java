package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.tasks.UtilityTasks;

public class ContactListener implements View.OnClickListener {

    private Contact contact;
    private Activity activity;

    public ContactListener(Contact contact, Activity activity) {
        this.contact = contact;
        this.activity = activity;
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
        }

    }

    private void copyContact() {
        UtilityTasks.copyToClipboard(activity,contact.getPhoneNumber());
    }

    private void callContact() {
        UtilityTasks.makeCall(activity,contact.getPhoneNumber());
    }
}
