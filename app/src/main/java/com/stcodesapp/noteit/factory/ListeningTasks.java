package com.stcodesapp.noteit.factory;

import android.app.Activity;
import android.net.Uri;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;
import com.stcodesapp.noteit.listeners.AudioListener;
import com.stcodesapp.noteit.listeners.CheckListListener;
import com.stcodesapp.noteit.listeners.ContactListener;
import com.stcodesapp.noteit.listeners.DatabaseSelectionTasksListener;
import com.stcodesapp.noteit.listeners.EmailListener;
import com.stcodesapp.noteit.listeners.ImageListener;
import com.stcodesapp.noteit.listeners.RemoveImageListener;
import com.stcodesapp.noteit.listeners.DatabaseInsertTasksListener;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;

public class ListeningTasks {

    private Activity activity;
    private DatabaseTasks databaseTasks;
    private TasksFactory tasksFactory;

    public ListeningTasks(Activity activity, DatabaseTasks databaseTasks, TasksFactory tasksFactory) {
        this.activity = activity;
        this.databaseTasks = databaseTasks;
        this.tasksFactory = tasksFactory;
    }

    public RemoveImageListener getRemoveImageListener(FlexboxLayout container, View image)
    {
        return new RemoveImageListener(container,image);
    }

    public ContactListener getContactListener(Contact contact,View contactHolder)
    {
        return new ContactListener(contact, activity,databaseTasks, contactHolder);
    }

    public EmailListener getEmailListener(Email email,View emailHolder)
    {
        return new EmailListener(email, activity,databaseTasks,emailHolder);
    }

    public CheckListListener getCheckListListener(CheckList checkList, View checkListHolder,int checkListPosition)
    {
        return new CheckListListener(checkList, activity,databaseTasks,checkListHolder,tasksFactory, checkListPosition);
    }

    public ImageListener getImageListener(Image image, View imageHolder)
    {
        return new ImageListener(image, activity,databaseTasks,imageHolder);
    }

    public AudioListener getAudioListener(Audio audio, FileIOTasks fileIOTasks, Uri audioUri,View audioHolder)
    {
        return new AudioListener(audio, fileIOTasks, audioUri, activity, databaseTasks, audioHolder);
    }

    public DatabaseInsertTasksListener getDBInsertTasksListener(DatabaseTasks databaseTasks, NoteComponents noteComponents, boolean isUpdating){
        return new DatabaseInsertTasksListener(activity, noteComponents, isUpdating, databaseTasks);
    }

    public DatabaseSelectionTasksListener getDBSelectTasksListener(DatabaseTasks databaseTasks,NoteComponents noteComponents){
        return new DatabaseSelectionTasksListener(databaseTasks,noteComponents);
    }


}
