package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Image;

import java.util.List;

public class ImageSelectTask extends NoteComponentSelectionTasks<Image>{

    public interface Listener{
        void onImageFetched(List<Image> fetchedImage);
    }

    private Listener listener;

    public ImageSelectTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(List<Image> results) {
        listener.onImageFetched(results);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
