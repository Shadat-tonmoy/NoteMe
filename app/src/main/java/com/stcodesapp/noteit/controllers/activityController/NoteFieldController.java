package com.stcodesapp.noteit.controllers.activityController;

import android.content.DialogInterface;
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
import com.stcodesapp.noteit.constants.PermissionType;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.controllers.adController.NoteFieldAdController;
import com.stcodesapp.noteit.factory.ListeningTasks;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.listeners.DatabaseInsertTasksListener;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.AudioDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.ContactDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.EmailDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.ImageDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.AudioInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.CheckListInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ContactInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.EmailInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ImageInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.NoteComponentSelectionTask;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.AdStrategyTrackingTask;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.phoneFunctionAccessTasks.ImageCapturingTask;
import com.stcodesapp.noteit.tasks.functionalTasks.phoneFunctionAccessTasks.VoiceInputTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.phoneFunctionAccessTasks.VoiceRecordTask;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.RateUSPopupTrackingTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks.NoteFieldScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;
import com.stcodesapp.noteit.ui.fragments.AudioOptionsBottomSheets;
import com.stcodesapp.noteit.ui.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.ui.fragments.ImageOptionsBottomSheets;
import com.stcodesapp.noteit.ui.fragments.PhoneNoOptionsBottomSheets;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.NoteFieldScreen;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.stcodesapp.noteit.constants.Constants.SINGLE_CHECKLIST;

public class NoteFieldController implements NoteFieldScreen.Listener,ColorPallateBottomSheets.Listener, PhoneNoOptionsBottomSheets.Listener,NoteComponentSelectionTask.Listener, ContactDeleteTask.Listener, EmailDeleteTask.Listener, ImageDeleteTask.Listener, AudioDeleteTask.Listener, DialogInterface.OnClickListener, AudioOptionsBottomSheets.Listener, ImageOptionsBottomSheets.Listener
{

    private TasksFactory tasksFactory;
    private ListeningTasks listeningTasks;
    private ActivityNavigationTasks activityNavigationTasks;
    private NoteFieldScreenView noteFieldScreenView;
    private NoteFieldScreenManipulationTasks noteFieldScreenManipulationTasks;
    private FileIOTasks fileIOTasks;
    private AppPermissionTrackingTasks appPermissionTrackingTasks;
    private VoiceInputTasks voiceInputTasks;
    private NoteComponents noteComponents;
    private boolean isUpdating = false;
    private ImageCapturingTask imageCapturingTask;
    private AdStrategyTrackingTask adStrategyTrackingTask;
    private NoteFieldAdController noteFieldAdController;

    public NoteFieldController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        activityNavigationTasks = tasksFactory.getActivityNavigationTasks();
        noteFieldScreenManipulationTasks = tasksFactory.getNoteFieldScreenManipulationTasks();
        fileIOTasks = tasksFactory.getFileIOTasks();
        appPermissionTrackingTasks = tasksFactory.getAppPermissionTrackingTasks();
        voiceInputTasks = tasksFactory.getVoiceInputTasks();
        listeningTasks = tasksFactory.getListeningTasks();
        imageCapturingTask = tasksFactory.getImageCapturingTask();
        adStrategyTrackingTask = tasksFactory.getAdStrategyTrackingTask();

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

    public void bindNoteFieldAdController(NoteFieldAdController noteFieldAdController) {
        this.noteFieldAdController = noteFieldAdController;
    }

    public void onStart()
    {
        noteFieldScreenView.registerListener(this);
    }

    public void onStop()
    {
        noteFieldScreenView.unregisterListener(this);
    }

    public void onBackPressed()
    {
        if(isUpdating)
        {
            noteFieldScreenManipulationTasks.grabNoteValues();
            updateNote(noteComponents.getNote());
            Intent result = new Intent();
            result.putExtra(Tags.NOTE_UPDATED,true);
            activityNavigationTasks.sendResultBack(result);

        }
        else
        {
            noteFieldScreenManipulationTasks.grabNoteValues();
            if(tasksFactory.getNoteFieldValidationTask(noteComponents).isValidNote())
            {
                noteFieldScreenManipulationTasks.showSavePromptDialog(this);
            }
            else
            {
                activityNavigationTasks.closeScreen();
            }
        }
    }


