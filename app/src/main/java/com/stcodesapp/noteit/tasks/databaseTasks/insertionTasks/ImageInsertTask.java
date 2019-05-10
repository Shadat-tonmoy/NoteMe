package com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Image;

public class ImageInsertTask extends AsyncTask<Image, Void, Integer> {

    public interface Listener{
        void onImageInserted(int numberOfImages);
    }


    private final Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;


    public ImageInsertTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected Integer doInBackground(Image... images) {
        noteDatabase.imageDao().insertImage(images);
        return images.length;
    }

    @Override
    protected void onPostExecute(Integer numberOfImages) {
        super.onPostExecute(numberOfImages);
        listener.onImageInserted(numberOfImages);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
