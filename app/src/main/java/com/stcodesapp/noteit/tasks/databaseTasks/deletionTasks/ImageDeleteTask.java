package com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;

public class ImageDeleteTask extends BaseDeletionTasks<Image> {

    public interface Listener{
        void onImageDeleted(Image image);
    }

    private Listener listener;

    public ImageDeleteTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(Image image) {
        super.onPostExecute(image);
        listener.onImageDeleted(image);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
