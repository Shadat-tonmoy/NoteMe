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

    public void showFileSaveDoneMessage(String filePath)
    {
        hideProgressPanel();
        hideFieldPanel();
        showSaveDone(filePath);
        showButtonPanel(activity.getResources().getString(R.string.got_it));


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

    private void showSaveDone(String filePath)
    {
        String doneMessage = activity.getResources().getString(R.string.saved_successfully)+filePath;
//        fileSaveDialogScreenView.getFileNameField().getText().toString()+Constants.RECORDING_FILE_TYPE;
        fileSaveDialogScreenView.getFileSaveDoneText().setText(doneMessage);

        fileSaveDialogScreenView.getFileSaveDoneLayout().setVisibility(View.VISIBLE);
    }

    private void hideSaveDone()
    {
        fileSaveDialogScreenView.getFileSaveDoneLayout().setVisibility(View.GONE);
    }

    public String getFileName()
    {
        return fileSaveDialogScreenView.getFileNameField().getText().toString();
    }
}
