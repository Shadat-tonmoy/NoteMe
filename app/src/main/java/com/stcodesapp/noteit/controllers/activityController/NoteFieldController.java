package com.stcodesapp.noteit.controllers.activityController;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.NoteFieldScreenManipulationTasks;
import com.stcodesapp.noteit.ui.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.NoteFieldScreen;

import static android.app.Activity.RESULT_OK;

public class NoteFieldController implements NoteFieldScreen.Listener,ColorPallateBottomSheets.Listener {

    private TasksFactory tasksFactory;
    private ActivityNavigationTasks activityNavigationTasks;
    private NoteFieldScreenView noteFieldScreenView;
    private NoteFieldScreenManipulationTasks noteFieldScreenManipulationTasks;
    private FileIOTasks fileIOTasks;

    public NoteFieldController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        activityNavigationTasks = tasksFactory.getActivityNavigationTasks();
        noteFieldScreenManipulationTasks = tasksFactory.getNoteFieldScreenManipulationTasks();
        fileIOTasks = tasksFactory.getFileIOTasks();
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


    public void onPostCreate() {
        noteFieldScreenView.initUserInteractions();
    }




}
