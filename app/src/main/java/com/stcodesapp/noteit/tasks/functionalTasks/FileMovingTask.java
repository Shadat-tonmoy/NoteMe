package com.stcodesapp.noteit.tasks.functionalTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileMovingTask extends AsyncTask<File,Void,File>
{

    public interface Listener{
        void onFileMovingDone(File outputFile);
        void onFileAlreadyExists();
        void onFileSaveStarted();
    }

    private Listener listener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected File doInBackground(File... files) {
        if(moveFile(files[0].getAbsolutePath(),files[1].getAbsolutePath()))
            return files[1];
        return null;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(file!=null)
            listener.onFileMovingDone(file);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    private boolean moveFile(String inputPath, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File file = new File (outputPath);
            if (!file.exists())
            {
                file.createNewFile();
            }


            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath).delete();
            return true;


        }

        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        return false;

    }
}
