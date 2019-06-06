package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Audio;

import java.util.List;

public class AudioSelectTask extends BaseSelectionTasks<Audio> {

    public interface Listener{
        void onAudioFetched(List<Audio> fetchedAudio);
    }

    private Listener listener;

    public AudioSelectTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(List<Audio> results) {
        listener.onAudioFetched(results);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
