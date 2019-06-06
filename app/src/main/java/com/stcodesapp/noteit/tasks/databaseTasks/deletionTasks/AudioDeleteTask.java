package com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Image;

public class AudioDeleteTask extends BaseDeletionTasks<Audio> {

    public interface Listener{
        void onAudioDeleted(Audio audio);
    }

    private Listener listener;

    public AudioDeleteTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(Audio audio) {
        super.onPostExecute(audio);
        listener.onAudioDeleted(audio);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
