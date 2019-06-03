package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;

import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;

public class NoteFieldValidationTask {

    private Activity activity;
    private NoteComponents noteComponents;

    public NoteFieldValidationTask(Activity activity, NoteComponents noteComponents) {
        this.activity = activity;
        this.noteComponents = noteComponents;
    }

    public boolean isValidNote()
    {
        int audio = noteComponents.getChosenAudios().size();
        int images = noteComponents.getChosenImages().size();
        int contact = noteComponents.getChosenContacts().size();
        int emails = noteComponents.getEmails().size();
        if(audio>0 || images>0 || contact>0 || emails>0)
            return true;
        String title = noteComponents.getNote().getNoteTitle();
        String text = noteComponents.getNote().getNoteText();
        if(UtilityTasks.isValidString(title) || UtilityTasks.isValidString(text))
            return true;
        return false;
    }
}
