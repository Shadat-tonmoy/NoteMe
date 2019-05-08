package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.BackgroundColors;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.factory.ListeningTasks;
import com.stcodesapp.noteit.factory.UIComponentFatory;
import com.stcodesapp.noteit.listeners.AudioListener;
import com.stcodesapp.noteit.listeners.ContactListener;
import com.stcodesapp.noteit.listeners.EmailListener;
import com.stcodesapp.noteit.listeners.RemoveImageListener;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.UtilityTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;
import com.stcodesapp.noteit.ui.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.ui.fragments.PhoneNoOptionsBottomSheets;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldScreenManipulationTasks {

    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;
    private ColorPallateBottomSheets colorPallateBottomSheets;
    private PhoneNoOptionsBottomSheets phoneNoOptionsBottomSheets;
    private ListeningTasks listeningTasks;
    private UIComponentFatory uiComponentFatory;
    private NoteComponents noteComponents;

    public NoteFieldScreenManipulationTasks(Activity activity, ListeningTasks listeningTasks, UIComponentFatory uiComponentFatory) {
        this.activity = activity;
        this.listeningTasks = listeningTasks;
        this.uiComponentFatory = uiComponentFatory;
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
            Log.e("Exception",e.getMessage());
        }
    }

    public void addImageToChosenImageContainer(Uri imageUri)
    {
        FlexboxLayout imageContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_image_container);
        if(imageContainer==null)
        {
            imageContainer = (activity.getLayoutInflater().inflate(R.layout.image_container,null,false)).findViewById(R.id.chosen_image_container);
            noteFieldScreenView.getUiComponentContainer().addView(imageContainer);
        }
        final View imageHolder = activity.getLayoutInflater().inflate(R.layout.image_holder,null,false);
        ImageView imageView = imageHolder.findViewById(R.id.image);
        ImageView removeIcon = imageHolder.findViewById(R.id.remove_image);
        imageView.setImageURI(imageUri);
        imageContainer.addView(imageHolder);
        RemoveImageListener removeImageListener = listeningTasks.getRemoveImageListener(imageContainer,imageHolder);
        removeIcon.setOnClickListener(removeImageListener);
    }

    public void addContactToChosenContactContainer(Contact contact)
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
        final View contactHolder = activity.getLayoutInflater().inflate(R.layout.contact_holder,null,false);
        ImageView callButton = contactHolder.findViewById(R.id.contact_call_btn);
        ImageView copyButton = contactHolder.findViewById(R.id.contact_copy_btn);
        TextView contactNo = contactHolder.findViewById(R.id.contact_no);
        TextView displayName = contactHolder.findViewById(R.id.contact_name);
        contactNo.setText(contact.getPhoneNumber());
        displayName.setText(contact.getDisplayName());
        contactContainer.addView(contactHolder);
        ContactListener contactListener= listeningTasks.getContactListener(contact);
        callButton.setOnClickListener(contactListener);
        copyButton.setOnClickListener(contactListener);
    }

    public void addEmailToChosenEmailContainer(Email email)
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
        final View emailHolder = activity.getLayoutInflater().inflate(R.layout.email_holder,null,false);
        ImageView sendButton = emailHolder.findViewById(R.id.email_send_btn);
        ImageView copyButton = emailHolder.findViewById(R.id.email_copy_btn);
        TextView emailId = emailHolder.findViewById(R.id.email_id);
        TextView emailName = emailHolder.findViewById(R.id.email_name);
        emailId.setText(email.getEmailID());
        emailName.setText(email.getEmailName());
        emailContainer.addView(emailHolder);

        EmailListener emailListener = listeningTasks.getEEmailListener(email);
        sendButton.setOnClickListener(emailListener);
        copyButton.setOnClickListener(emailListener);
    }

    public void addAudioToChosenContactContainer(Audio audio, Uri audioUri, FileIOTasks fileIOTasks)
    {
        LinearLayout audioContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_audio_container);
        if(audioContainer==null)
        {
            audioContainer = (activity.getLayoutInflater().inflate(R.layout.audio_container,null,false)).findViewById(R.id.chosen_audio_container);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 16, 0, 0);
            audioContainer.setLayoutParams(params);
            noteFieldScreenView.getUiComponentContainer().addView(audioContainer);
        }
        final View audioHolder = activity.getLayoutInflater().inflate(R.layout.chosen_audio_single_row,null,false);
        TextView audioTitle = audioHolder.findViewById(R.id.audio_title);
        TextView audioSize = audioHolder.findViewById(R.id.audio_size);

        audioTitle.setText(UtilityTasks.truncateText(audio.getAudioTitle(),Constants.MAX_AUDIO_FILE_NAME_LENGTH,Constants.MP3_FILE_EXT));
        audioSize.setText(UtilityTasks.getFileSizeString(Double.parseDouble(audio.getAudioSize())));
        audioContainer.addView(audioHolder);
        AudioListener audioListener = listeningTasks.getAudioListener(audio,fileIOTasks,audioUri);
        audioHolder.setOnClickListener(audioListener);
    }

    public void showPhoneNoOptions(PhoneNoOptionsBottomSheets.Listener listener)
    {
        phoneNoOptionsBottomSheets =  uiComponentFatory.getphoneNoOptionsBottomSheets();
        phoneNoOptionsBottomSheets.setListener(listener);
        phoneNoOptionsBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(),"TAG");
    }
    public void dismissPhoneNoOptions()
    {
        if(phoneNoOptionsBottomSheets!=null && phoneNoOptionsBottomSheets.isVisible())
            phoneNoOptionsBottomSheets.dismiss();
    }

    public void showPermissionRequiredMessage()
    {
        Toast.makeText(activity, activity.getResources().getText(R.string.permission_is_required), Toast.LENGTH_SHORT).show();
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
}
