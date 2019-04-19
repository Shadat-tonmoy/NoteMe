package com.stcodesapp.noteit.controllers.activityController;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;
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
    private ActivityNavigationTasks activityNavigationTasks;
    private NoteFieldScreenView noteFieldScreenView;
    private NoteFieldScreenManipulationTasks noteFieldScreenManipulationTasks;
    private FileIOTasks fileIOTasks;
    private AppPermissionTrackingTasks appPermissionTrackingTasks;

    public NoteFieldController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        activityNavigationTasks = tasksFactory.getActivityNavigationTasks();
        noteFieldScreenManipulationTasks = tasksFactory.getNoteFieldScreenManipulationTasks();
        fileIOTasks = tasksFactory.getFileIOTasks();
        appPermissionTrackingTasks = tasksFactory.getAppPermissionTrackingTasks();
    }

    public void bindView(NoteFieldScreenView secondActivityScreenView) {
        this.noteFieldScreenView = secondActivityScreenView;
        noteFieldScreenManipulationTasks.bindView(noteFieldScreenView);
    }

    public void onStart()
    {
        noteFieldScreenView.registerListener(this);
    }

    public void onStop()
    {
        noteFieldScreenView.unregisterListener(this);
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
    public void onColorClicked(String colorName) {
        Log.e("Clicked",colorName);
        noteFieldScreenManipulationTasks.dismissColorPalate();
        noteFieldScreenManipulationTasks.applyBackgroundColor(colorName);
    }

    private void handleChosenImage(Intent intent)
    {
        Log.e("Adding","image");
        Uri selectedImage = intent.getData();
        noteFieldScreenManipulationTasks.addImageToChosenImageContainer(selectedImage);
//        imageview.setImageURI(selectedImage);

    }

    private void handleChosenContact(Intent intent)
    {
        noteFieldScreenManipulationTasks.addContactToChosenContactContainer(fileIOTasks.readContact(intent));
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
