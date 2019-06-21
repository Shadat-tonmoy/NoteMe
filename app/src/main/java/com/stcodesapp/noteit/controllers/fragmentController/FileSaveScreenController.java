package com.stcodesapp.noteit.controllers.fragmentController;

import android.util.Log;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.FileMovingTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.FileSaveScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.ClipboardTasks;
import com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView.FileSaveDialogScreenView;
import com.stcodesapp.noteit.ui.views.screens.dialogScreen.FileSaveDialogScreen;
import java.io.File;

public class FileSaveScreenController implements FileSaveDialogScreen.Listener, FileMovingTask.Listener{

    public interface Listener{
        void onDismissDialog();

        void onDisableCancellable();
    }

    private TasksFactory tasksFactory;
    private FileSaveDialogScreen fileSaveDialogScreenView;
    private Listener listener;
    private ClipboardTasks clipboardTasks;
    private FileIOTasks fileIOTasks;
    private FileSaveScreenManipulationTasks fileSaveScreenManipulationTasks;
    private File inputFile;

    public FileSaveScreenController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        this.clipboardTasks = tasksFactory.getClipboardTasks();
        this.fileIOTasks= tasksFactory.getFileIOTasks();
        this.fileSaveScreenManipulationTasks= tasksFactory.getFileSaveScreenManipulationTasks();

    }

    public void bindView(FileSaveDialogScreenView fileSaveDialogScreenView) {
        this.fileSaveDialogScreenView = fileSaveDialogScreenView;
        fileSaveScreenManipulationTasks.bindView(fileSaveDialogScreenView);
    }

    public void onStart()
    {
        fileSaveDialogScreenView.registerListener(this);
    }

    public void onStop()
    {
        fileSaveDialogScreenView.unregisterListener(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    public void bindInputFile(File inputFile) {
        this.inputFile = inputFile;
        fileSaveScreenManipulationTasks.bindFileSavePath(inputFile.getAbsolutePath());
    }

    private void insertTTSHistory()
    {
//        Text2SpeechInsertTask text2SpeechInsertTask = tasksFactory.getText2SpeechInsertTask();
//        text2SpeechInsertTask.execute(text2SpeechModel);
    }

    @Override
    public void onPositiveButtonClicked() {
//        String label = fileSaveDialogScreenView.getSaveButton().getText().toString();
//        text2SpeechTask.saveFileFromTTS(fileSaveDialogScreenView.getFileNameField().getText().toString(),label,listener);
//        if(label.equals(Constants.OPEN))
//            onNegativeButtonClicked();
        insertTTSHistory();
    }

    @Override
    public void onNegativeButtonClicked() {
        clipboardTasks.hideKeyBoard(fileSaveDialogScreenView.getFileNameField());
        listener.onDismissDialog();
    }

    @Override
    public void onEditTextChanged(String text) {
        fileSaveScreenManipulationTasks.handleTextChanged(text);
    }

    @Override
    public void onFileMovingDone(File outputFile) {
        Log.e("FileSaved","Successfully@ "+outputFile.getAbsolutePath());

    }

    @Override
    public void onFileAlreadyExists() {
        fileSaveScreenManipulationTasks.toggleFileExistsText(true);
        fileSaveScreenManipulationTasks.toggleSaveButtonText(false);

    }

    @Override
    public void onFileSaveStarted() {
        fileSaveScreenManipulationTasks.showSavingProgress();

    }
}
