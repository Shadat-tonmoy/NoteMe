package com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.ravikoradiya.zoomableimageview.ZoomableImageView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.BackgroundColors;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.constants.PermissionType;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.factory.ListeningTasks;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.factory.UIComponentFatory;
import com.stcodesapp.noteit.listeners.AudioListener;
import com.stcodesapp.noteit.listeners.CheckListListener;
import com.stcodesapp.noteit.listeners.ContactListener;
import com.stcodesapp.noteit.listeners.EmailListener;
import com.stcodesapp.noteit.listeners.ImageListener;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.functionalTasks.DialogManagementTask;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileDeletingTask;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;
import com.stcodesapp.noteit.ui.fragments.AudioOptionsBottomSheets;
import com.stcodesapp.noteit.ui.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.ui.fragments.FileSaveDialog;
import com.stcodesapp.noteit.ui.fragments.ImageOptionsBottomSheets;
import com.stcodesapp.noteit.ui.fragments.PhoneNoOptionsBottomSheets;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

import java.io.File;
import java.io.IOException;


public class NoteFieldScreenManipulationTasks {

    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;
    private ColorPallateBottomSheets colorPallateBottomSheets;
    private PhoneNoOptionsBottomSheets phoneNoOptionsBottomSheets;
    private AudioOptionsBottomSheets audioOptionsBottomSheets;
    private ImageOptionsBottomSheets imageOptionsBottomSheets;
    private ListeningTasks listeningTasks;
    private UIComponentFatory uiComponentFatory;
    private NoteComponents noteComponents;
    private FileIOTasks fileIOTasks;
    private TasksFactory tasksFactory;

    public NoteFieldScreenManipulationTasks(Activity activity, ListeningTasks listeningTasks, UIComponentFatory uiComponentFatory, TasksFactory tasksFactory) {
        this.activity = activity;
        this.listeningTasks = listeningTasks;
        this.uiComponentFatory = uiComponentFatory;
        this.tasksFactory= tasksFactory;
    }

    public void bindView(NoteFieldScreenView noteFieldScreenView) {
        this.noteFieldScreenView = noteFieldScreenView;
    }

    public void bindNoteComponents(NoteComponents noteComponents) {
        this.noteComponents = noteComponents;
    }

    public void showColorPalate(ColorPallateBottomSheets.Listener listener)
    {
        colorPallateBottomSheets =  uiComponentFatory.getColorPallateBottomSheets();
        colorPallateBottomSheets.setListener(listener);
        colorPallateBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(),"TAG");
    }

    public void showExportOption()
    {
        Toast.makeText(activity,"I am getting ready",Toast.LENGTH_SHORT).show();
    }

    public void dismissColorPalate()
    {
        if(colorPallateBottomSheets!=null && colorPallateBottomSheets.isVisible())
            colorPallateBottomSheets.dismiss();
    }

    public void applyBackgroundColor(String colorName)
    {
        try {
            noteFieldScreenView.getUiRoot().setBackgroundColor(activity.getResources().getColor(BackgroundColors.getColorMaps().get(colorName)));
            noteFieldScreenView.getUiRoot().getBackground().setAlpha(Constants.BACKGROUND_OPACITY);
        }catch (Exception e)
        {
//            Log.e("Exception",e.getMessage());
        }
    }

    public void addImageToChosenImageContainer(final Image image)
    {
//        Log.e("TryingToAdd","Image"+image.toString());
        FlexboxLayout imageContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_image_container);
        if(imageContainer==null)
        {
            imageContainer = (activity.getLayoutInflater().inflate(R.layout.image_container,null,false)).findViewById(R.id.chosen_image_container);
            noteFieldScreenView.getUiComponentContainer().addView(imageContainer);
        }
        if(imageContainer.getVisibility()==View.GONE)
            imageContainer.setVisibility(View.VISIBLE);
        final View imageHolder = activity.getLayoutInflater().inflate(R.layout.image_holder,null,false);
        ZoomableImageView imageView = imageHolder.findViewById(R.id.image);
        ImageView removeIcon = imageHolder.findViewById(R.id.remove_image);
        imageView.invalidate();
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(image.getImageURI()));
            Glide.with(activity)
                    .load(Uri.parse(image.getImageURI()))
                    .centerCrop()
                    .placeholder(activity.getResources().getDrawable(R.drawable.placeholder_image))
                    .into(imageView);
