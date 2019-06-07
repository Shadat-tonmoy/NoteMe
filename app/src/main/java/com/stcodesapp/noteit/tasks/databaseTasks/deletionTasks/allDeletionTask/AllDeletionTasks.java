package com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.allDeletionTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;

public class AllDeletionTasks extends AsyncTask<Long, Void, Integer>{

    public interface Listener{
        void onAllElementOfSingleNoteDeleted();

    }

    private Context context;
    private NoteDatabase noteDatabase;
    private ComponentType componentType;
    private Listener listener;



    public AllDeletionTasks(Context context, ComponentType componentType)
    {
        this.context = context;
        noteDatabase = NoteDatabase.getInstance(context);
        this.componentType = componentType;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Long... components) {
        return getDeletionTask(components[0]);
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if(result> Constants.ZERO)
            listener.onAllElementOfSingleNoteDeleted();
        Toast.makeText(context,context.getResources().getString(R.string.note_component_deleted),Toast.LENGTH_SHORT).show();
    }

    private int getDeletionTask(Long noteId)
    {
        switch (componentType)
        {
            case AUDIO:
                return noteDatabase.audioDao().deleteAllAudioForNote(noteId);
            case IMAGE:
                return noteDatabase.imageDao().deleteAllImageForNote(noteId);
            case EMAIL:
                return noteDatabase.emailDao().deleteAllEmailForNote(noteId);
            case CONTACT:
                return noteDatabase.contactDao().deleteAllContactForNote(noteId);
            case ALL_NOTE:
                return noteDatabase.notesDao().deleteAllNote();
            default:
                return -1;
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
