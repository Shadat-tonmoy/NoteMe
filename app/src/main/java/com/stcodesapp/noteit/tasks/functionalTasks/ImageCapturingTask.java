package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageCapturingTask {

    private Activity activity;
    private AppPermissionTrackingTasks appPermissionTrackingTasks;
    private String capturedImagePath;
    private TasksFactory tasksFactory;
    private FileIOTasks fileIOTasks;
    private Uri photoURI;

    public ImageCapturingTask(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.appPermissionTrackingTasks = tasksFactory.getAppPermissionTrackingTasks();
        this.fileIOTasks = tasksFactory.getFileIOTasks();
    }

    public void openCameraToTakeImage()
    {
        if(appPermissionTrackingTasks.hasCameraAccessPermission())
        {
            openCamera();
        }
    }

    public void openCamera() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(activity,
                        activity.getApplication().getPackageName()+".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, RequestCode.OPEN_CAMERA_TO_TAKE_IMAGE);
                addImageToGellary();
            }
        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat(Constants.DATE_FORMAT_FOR_IMAGE_NAME).format(new Date());
        String imageFileName = Constants.IMAGE + timeStamp + Constants.UNDERSCORE;
        File storageDir = new File(fileIOTasks.getDirectoryPath(Constants.IMAGE_FILE_PATH));
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                Constants.JPG_FILE_EXT,         /* suffix */
                storageDir      /* directory */
        );
        capturedImagePath = image.getAbsolutePath();
        return image;
    }

    public void deleteCapturedImage(File imageFile) throws IOException {

        String timeStamp = new SimpleDateFormat(Constants.DATE_FORMAT_FOR_IMAGE_NAME).format(new Date());
        String imageFileName = Constants.IMAGE + timeStamp + Constants.UNDERSCORE;
        File storageDir = new File(fileIOTasks.getDirectoryPath(Constants.IMAGE_FILE_PATH));
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                Constants.JPG_FILE_EXT,         /* suffix */
                storageDir      /* directory */
        );
        capturedImagePath = image.getAbsolutePath();

    }

    public Uri getCapturedImageURI()
    {
        return photoURI;
    }

    private void addImageToGellary() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(capturedImagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
    }

    /*private void handleCapturedImage(Intent intent)
    {
        Uri selectedImage = intent.getData();
        Log.e("CapturedImage",selectedImage.toString());
        Image image = fileIOTasks.getImageFromURI(selectedImage);
        Log.e("CapturedImage"," File "+selectedImage.toString());
        if(image!=null)
        {
            noteComponents.getChosenImages().add(image);
            noteComponents.getNote().updateImagePriority();
            image.setNoteId(noteComponents.getNote().getId());
            noteFieldScreenManipulationTasks.addImageToChosenImageContainer(image);
            if(isUpdating)
                addChosenImageToDB(image);
        }
    }*/

}