//            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
//            Log.e("Exception",e.getMessage());
        }
        imageContainer.addView(imageHolder);
        ImageListener imageListener = listeningTasks.getImageListener(image,imageHolder);
        if(image.getNoteId()!=Constants.ZERO)
        {
            removeIcon.setOnClickListener(imageListener);
        }
        else
        {
            removeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeImageComponent(imageHolder,image);
                    if(image.isCaptured())
                    {
                        FileDeletingTask fileDeletingTask = tasksFactory.getFileDeletingTask(new FileDeletingTask.Listener() {
                            @Override
                            public void onFileDeleted(File file) {
//                                Log.e("FileDeletedImage", file.getAbsolutePath());
                            }
                        });
                        fileDeletingTask.execute(new File(image.getImageFilePath()));
                    }

                }
            });
        }
    }

    public void addContactToChosenContactContainer(final Contact contact)
    {
        LinearLayout contactContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_contact_container);
        if(contactContainer==null)
        {
            contactContainer = (activity.getLayoutInflater().inflate(R.layout.contact_container,null,false)).findViewById(R.id.chosen_contact_container);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 16, 0, 0);
            contactContainer.setLayoutParams(params);
            noteFieldScreenView.getUiComponentContainer().addView(contactContainer);
        }
        if(contactContainer.getVisibility()==View.GONE)
            contactContainer.setVisibility(View.VISIBLE);
        final View contactHolder = activity.getLayoutInflater().inflate(R.layout.contact_holder,null,false);
        TextView callButton = contactHolder.findViewById(R.id.contact_call_btn);
        TextView copyButton = contactHolder.findViewById(R.id.contact_copy_btn);
        TextView removeButton = contactHolder.findViewById(R.id.contact_remove_btn);
        TextView contactNo = contactHolder.findViewById(R.id.contact_no);
        TextView displayName = contactHolder.findViewById(R.id.contact_name);
        contactNo.setText(contact.getPhoneNumber());
        displayName.setText(contact.getDisplayName());
        contactContainer.addView(contactHolder);
        ContactListener contactListener= listeningTasks.getContactListener(contact,contactHolder);
        callButton.setOnClickListener(contactListener);
        copyButton.setOnClickListener(contactListener);
        if(contact.getNoteId()!=Constants.ZERO)
        {
            removeButton.setOnClickListener(contactListener);
        }
        else
        {
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeContactComponent(contactHolder,contact);

                }
            });
        }
    }


    private void removeContactComponent(View view,Contact contact)
    {
        noteComponents.getChosenContacts().remove(contact);
        view.setVisibility(View.GONE);
        removeContactContainer();
    }

    private void removeEmailComponent(View view,Email email)
    {
        noteComponents.getEmails().remove(email);
        view.setVisibility(View.GONE);
        removeEmailContainer();
    }

    private void removeCheckListComponent(View view,CheckList checkList)
    {
        noteComponents.getCheckLists().remove(checkList);
        view.setVisibility(View.GONE);
        removeCheckListContainer();
    }

    private void removeImageComponent(View view,Image image)
    {
        noteComponents.getChosenImages().remove(image);
        view.setVisibility(View.GONE);

        removeImageContainer();
    }

    private void removeAudioComponent(View view,Audio audio)
    {
        noteComponents.getChosenAudios().remove(audio);
        view.setVisibility(View.GONE);
        removeAudioContainer();
    }


    public void removeEmailContainer()
    {
        if(noteComponents.getEmails().size()==0)
        {
            LinearLayout emailContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_email_container);
            if(emailContainer!=null)
            {
                emailContainer.setVisibility(View.GONE);
            }
        }
    }

    public void removeCheckListContainer()
    {
        if(noteComponents.getCheckLists().size()==0)
        {
            LinearLayout checkListContainer = noteFieldScreenView.getRootView().findViewById(R.id.check_list_container);
            if(checkListContainer!=null)
            {
                checkListContainer.setVisibility(View.GONE);
            }
        }
    }

    public void removeContactContainer()
    {
        if(noteComponents.getChosenContacts().size()==0)
        {
            LinearLayout contactContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_contact_container);
            if(contactContainer!=null)
            {
                contactContainer.setVisibility(View.GONE);
            }
        }
    }


    public void removeAudioContainer()
    {
        if(noteComponents.getChosenAudios().size()==0)
        {
            LinearLayout audioContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_audio_container);
            if(audioContainer!=null)
            {
                audioContainer.setVisibility(View.GONE);
            }
        }
    }

    public void removeImageContainer()
    {
        if(noteComponents.getChosenImages().size()==0)
        {
            FlexboxLayout imageContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_image_container);
            if(imageContainer!=null)
            {
                imageContainer.setVisibility(View.GONE);
            }
        }
    }


    public void addEmailToChosenEmailContainer(final Email email)
    {
        LinearLayout emailContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_email_container);
        if(emailContainer==null)
        {
            emailContainer = (activity.getLayoutInflater().inflate(R.layout.email_container,null,false)).findViewById(R.id.chosen_email_container);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 16, 0, 0);
            emailContainer.setLayoutParams(params);
            noteFieldScreenView.getUiComponentContainer().addView(emailContainer);
        }
        if(emailContainer.getVisibility()==View.GONE)
            emailContainer.setVisibility(View.VISIBLE);
        final View emailHolder = activity.getLayoutInflater().inflate(R.layout.email_holder,null,false);
        TextView sendButton = emailHolder.findViewById(R.id.email_send_btn);
        TextView copyButton = emailHolder.findViewById(R.id.email_copy_btn);
        TextView removeButton = emailHolder.findViewById(R.id.email_remove_btn);
        TextView emailId = emailHolder.findViewById(R.id.email_id);
        TextView emailName = emailHolder.findViewById(R.id.email_name);
        emailId.setText(email.getEmailID());
        emailName.setText(email.getEmailName());
        emailContainer.addView(emailHolder);

        EmailListener emailListener = listeningTasks.getEmailListener(email,emailHolder);
        sendButton.setOnClickListener(emailListener);
        copyButton.setOnClickListener(emailListener);
        if(email.getNoteId()!=Constants.ZERO)
        {
            removeButton.setOnClickListener(emailListener);
        }
        else
        {
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeEmailComponent(emailHolder,email);

                }
            });
        }
    }


    public void addCheckListToCheckListContainer(final CheckList checklist)
    {
        LinearLayout checkListContainer = noteFieldScreenView.getRootView().findViewById(R.id.check_list_container);
        if(checkListContainer==null)
        {
            checkListContainer = (activity.getLayoutInflater().inflate(R.layout.check_list_container,null,false)).findViewById(R.id.check_list_container);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 16, 0, 0);
            checkListContainer.setLayoutParams(params);
            noteFieldScreenView.getUiComponentContainer().addView(checkListContainer);
        }
        if(checkListContainer.getVisibility()==View.GONE)
            checkListContainer.setVisibility(View.VISIBLE);
        final View checkListHolder = activity.getLayoutInflater().inflate(R.layout.check_list_holder,null,false);
        ConstraintLayout mainLayout = checkListHolder.findViewById(R.id.checklist_holder_main_layout);
        TextView removeButton = checkListHolder.findViewById(R.id.check_list_remove_btn);
        TextView checkListName = checkListHolder.findViewById(R.id.check_list_title);
        checkListName.setText(checklist.getCheckListTitle());
        checkListContainer.addView(checkListHolder);

        CheckListListener checkListListener = listeningTasks.getCheckListListener(checklist,checkListHolder,checkListContainer.getChildCount()-1);
        mainLayout.setOnClickListener(checkListListener);
        if(checklist.getNoteId()!=Constants.ZERO)
        {
            removeButton.setOnClickListener(checkListListener);
        }
        else
        {
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeCheckListComponent(checkListHolder,checklist);

                }
            });
        }
    }



    public void updateCheckListAtPosition(final CheckList checklist,int position)
    {
        LinearLayout checkListContainer = noteFieldScreenView.getRootView().findViewById(R.id.check_list_container);
        final View checkListHolder = checkListContainer.getChildAt(position);
        ConstraintLayout mainLayout = checkListHolder.findViewById(R.id.checklist_holder_main_layout);
        TextView removeButton = checkListHolder.findViewById(R.id.check_list_remove_btn);
        TextView checkListName = checkListHolder.findViewById(R.id.check_list_title);
        checkListName.setText(checklist.getCheckListTitle());

        CheckListListener checkListListener = listeningTasks.getCheckListListener(checklist,checkListHolder,checkListContainer.getChildCount()-1);
        mainLayout.setOnClickListener(checkListListener);
        if(checklist.getNoteId()!=Constants.ZERO)
        {
            removeButton.setOnClickListener(checkListListener);
        }
        else
        {
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeCheckListComponent(checkListHolder,checklist);

                }
            });
        }
    }

    public void addAudioToChosenContactContainer(Audio audio,Uri audioUri, FileIOTasks fileIOTasks)
    {
        LinearLayout audioContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_audio_container);
        Audio audioFromUri = fileIOTasks.getAudioFileFromURI(audioUri);
        if(audioFromUri==null && !audio.isFilePath())
        {
            return;
        }
        if(!audio.isFilePath())
        {
            audioFromUri.setId(audio.getId());
            audioFromUri.setNoteId(audio.getNoteId());
            audio = audioFromUri;
        }
        else
        {
            File audioFile = new File(audio.getAudioUri());
            audio.setAudioTitle(audioFile.getName());
            audio.setAudioSize(String.valueOf(audioFile.length()));

        }
        if(audioContainer==null)
        {
            audioContainer = (activity.getLayoutInflater().inflate(R.layout.audio_container,null,false)).findViewById(R.id.chosen_audio_container);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 16, 0, 0);
            audioContainer.setLayoutParams(params);
            noteFieldScreenView.getUiComponentContainer().addView(audioContainer);
        }
        if(audioContainer.getVisibility()==View.GONE)
            audioContainer.setVisibility(View.VISIBLE);
        final View audioHolder = activity.getLayoutInflater().inflate(R.layout.audio_holder,null,false);
        View audioHolderRow = audioHolder.findViewById(R.id.audio_holder);
        TextView audioTitle = audioHolder.findViewById(R.id.audio_title);
        TextView audioSize = audioHolder.findViewById(R.id.audio_size);
        TextView removeIcon = audioHolder.findViewById(R.id.audio_remove_btn);
        audioTitle.setText(UtilityTasks.truncateText(audio.getAudioTitle(),Constants.MAX_AUDIO_FILE_NAME_LENGTH,Constants.MP3_FILE_EXT));
        audioSize.setText(UtilityTasks.getFileSizeString(Double.parseDouble(audio.getAudioSize())));
        audioContainer.addView(audioHolder);
        AudioListener audioListener = listeningTasks.getAudioListener(audio,audioUri,audioHolder);
        audioHolderRow.setOnClickListener(audioListener);
        if(audio.getNoteId()!=Constants.ZERO)
        {
            removeIcon.setOnClickListener(audioListener);
        }
        else
        {
            final Audio finalAudio = audio;
            removeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAudioComponent(audioHolder, finalAudio);
                    if(finalAudio.isFilePath())
                    {
                        FileDeletingTask fileDeletingTask = tasksFactory.getFileDeletingTask(new FileDeletingTask.Listener() {
                            @Override
                            public void onFileDeleted(File file) {
//                                Log.e("FileDeletedAudio", file.getAbsolutePath());
                            }
                        });
                        fileDeletingTask.execute(new File(finalAudio.getAudioUri()));
                    }
                }
            });
        }
    }

    public void showPhoneNoOptions(PhoneNoOptionsBottomSheets.Listener listener)
    {
        phoneNoOptionsBottomSheets =  uiComponentFatory.getphoneNoOptionsBottomSheets();
        phoneNoOptionsBottomSheets.setListener(listener);
        phoneNoOptionsBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(),FragmentTags.CONTACT_OPTIONS);
    }
    public void dismissPhoneNoOptions()
    {
        if(phoneNoOptionsBottomSheets!=null && phoneNoOptionsBottomSheets.isVisible())
            phoneNoOptionsBottomSheets.dismiss();
    }


    public void showAudioOptions(AudioOptionsBottomSheets.Listener listener)
    {
        audioOptionsBottomSheets =  uiComponentFatory.getAudioOptionsBottomSheets();
        audioOptionsBottomSheets.setListener(listener);
        audioOptionsBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(),FragmentTags.AUDIO_OPTIONS);
    }

    public void dismissAudioOptions()
    {
        if(audioOptionsBottomSheets!=null && audioOptionsBottomSheets.isVisible())
            audioOptionsBottomSheets.dismiss();
    }

    public void showImageOptions(ImageOptionsBottomSheets.Listener listener)
    {
        imageOptionsBottomSheets =  uiComponentFatory.getImageOptionsBottomSheets();
        imageOptionsBottomSheets.setListener(listener);
        imageOptionsBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(),FragmentTags.AUDIO_OPTIONS);
    }

    public void dismissImageOptions()
    {
        if(imageOptionsBottomSheets!=null && imageOptionsBottomSheets.isVisible())
            imageOptionsBottomSheets.dismiss();
    }

    public void showPermissionRequiredMessage(PermissionType permissionType)
    {
        String message = activity.getResources().getString(R.string.permission_is_required);
        switch (permissionType)
        {
            case CONTACT_READ_PERMISSION:
                message = activity.getResources().getString(R.string.contact_permission_is_required);
                break;
            case IMAGE_READ_PERMISSION:
                message = activity.getResources().getString(R.string.storage_permission_is_required);
                break;
            case CAMERA_ACCESS_PERMISSION:
                message = activity.getResources().getString(R.string.camera_permission_is_required);
                break;
            case WRITE_EXTERNAL_STORAGE:
                message = activity.getResources().getString(R.string.write_storage_permission_is_required);
                break;
        }
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void updateNoteTitleFromVoiceInput(String text) {
        String existingText = noteFieldScreenView.getNoteTitleField().getText().toString();
        existingText += text+Constants.SPACE;
        noteFieldScreenView.getNoteTitleField().setText(existingText);
        noteFieldScreenView.getNoteTitleField().setSelection(existingText.length());
    }

    public void updateNoteTextFromVoiceInput(String text) {
        String existingText = noteFieldScreenView.getNoteTextField().getText().toString();
        existingText += text+Constants.SPACE;;
        noteFieldScreenView.getNoteTextField().setText(existingText);
        noteFieldScreenView.getNoteTextField().setSelection(existingText.length());
    }

    public void grabNoteValues()
    {
        String title = noteFieldScreenView.getNoteTitleField().getText().toString();
        String text = noteFieldScreenView.getNoteTextField().getText().toString();
        noteComponents.getNote().setNoteTitle(title);
        noteComponents.getNote().setNoteText(text);
        noteComponents.getNote().setCreationTime(UtilityTasks.getCurrentTime());
    }

    public void buildUIFromNoteComponents(FileIOTasks fileIOTasks)
    {
        this.fileIOTasks = fileIOTasks;
        setNoteFieldValue();
        addComponentByPriority(0);
        addComponentByPriority(1);
        addComponentByPriority(2);
        addComponentByPriority(3);
        addComponentByPriority(4);
        addComponentByPriority(5);
    }

    private void addComponentByPriority(int priority)
    {
        if(noteComponents.getNote().getContactPriority()==priority)
            addContactsToField();
        else if(noteComponents.getNote().getEmailPriority()==priority)
            addEmailsToField();
        else if(noteComponents.getNote().getAudioPriority()==priority)
            addAudioToField();
        else if(noteComponents.getNote().getImagePriority()==priority)
            addImageToFields();
        else if(noteComponents.getNote().getCheckListPriority()==priority)
            addCheckListToFields();
    }


    private void addEmailsToField()
    {
        for(Email email:noteComponents.getEmails())
            addEmailToChosenEmailContainer(email);
    }


    private void addCheckListToFields()
    {
        for(CheckList checkList:noteComponents.getCheckLists())
            addCheckListToCheckListContainer(checkList);
    }

    private void addContactsToField()
    {
        for(Contact contact:noteComponents.getChosenContacts())
            addContactToChosenContactContainer(contact);
    }

    private void addAudioToField()
    {
        for(Audio audio:noteComponents.getChosenAudios())
            addAudioToChosenContactContainer(audio,Uri.parse(audio.getAudioUri()),fileIOTasks);
    }

    private void setNoteFieldValue()
    {
        noteFieldScreenView.getNoteTitleField().setText(noteComponents.getNote().getNoteTitle());
        noteFieldScreenView.getNoteTextField().setText(noteComponents.getNote().getNoteText());
        applyBackgroundColor(noteComponents.getNote().getBackgroundColor());
    }



    public void addImageToFields()
    {
        if(AppPermissionTrackingTasks.hasReadExternalStoragePermission(activity))
        {
            for(Image image:noteComponents.getChosenImages())
                addImageToChosenImageContainer(image);
        }
    }

    public void showInvalidNoteToast()
    {
        Toast.makeText(activity, activity.getResources().getString(R.string.nothing_to_save), Toast.LENGTH_SHORT).show();
    }

    public void showSavePromptDialog(DialogInterface.OnClickListener listener) {

        new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.save_note))
                .setMessage(activity.getResources().getString(R.string.note_is_not_saved))
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, listener)
                .setIcon(activity.getResources().getDrawable(R.drawable.warning_red))
                .show();
    }

    public void showFileSavingDialog(Bundle args)
    {
        FileSaveDialog fileSaveDialog = FileSaveDialog.newInstance(args);
        fileSaveDialog.show(((AppCompatActivity)activity).getSupportFragmentManager(), FragmentTags.FILE_SAVE_DIALOG);

    }

    public void hideDoneButton() {
        noteFieldScreenView.getSaveBtton().setVisibility(View.GONE);
    }

    public void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, RequestCode.OPEN_CAMERA_TO_TAKE_IMAGE);

    }

    public void showUpgradeDialog(DialogManagementTask.DialogOptionListener dialogOptionListener) {
        DialogManagementTask dialogManagementTask = tasksFactory.getDialogManagementTask();
        dialogManagementTask.showUpgradeDialog(dialogOptionListener);
    }
}
