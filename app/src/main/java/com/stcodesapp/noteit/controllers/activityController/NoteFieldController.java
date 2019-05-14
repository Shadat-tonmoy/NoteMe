package com.stcodesapp.noteit.controllers.activityController;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.factory.ListeningTasks;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.listeners.DatabaseInsertTasksListener;
import com.stcodesapp.noteit.listeners.DatabaseSelectionTasksListener;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.VoiceInputTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.NoteFieldScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;
import com.stcodesapp.noteit.ui.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.ui.fragments.PhoneNoOptionsBottomSheets;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.NoteFieldScreen;

import static android.app.Activity.RESULT_OK;

public class NoteFieldController implements NoteFieldScreen.Listener,ColorPallateBottomSheets.Listener, PhoneNoOptionsBottomSheets.Listener {

    private TasksFactory tasksFactory;
    private ListeningTasks listeningTasks;
    private ActivityNavigationTasks activityNavigationTasks;
    private NoteFieldScreenView noteFieldScreenView;
    private NoteFieldScreenManipulationTasks noteFieldScreenManipulationTasks;
    private FileIOTasks fileIOTasks;
    private AppPermissionTrackingTasks appPermissionTrackingTasks;
    private VoiceInputTasks voiceInputTasks;
    private NoteComponents noteComponents;