    public void checkBundleForNote(Bundle args)
    {
        Note note = (Note) args.getSerializable(Tags.NOTE);
        if(note!=null)
        {
            noteComponents.setNote(note);
            NoteComponentSelectionTask noteComponentSelectionTask = tasksFactory.getDatabaseTasks().getNoteComponentSelectionTask(this,noteComponents);
            noteComponentSelectionTask.execute(note.getId());
            isUpdating = true;
            noteFieldScreenManipulationTasks.hideDoneButton();

            /*DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
            DatabaseSelectionTasksListener databaseSelectionTasksListener= listeningTasks.getDBSelectTasksListener(databaseTasks,noteComponents);
            databaseSelectionTasksListener.setListener(this);
            databaseTasks.getEmailSelectTask(databaseSelectionTasksListener).execute(note.getId());*/
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
                noteFieldScreenManipulationTasks.showImageOptions(this);
                break;
            case R.id.add_phone_no_menu:
                noteFieldScreenManipulationTasks.showPhoneNoOptions(this);
                break;
            case R.id.add_email_menu:
                activityNavigationTasks.toManualEmailScreen(new Bundle());
                break;
            case R.id.add_audio_menu:
                noteFieldScreenManipulationTasks.showAudioOptions(this);
                break;
            case R.id.add_checklist_menu:
                Bundle args = new Bundle();
                args.putLong(Tags.NOTE_ID,noteComponents.getNote().getId());
                activityNavigationTasks.toCheckListScreen(args);
                break;

        }


    }

    private void startVoiceRecorder()
    {
        if(appPermissionTrackingTasks.hasWriteExternalStoragePermission())
        {
            VoiceRecordTask voiceRecordTask = tasksFactory.getVoiceRecordTask();
            voiceRecordTask.startVoiceRecorder();
        }
    }

