package com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileDeletingTask extends AsyncTask<File,Void,File>
{

    public interface Listener{
        void onFileDeleted(File file);
    }

    private Listener listener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected File doInBackground(File... files) {
        boolean done = false;
        for(File file:files)
        {
            deleteFile(file.getAbsolutePath());
            done = true;
        }
        if(done)
            return files[0];
        return null;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(file!=null)
            listener.onFileDeleted(file);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    private boolean deleteFile(String filePath) {
        File file = new File (filePath);
        return file.delete();
    }
}
