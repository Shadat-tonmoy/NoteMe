package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.ui.activities.NoteFieldActivity;

public class ImageListener implements View.OnClickListener {

    private Image image;
    private Activity activity;
    private DatabaseTasks databaseTasks;
    private View imageHolder;

    public ImageListener(Image image, Activity activity, DatabaseTasks databaseTasks, View imageHolder) {
        this.image = image;
        this.activity = activity;
        this.databaseTasks = databaseTasks;
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
    }
}
