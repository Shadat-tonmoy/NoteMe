package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.AudioInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.CheckListInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.CheckListItemInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ContactInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.EmailInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ImageInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.NoteInsertTask;

import java.util.List;

public class DatabaseInsertTasksListener implements EmailInsertTask.Listener, NoteInsertTask.Listener, ContactInsertTask.Listener, AudioInsertTask.Listener, ImageInsertTask.Listener, CheckListInsertTask.Listener, CheckListItemInsertTask.Listener {

    private DatabaseTasks databaseTasks;
    private NoteComponents noteComponents;
    private long insertedNoteId;
    private boolean isUpdating;
    private Activity activity;

    public DatabaseInsertTasksListener(Activity activity, NoteComponents noteComponents, boolean isUpdating, DatabaseTasks databaseTasks) {
        this.activity = activity;
        this.databaseTasks = databaseTasks;
        this.noteComponents = noteComponents;
        this.isUpdating = isUpdating;
    }

    @Override
    public void onNoteInserted(long insertedId) {
        setInsertedNoteId(insertedId);
        noteComponents.setNoteIdToNoteComponents(insertedId);
        if(!isUpdating)
            insertEmail();

    }

    @Override
    public void onEmailInserted(int numberOfEmail) {
        if(!isUpdating)
            insertContact();
        else Toast.makeText(activity,activity.getResources().getString(R.string.email_inserted),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onContactInserted(int numberOfContact) {
        if(!isUpdating)
            insertAudio();
        else Toast.makeText(activity,activity.getResources().getString(R.string.contact_inserted),Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAudioInserted(int numberOfEmail) {
        if(!isUpdating)
            insertImage();
        else Toast.makeText(activity,activity.getResources().getString(R.string.audio_inserted),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageInserted(int numberOfImages) {
        if(!isUpdating)
            insertCheckList();
        else Toast.makeText(activity,activity.getResources().getString(R.string.image_inserted),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCheckListInserted(int numberOfCheckList) {
        List<CheckList> checkLists = noteComponents.getCheckLists();
        for(CheckList checkList:checkLists)
        {
            insertCheckListItems(checkList);
        }
        if(isUpdating)
            Toast.makeText(activity,activity.getResources().getString(R.string.image_inserted),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCheckListItemInserted(int numberOfCheckListItem) {

    }

    public void setInsertedNoteId(long insertedNoteId) {
        this.insertedNoteId = insertedNoteId;
    }

    public long getInsertedNoteId() {
        return insertedNoteId;
    }

    private void insertAudio()
    {
        List<Audio> audios  = noteComponents.getChosenAudios();
        Audio[] audioArray = audios.toArray(new Audio[0]);
        databaseTasks.getAudioInsertTask(this).execute(audioArray);
    }

    private void insertImage()
    {
        List<Image> images = noteComponents.getChosenImages();
        Image[] imageArray = images.toArray(new Image[0]);
        databaseTasks.getImageInsertTask(this).execute(imageArray);
    }

    private void insertContact()
    {
        List<Contact> contacts  = noteComponents.getChosenContacts();
        Contact[] contactArray = contacts.toArray(new Contact[0]);
        databaseTasks.getContactInsertTask(this).execute(contactArray);
    }

    private void insertEmail()
    {
        List<Email> emails = noteComponents.getEmails();
        Email[] emailArray = emails.toArray(new Email[0]);
        databaseTasks.getEmailInsertTask(this).execute(emailArray);
    }


    private void insertCheckList()
    {
        List<CheckList> checkLists = noteComponents.getCheckLists();
        CheckList[] checkListsArray = checkLists.toArray(new CheckList[0]);
        databaseTasks.getCheckListInsertTask(this).execute(checkListsArray);
    }

    private void insertCheckListItems(CheckList checkList)
    {
        List<ChecklistItem> checklistItems = checkList.getChecklistItems();
        ChecklistItem[] checkListItemArray = checklistItems.toArray(new ChecklistItem[0]);
        databaseTasks.getCheckListItemInsertTask(this).execute(checkListItemArray);
    }
}
