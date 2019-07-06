package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileDeletingTask;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileIOTasks;
import com.stcodesapp.noteit.ui.activities.NoteFieldActivity;

import java.io.File;

public class ImageListener implements View.OnClickListener {

    private Image image;
    private Activity activity;
    private DatabaseTasks databaseTasks;
    private View imageHolder;
    private FileIOTasks fileIOTasks;
    private TasksFactory tasksFactory;

    public ImageListener(Image image, Activity activity, View imageHolder,TasksFactory tasksFactory) {
        this.image = image;
        this.activity = activity;
        this.tasksFactory= tasksFactory;
        this.databaseTasks = tasksFactory.getDatabaseTasks();
        this.fileIOTasks= tasksFactory.getFileIOTasks();
        this.imageHolder = imageHolder;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.remove_image:
                removeImage();
                break;
        }

    }

    private void removeImage()
    {
        imageHolder.setVisibility(View.GONE);
        databaseTasks.getImageDeleteTask(((NoteFieldActivity)activity).getNoteFieldController()).execute(image);
        if(image.isCaptured())
        {
//            Log.e("WillDeleteImage",image.getImageFilePath());
            FileDeletingTask fileDeletingTask = tasksFactory.getFileDeletingTask(new FileDeletingTask.Listener() {
                @Override
                public void onFileDeleted(File file) {
//                    Log.e("FileDeleted", file.getAbsolutePath());
                }
            });
            fileDeletingTask.execute(new File(image.getImageFilePath()));
        }
    }
}
