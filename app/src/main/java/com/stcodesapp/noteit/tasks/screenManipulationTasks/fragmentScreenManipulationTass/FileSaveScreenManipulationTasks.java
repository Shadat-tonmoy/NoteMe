package com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView.FileSaveDialogScreenView;


public class FileSaveScreenManipulationTasks {

    private Activity activity;
    private FileSaveDialogScreenView fileSaveDialogScreenView;

    public FileSaveScreenManipulationTasks(Activity activity) {
        this.activity = activity;
    }

    public void bindView(FileSaveDialogScreenView fileSaveDialogScreenView) {
        this.fileSaveDialogScreenView = fileSaveDialogScreenView;
    }

    public void bindFileSavePath(String path)
    {
        fileSaveDialogScreenView.getFilePathText().setText(path+ Constants.SLASH);
    }

    public void showSavingProgress()
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideButtonPanel();
                hideFieldPanel();
                showProgressPanel();
            }
        });
    }


    public void handleTextChanged(String text) {
        if(fileSaveDialogScreenView.getSaveButton().getText().equals(activity.getResources().getString(R.string.override)))
        {
            toggleSaveButtonText(true);
            toggleFileExistsText(false);
        }
    }



    public void toggleFileExistsText(boolean show)
    {
        if(show)
            fileSaveDialogScreenView.getFileExistsText().setVisibility(View.VISIBLE);
        else fileSaveDialogScreenView.getFileExistsText().setVisibility(View.GONE);
    }


    public void toggleSaveButtonText(boolean save)
    {
        if(save)
            fileSaveDialogScreenView.getSaveButton().setText(activity.getResources().getString(R.string.save));
        else fileSaveDialogScreenView.getSaveButton().setText(activity.getResources().getString(R.string.override));
    }

    public void showFileSaveDoneMessage()
    {
        hideProgressPanel();
        hideFieldPanel();
        showSaveDone();
        showButtonPanel(activity.getResources().getString(R.string.open));


    }

    private void hideFieldPanel()
    {
        fileSaveDialogScreenView.getRootView().findViewById(R.id.field_panel).setVisibility(View.GONE);
    }

    private void showFieldPanel()
    {
        fileSaveDialogScreenView.getRootView().findViewById(R.id.field_panel).setVisibility(View.VISIBLE);
    }

    private void hideProgressPanel()
    {
        fileSaveDialogScreenView.getRootView().findViewById(R.id.progress_panel).setVisibility(View.GONE);
    }

    private void showProgressPanel()
    {
        fileSaveDialogScreenView.getRootView().findViewById(R.id.progress_panel).setVisibility(View.VISIBLE);
    }

    private void hideButtonPanel()
    {
        fileSaveDialogScreenView.getRootView().findViewById(R.id.button_panel).setVisibility(View.GONE);
    }

    private void showButtonPanel(String buttonLabel)
    {
        fileSaveDialogScreenView.getSaveButton().setText(buttonLabel);
        fileSaveDialogScreenView.getRootView().findViewById(R.id.button_panel).setVisibility(View.VISIBLE);
    }

    private void showSaveDone()
    {
        String doneMessage = activity.getResources().getString(R.string.saved_successfully)+fileSaveDialogScreenView.getFilePathText().getText().toString()+fileSaveDialogScreenView.getFileNameField().getText().toString()+Constants.MP3_FILE_EXT;
        fileSaveDialogScreenView.getFileSaveDoneText().setText(doneMessage);

        fileSaveDialogScreenView.getFileSaveDoneLayout().setVisibility(View.VISIBLE);
    }

    private void hideSaveDone()
    {
        fileSaveDialogScreenView.getFileSaveDoneLayout().setVisibility(View.GONE);
    }
}
