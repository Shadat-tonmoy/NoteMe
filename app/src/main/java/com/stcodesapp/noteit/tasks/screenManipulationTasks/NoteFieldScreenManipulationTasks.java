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
import com.stcodesapp.noteit.factory.ListenerFactory;
import com.stcodesapp.noteit.factory.UIComponentFatory;
import com.stcodesapp.noteit.listeners.ContactListener;
import com.stcodesapp.noteit.listeners.RemoveImageListener;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.ui.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.ui.fragments.PhoneNoOptionsBottomSheets;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldScreenManipulationTasks {

    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;
    private ColorPallateBottomSheets colorPallateBottomSheets;
    private PhoneNoOptionsBottomSheets phoneNoOptionsBottomSheets;
    private ListenerFactory listenerFactory;
    private UIComponentFatory uiComponentFatory;

    public NoteFieldScreenManipulationTasks(Activity activity, ListenerFactory listenerFactory, UIComponentFatory uiComponentFatory) {
        this.activity = activity;
        this.listenerFactory = listenerFactory;
        this.uiComponentFatory = uiComponentFatory;
    }

    public void bindView(NoteFieldScreenView noteFieldScreenView) {
        this.noteFieldScreenView = noteFieldScreenView;
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
            int textColor = R.color.white;
            int dividerColor = R.color.white;
            if(colorName.equals(BackgroundColors.WHITE))
            {
                textColor = R.color.default_text;
                dividerColor = R.color.default_divider;
            }
            noteFieldScreenView.getNoteTitleText().setTextColor(activity.getResources().getColor(textColor));
            noteFieldScreenView.getNoteTextField().setTextColor(activity.getResources().getColor(textColor));
            noteFieldScreenView.getNoteTextField().setHintTextColor(activity.getResources().getColor(textColor));
            noteFieldScreenView.getNoteTitleDivider().setBackgroundColor(activity.getResources().getColor(dividerColor));
            noteFieldScreenView.getNoteTextDivider().setBackgroundColor(activity.getResources().getColor(dividerColor));
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
        RemoveImageListener removeImageListener = listenerFactory.getRemoveImageListener(imageContainer,imageHolder);
        removeIcon.setOnClickListener(removeImageListener);
    }

    public void addContactToChosenContactContainer(Contact contact)
    {
        LinearLayout contactContainer = noteFieldScreenView.getRootView().findViewById(R.id.chosen_contact_container);
        if(contactContainer==null)
        {
            contactContainer = (activity.getLayoutInflater().inflate(R.layout.contact_container,null,false)).findViewById(R.id.chosen_contact_container);
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
        ContactListener contactListener= listenerFactory.getContactListener(contact);
        callButton.setOnClickListener(contactListener);
        copyButton.setOnClickListener(contactListener);
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
}
