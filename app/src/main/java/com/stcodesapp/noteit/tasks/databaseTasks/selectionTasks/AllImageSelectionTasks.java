package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Image;

import java.util.List;

public class AllImageSelectionTasks extends AsyncTask<Long, Void, List<Image>>{



    public interface Listener{
        void onAllImageFetchedFetched(List<Image> image, int purpose);
    }

    private Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;
    private int purpose;

    public AllImageSelectionTasks(Context context, Listener listener, int purpose)
    {
        this.context = context;
        noteDatabase = NoteDatabase.getInstance(context);
        this.listener = listener;
        this.purpose = purpose;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Image> doInBackground(Long... noteID) {
        long noteId = noteID[0];
        if(noteId == Constants.INVALID)
            return noteDatabase.imageDao().getAllImage();
        else return noteDatabase.imageDao().getAllImageForNote(noteId);
    }

    @Override
    protected void onPostExecute(List<Image> results) {
        super.onPostExecute(results);
        if(listener!=null)
            listener.onAllImageFetchedFetched(results,purpose);
    }
}
