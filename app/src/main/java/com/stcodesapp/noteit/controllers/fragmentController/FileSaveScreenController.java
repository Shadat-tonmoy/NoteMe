package com.stcodesapp.noteit.controllers.fragmentController;

import android.util.Log;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileMovingTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.FileSaveScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.ClipboardTasks;
import com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView.FileSaveDialogScreenView;
import com.stcodesapp.noteit.ui.views.screens.dialogScreen.FileSaveDialogScreen;
import java.io.File;

public class FileSaveScreenController implements FileSaveDialogScreen.Listener, FileMovingTask.Listener{

    public interface Listener{
        void onDismissDialog();

        void onFileSaved(File file);

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
    }

    private void startMovingFile()
    {
        File outputFile = new File(fileIOTasks.getDirectoryPath(Constants.RECORDING_FILE_PATH)+ Constants.SLASH +fileSaveScreenManipulationTasks.getFileName()+Constants.RECORDING_FILE_TYPE);
        FileMovingTask fileMovingTask = tasksFactory.getFileMovingTask(this);
        fileSaveScreenManipulationTasks.showSavingProgress();
        fileMovingTask.execute(inputFile,outputFile);

    }

    @Override
    public void onPositiveButtonClicked() {
        String label = fileSaveDialogScreenView.getSaveButton().getText().toString();
        if(label.equals(Constants.GOT_IT))
        {
            onNegativeButtonClicked(true);
        }
        else
        {
            if(fileIOTasks.isRecordedAudioFileAlreadyExists(fileSaveScreenManipulationTasks.getFileName()))
            {
                fileSaveScreenManipulationTasks.toggleFileExistsText(true);
                fileSaveScreenManipulationTasks.toggleSaveButtonText(false);
            }
            else
            {
                fileSaveScreenManipulationTasks.toggleFileExistsText(false);
                startMovingFile();
            }
        }

    }

    @Override
    public void onNegativeButtonClicked(boolean saveFileToDB) {
        clipboardTasks.hideKeyBoard(fileSaveDialogScreenView.getFileNameField());
        /*if(saveFileToDB)
            listener.onFileSaved(inputFile);*/
        listener.onDismissDialog();
    }

    @Override
    public void onEditTextChanged(String text) {
        fileSaveScreenManipulationTasks.handleTextChanged(text);
    }

    @Override
    public void onFileMovingDone(File outputFile) {
        Log.e("FileSaved","Successfully@ "+outputFile.getAbsolutePath());
        bindInputFile(outputFile);
        listener.onFileSaved(inputFile);
        fileSaveScreenManipulationTasks.showFileSaveDoneMessage(outputFile.getAbsolutePath());

    }
}
