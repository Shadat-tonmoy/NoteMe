package com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask;

import android.content.Context;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;

public class NoteDeleteTask extends BaseDeletionTasks<Note> {

    public interface Listener{
        void onNoteDeleted(Note note);
    }

    private Listener listener;

    public NoteDeleteTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(Note note) {
        listener.onNoteDeleted(note);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