    private void testExport()
    {
        tasksFactory.getPDFCreationTasks().createPDF(noteComponents);
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
                case RequestCode.OPEN_CAMERA_TO_TAKE_IMAGE:
                    handleCapturedImage(data);
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
                case RequestCode.OPEN_VOICE_RECORDER:
                    handleRecordedVoice(data);
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
                case RequestCode.ADD_SINGLE_CHECKLIST:
                    handleSingleCheckList(data);
                    break;
            }

        }


    }

    private void handleRecordedVoice(Intent data) {
        Uri selectedAudio = data.getData();
        Bundle args = new Bundle();
        args.putString(Tags.INPUT_FILE_PATH,selectedAudio.getPath());
        noteFieldScreenManipulationTasks.showFileSavingDialog(args);

    }

    public void saveRecordedAudioFileToDB(File file)
    {
        Audio audio = new Audio(file.getName(),String.valueOf(file.length()), file.getAbsolutePath(), true);
        audio.setNoteId(noteComponents.getNote().getId());
        noteFieldScreenManipulationTasks.addAudioToChosenContactContainer(audio,Uri.parse(audio.getAudioUri()),fileIOTasks);
        if(audio!=null)
        {
            noteComponents.getNote().updateAudioPriority();
            noteComponents.getChosenAudios().add(audio);
            if(isUpdating)
                addChosenAudioToDB(audio);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode == RequestCode.READ_CONTACT_PERMISSION)
       {
           if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
               executeReadingContact();
           else noteFieldScreenManipulationTasks.showPermissionRequiredMessage(PermissionType.CONTACT_READ_PERMISSION);
       }
       else if(requestCode == RequestCode.READ_EXTERNAL_STORAGE_PERMISSION)
       {
           if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
               noteFieldScreenManipulationTasks.addImageToFields();
           else noteFieldScreenManipulationTasks.showPermissionRequiredMessage(PermissionType.IMAGE_READ_PERMISSION);
       }
       else if(requestCode == RequestCode.OPEN_CAMERA_PERMISSION)
       {
           if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
               openCameraToTakeImage();
           else noteFieldScreenManipulationTasks.showPermissionRequiredMessage(PermissionType.CAMERA_ACCESS_PERMISSION);
       }
       else if(requestCode == RequestCode.WRITE_EXTERNAL_STORAGE_PERMISSION)
       {
           if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
               startVoiceRecorder();
           else
               noteFieldScreenManipulationTasks.showPermissionRequiredMessage(PermissionType.WRITE_EXTERNAL_STORAGE);
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
        if(tasksFactory.getNoteFieldValidationTask(noteComponents).isValidNote())
        {
            DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
            DatabaseInsertTasksListener databaseInsertTasksListener = listeningTasks.getDBInsertTasksListener(databaseTasks, noteComponents,false);
            databaseTasks.getNoteInsertTask(databaseInsertTasksListener).execute(noteComponents.getNote());
            Intent result = new Intent();
            result.putExtra(Tags.NOTE_ADDED,true);
            activityNavigationTasks.sendResultBack(result);

        }
        else
        {
            noteFieldScreenManipulationTasks.showInvalidNoteToast();
        }
    }

    private void updateRateUSTrackingCount()
    {
        RateUSPopupTrackingTasks rateUSPopupTrackingTasks = tasksFactory.getRateUSPopupTrackingTasks();
        rateUSPopupTrackingTasks.updateTotalNoteCount();
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

    private void updateNote(Note note)
    {
        tasksFactory.getDatabaseTasks().getNoteUpdateTask().execute(note);
    }

    private void handleChosenImage(Intent intent)
    {
        Uri selectedImage = intent.getData();
        Image image = fileIOTasks.getImageFromURI(selectedImage,false);
        if(image!=null)
        {
            noteComponents.getChosenImages().add(image);
            noteComponents.getNote().updateImagePriority();
            image.setNoteId(noteComponents.getNote().getId());
            noteFieldScreenManipulationTasks.addImageToChosenImageContainer(image);
            if(isUpdating)
                addChosenImageToDB(image);
        }
    }

    private void handleCapturedImage(Intent intent)
    {
//        Uri selectedImage = (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
        Uri selectedImage = imageCapturingTask.getCapturedImageURI();
        Log.e("CapturedImage",selectedImage.toString());
        Image image = fileIOTasks.getImageFromURI(selectedImage,true);
        Log.e("CapturedImage"," File "+selectedImage.toString());
        if(image!=null)
        {
            noteComponents.getChosenImages().add(image);
            noteComponents.getNote().updateImagePriority();
            image.setNoteId(noteComponents.getNote().getId());
            noteFieldScreenManipulationTasks.addImageToChosenImageContainer(image);
            if(isUpdating)
                addChosenImageToDB(image);
        }
    }


    private void addChosenImageToDB(Image image)
    {
        image.setNoteId(noteComponents.getNote().getId());
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        DatabaseInsertTasksListener databaseInsertTasksListener = listeningTasks.getDBInsertTasksListener(databaseTasks,noteComponents,true);
        ImageInsertTask imageInsertTask = tasksFactory.getDatabaseTasks().getImageInsertTask(databaseInsertTasksListener);
        imageInsertTask.execute(image);
    }

    private void handleChosenContact(Intent intent)
    {
        Contact contact = fileIOTasks.readContact(intent);
        if(contact!=null)
        {
            noteComponents.getNote().updateContactPriority();
            noteComponents.getChosenContacts().add(contact);
            contact.setNoteId(noteComponents.getNote().getId());
            noteFieldScreenManipulationTasks.addContactToChosenContactContainer(contact);
            if(isUpdating)
                addChosenContactToDB(contact);
        }

    }

    private void handleManualContact(Intent intent)
    {
        Contact contact = (Contact) intent.getSerializableExtra(Constants.MANUAL_CONTACT);
        if(contact!=null)
        {
            noteComponents.getNote().updateContactPriority();
            noteComponents.getChosenContacts().add(contact);
            contact.setNoteId(noteComponents.getNote().getId());
            noteFieldScreenManipulationTasks.addContactToChosenContactContainer(contact);
            if(isUpdating)
                addChosenContactToDB(contact);
        }
    }

    private void addChosenContactToDB(Contact contact)
    {
        contact.setNoteId(noteComponents.getNote().getId());
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        DatabaseInsertTasksListener databaseInsertTasksListener = listeningTasks.getDBInsertTasksListener(databaseTasks,noteComponents,true);
        ContactInsertTask contactInsertTask = tasksFactory.getDatabaseTasks().getContactInsertTask(databaseInsertTasksListener);
        contactInsertTask.execute(contact);
    }

    private void handleChosenAudio(Intent data)
    {
        Uri selectedAudio = data.getData();
        Audio audio = fileIOTasks.getAudioFileFromURI(selectedAudio);
        audio.setNoteId(noteComponents.getNote().getId());
        noteFieldScreenManipulationTasks.addAudioToChosenContactContainer(audio,selectedAudio,fileIOTasks);
        if(audio!=null)
        {
            noteComponents.getNote().updateAudioPriority();
            noteComponents.getChosenAudios().add(audio);
            if(isUpdating)
                addChosenAudioToDB(audio);
        }
    }

    private void addChosenAudioToDB(Audio audio)
    {
        audio.setNoteId(noteComponents.getNote().getId());
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        DatabaseInsertTasksListener databaseInsertTasksListener = listeningTasks.getDBInsertTasksListener(databaseTasks,noteComponents,true);
        AudioInsertTask audioInsertTask = tasksFactory.getDatabaseTasks().getAudioInsertTask(databaseInsertTasksListener);
        audioInsertTask.execute(audio);
    }

    private void handleManualEmail(Intent intent)
    {
        Email email= (Email) intent.getSerializableExtra(Constants.MANUAL_EMAIL);
        if(email!=null)
        {
            noteComponents.getNote().updateEmailPriority();
            noteComponents.getEmails().add(email);
            email.setNoteId(noteComponents.getNote().getId());
            noteFieldScreenManipulationTasks.addEmailToChosenEmailContainer(email);
            if(isUpdating)
                addChosenEmailToDB(email);
        }
    }


    private void handleSingleCheckList(Intent intent)
    {
        Bundle bundle = intent.getBundleExtra(Constants.SINGLE_CHECKLIST);
        int checkListPosition = intent.getIntExtra(Tags.CHECK_LIST_POSITION,Constants.ZERO);
        boolean invalidNote = intent.getBooleanExtra(Tags.INVALID_NOTE,false);
        CheckList checkList = (CheckList) bundle.getSerializable(SINGLE_CHECKLIST);
        if(checkList!=null)
        {
            if(!invalidNote)
            {
                noteComponents.getNote().updateCheckListPriority();
                noteComponents.getCheckLists().add(checkList);
                checkList.setNoteId(noteComponents.getNote().getId());
                noteFieldScreenManipulationTasks.addCheckListToCheckListContainer(checkList);
                if(isUpdating)
                    addCheckListToDB(checkList);
            }
            else
            {
                noteComponents.getCheckLists().set(checkListPosition-1,checkList);
                noteFieldScreenManipulationTasks.updateCheckListAtPosition(checkList,checkListPosition);
            }
        }

    }


    private void addChosenEmailToDB(Email email)
    {
        email.setNoteId(noteComponents.getNote().getId());
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        DatabaseInsertTasksListener databaseInsertTasksListener = listeningTasks.getDBInsertTasksListener(databaseTasks,noteComponents,true);
        EmailInsertTask emailInsertTask = tasksFactory.getDatabaseTasks().getEmailInsertTask(databaseInsertTasksListener);
        emailInsertTask.execute(email);
    }

    private void addCheckListToDB(CheckList checkList)
    {
        checkList.setNoteId(noteComponents.getNote().getId());
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        DatabaseInsertTasksListener databaseInsertTasksListener = listeningTasks.getDBInsertTasksListener(databaseTasks,noteComponents,true);
        CheckListInsertTask checkListInsertTask = tasksFactory.getDatabaseTasks().getCheckListInsertTask(databaseInsertTasksListener);
        checkListInsertTask.execute(checkList);
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

    private void openCameraToTakeImage()
    {
        imageCapturingTask.openCameraToTakeImage();
    }

    @Override
    public void onNoteComponentFetched(NoteComponents noteComponents) {
        noteFieldScreenManipulationTasks.bindNoteComponents(noteComponents);
        noteFieldScreenManipulationTasks.buildUIFromNoteComponents(fileIOTasks);

    }

    @Override
    public void onContactDeleted(Contact contact)
    {
        noteComponents.getChosenContacts().remove(contact);
        noteFieldScreenManipulationTasks.removeContactContainer();
        if(noteComponents.getChosenContacts().size()==Constants.ZERO)
        {
            noteComponents.getNote().setContactPriority(Constants.ZERO);
            updateNote(noteComponents.getNote());
        }
    }

    @Override
    public void onEmailDeleted(Email email) {
        noteComponents.getEmails().remove(email);
        if(noteComponents.getEmails().size()==Constants.ZERO)
        {
            noteComponents.getNote().setEmailPriority(Constants.ZERO);
            updateNote(noteComponents.getNote());
        }
        noteFieldScreenManipulationTasks.removeEmailContainer();

    }

    @Override
    public void onImageDeleted(Image image) {
        noteComponents.getChosenImages().remove(image);
        noteFieldScreenManipulationTasks.removeImageContainer();
    }

    @Override
    public void onAudioDeleted(Audio audio) {
        noteComponents.getChosenAudios().remove(audio);
        noteFieldScreenManipulationTasks.removeAudioContainer();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which)
        {
            case DialogInterface.BUTTON_POSITIVE:
                onSaveButtonClicked();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                activityNavigationTasks.closeScreen();
                break;

        }

    }

    @Override
    public void onAudioOptionSelected(int audioOption) {
        noteFieldScreenManipulationTasks.dismissAudioOptions();
        switch (audioOption)
        {
            case EventTypes.RECORD_AUDIO_OPTION_CLICKED:
                startVoiceRecorder();
                break;
            case EventTypes.PICK_AUDIO_FROM_FILE_OPTION_CLICKED:
                fileIOTasks.openFilePickerForAudio();
                break;
        }
    }

    @Override
    public void onImageOptionSelected(int imageOption) {
        noteFieldScreenManipulationTasks.dismissImageOptions();
        switch (imageOption)
        {
            case EventTypes.TAKE_PHOTO_OPTION_CLICKED:
                openCameraToTakeImage();
                break;
            case EventTypes.PICK_IMAGE_FROM_FILE_OPTION_CLICKED:
                fileIOTasks.openFilePickerForImage();
                break;
        }

    }
}