    public NoteFieldController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        activityNavigationTasks = tasksFactory.getActivityNavigationTasks();
        noteFieldScreenManipulationTasks = tasksFactory.getNoteFieldScreenManipulationTasks();
        fileIOTasks = tasksFactory.getFileIOTasks();
        appPermissionTrackingTasks = tasksFactory.getAppPermissionTrackingTasks();
        voiceInputTasks = tasksFactory.getVoiceInputTasks();
        listeningTasks = tasksFactory.getListeningTasks();

    }

    public void bindView(NoteFieldScreenView secondActivityScreenView) {
        this.noteFieldScreenView = secondActivityScreenView;
        noteFieldScreenManipulationTasks.bindView(noteFieldScreenView);
    }

    public void bindNoteComponents(NoteComponents noteComponents)
    {
        this.noteComponents = noteComponents;
        noteFieldScreenManipulationTasks.bindNoteComponents(noteComponents);
    }

    public void onStart()
    {
        noteFieldScreenView.registerListener(this);
    }

    public void onStop()
    {
        noteFieldScreenView.unregisterListener(this);
    }

    public void checkBundleForNote(Bundle args)
    {
        Note note = (Note) args.getSerializable(Tags.NOTE);
        if(note!=null)
        {
            DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
            DatabaseSelectionTasksListener databaseSelectionTasksListener= listeningTasks.getDBSelectTasksListener(databaseTasks,note.getId());
            databaseTasks.getEmailSelectTask(databaseSelectionTasksListener).execute(note.getId());
            Log.e("ShowingExisting","Note is "+note.toString());
        }

    }

    @Override
    public void onNavigateUp() {
        activityNavigationTasks.closeScreen();
    }

    @Override
    public void onOptionItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.choose_color_menu:
                noteFieldScreenManipulationTasks.showColorPalate(this);
                break;
            case R.id.add_image_menu:
                fileIOTasks.openFilePickerForImage();
                break;
            case R.id.add_phone_no_menu:
                noteFieldScreenManipulationTasks.showPhoneNoOptions(this);
                break;
            case R.id.add_email_menu:
                activityNavigationTasks.toManualEmailScreen(new Bundle());
                break;
            case R.id.add_audio_menu:
                fileIOTasks.openFilePickerForAudio();
                break;

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK)
        {
            switch (requestCode)
            {
                case RequestCode.OPEN_IMAGE_FILE:
                    handleChosenImage(data);
                    break;
                case RequestCode.OPEN_CONTACT_LIST:
                    handleChosenContact(data);
                    break;
                case RequestCode.ADD_MANUAL_CONTACT:
                    handleManualContact(data);
                    break;
                case RequestCode.OPEN_AUDIO_LIST:
                    handleChosenAudio(data);
                    break;
                case RequestCode.ADD_MANUAL_EMAIL:
                    handleManualEmail(data);
                    break;
                case RequestCode.NOTE_TITLE_VOICE_INPUT:
                    handleNoteTitleVoiceInput(data);
                    break;
                case RequestCode.NOTE_TEXT_VOICE_INPUT:
                    handleNoteTextVoiceInput(data);
                    break;
            }

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode == RequestCode.READ_CONTACT_PERMISSION)
       {
           if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
           {
               executeReadingContact();
           }
           else
           {
               Log.e("Permission","Denied");
               noteFieldScreenManipulationTasks.showPermissionRequiredMessage();
           }
       }

    }

    @Override
    public void onTitleMicClicked() {
        voiceInputTasks.showVoiceInputDialog(Constants.EN,RequestCode.NOTE_TITLE_VOICE_INPUT);
    }

    @Override
    public void onNoteMicClicked() {
        voiceInputTasks.showVoiceInputDialog(Constants.EN,RequestCode.NOTE_TEXT_VOICE_INPUT);
    }

    @Override
    public void onSaveButtonClicked() {
        noteFieldScreenManipulationTasks.grabNoteValues();
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        DatabaseInsertTasksListener databaseInsertTasksListener = listeningTasks.getDBInsertTasksListener(databaseTasks, noteComponents);
        databaseTasks.getNoteInsertTask(databaseInsertTasksListener).execute(noteComponents.getNote());


    }

    @Override
    public void onExportAsButtonClicked() {

    }

    @Override
    public void onColorClicked(String colorName) {
        noteFieldScreenManipulationTasks.dismissColorPalate();
        noteFieldScreenManipulationTasks.applyBackgroundColor(colorName);
        noteComponents.getNote().setBackgroundColor(colorName);
    }

    private void handleChosenImage(Intent intent)
    {
        Uri selectedImage = intent.getData();
        Image image = fileIOTasks.getImageFromURI(selectedImage);
        if(image!=null)
        {
            noteComponents.getChosenImages().add(image);
            noteComponents.getNote().updateImagePriority();
            noteFieldScreenManipulationTasks.addImageToChosenImageContainer(image);
        }
    }

    private void handleChosenContact(Intent intent)
    {
        Contact contact = fileIOTasks.readContact(intent);
        if(contact!=null)
            noteComponents.getNote().updateContactPriority();
        noteComponents.getChosenContacts().add(contact);
        noteFieldScreenManipulationTasks.addContactToChosenContactContainer(contact);
    }

    private void handleChosenAudio(Intent data)
    {
        Uri selectedAudio = data.getData();
        Audio audio = fileIOTasks.getAudioFileFromURI(selectedAudio);
        noteFieldScreenManipulationTasks.addAudioToChosenContactContainer(audio,selectedAudio,fileIOTasks);
        if(audio!=null)
        {
            noteComponents.getNote().updateAudioPriority();
            noteComponents.getChosenAudios().add(audio);
        }

    }

    private void handleManualContact(Intent intent)
    {
        Contact contact = (Contact) intent.getSerializableExtra(Constants.MANUAL_CONTACT);
        if(contact!=null)
        {
            noteComponents.getNote().updateContactPriority();
            noteComponents.getChosenContacts().add(contact);
            noteFieldScreenManipulationTasks.addContactToChosenContactContainer(contact);
        }
    }

    private void handleManualEmail(Intent intent)
    {
        Email email= (Email) intent.getSerializableExtra(Constants.MANUAL_EMAIL);
        if(email!=null)
        {
            noteComponents.getNote().updateEmailPriority();
            noteComponents.getEmails().add(email);
            noteFieldScreenManipulationTasks.addEmailToChosenEmailContainer(email);
        }
    }

    private void handleNoteTitleVoiceInput(Intent intent)
    {
        noteFieldScreenManipulationTasks.updateNoteTitleFromVoiceInput(intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
    }

    private void handleNoteTextVoiceInput(Intent intent)
    {
        noteFieldScreenManipulationTasks.updateNoteTextFromVoiceInput(intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
    }


    public void onPostCreate() {
        noteFieldScreenView.initUserInteractions();
    }


    @Override
    public void onPhoneNoOptionSelected(int phoneNoOption) {
        noteFieldScreenManipulationTasks.dismissPhoneNoOptions();
        switch (phoneNoOption)
        {
            case EventTypes.MANUAL_PHONE_NO_OPTION_CLICKED:
                activityNavigationTasks.toManualContactScreen(new Bundle());
                break;
            case EventTypes.PICK_FROM_CONTACT_OPTION_CLICKED:
                executeReadingContact();
                break;
        }
    }

    private void executeReadingContact()
    {
        if(appPermissionTrackingTasks.hasContactReadPermission())
        {
            fileIOTasks.openContactPicker();
        }
    }
}
