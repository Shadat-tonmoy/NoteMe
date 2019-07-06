package com.stcodesapp.noteit.listeners;

import android.util.Log;

import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AudioSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.CheckListSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ContactSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.EmailSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ImageSelectTask;

import java.util.List;

public class DatabaseSelectionTasksListener implements EmailSelectTask.Listener, AudioSelectTask.Listener, ImageSelectTask.Listener, ContactSelectTask.Listener, CheckListSelectTask.Listener {


    public interface Listener{
        void onNoteComponentsFetched(NoteComponents noteComponents);
    }


    private DatabaseTasks databaseTasks;
    private Long noteId;
    private NoteComponents noteComponents;
    private Listener listener;


    public DatabaseSelectionTasksListener(DatabaseTasks databaseTasks, NoteComponents noteComponents) {
        this.databaseTasks = databaseTasks;
        this.noteId = noteComponents.getNote().getId();
        this.noteComponents = noteComponents;
    }

    @Override
    public void onEmailFetched(List<Email> fetchedEmails) {
        noteComponents.setEmails(fetchedEmails);
        startFetchingAudio();

    }

    @Override
    public void onAudioFetched(List<Audio> fetchedAudio) {
        noteComponents.setChosenAudios(fetchedAudio);
        startFetchingContact();

    }

    @Override
    public void onContactFetched(List<Contact> fetchedContact) {
        noteComponents.setChosenContacts(fetchedContact);
        startFetchingImage();

    }

    @Override
    public void onImageFetched(List<Image> fetchedImage) {
        noteComponents.setChosenImages(fetchedImage);
        startFetchingChekList();

    }

    @Override
    public void onCheckListFetched(List<CheckList> fetchedCheckList) {
        noteComponents.setCheckLists(fetchedCheckList);
        for(CheckList checkList:fetchedCheckList)
        {
//            Log.e("FetchedCehckList",checkList.toString());
        }

        listener.onNoteComponentsFetched(noteComponents);

    }

    private void startFetchingContact()
    {
        databaseTasks.getContactSelectTask(this).execute(noteId);
    }

    private void startFetchingImage()
    {
        databaseTasks.getImageSelectTask(this).execute(noteId);
    }

    private void startFetchingAudio()
    {
        databaseTasks.getAudioSelectTask(this).execute(noteId);
    }

    private void startFetchingChekList()
    {
        databaseTasks.getCheckListSelectTask(this).execute(noteId);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
