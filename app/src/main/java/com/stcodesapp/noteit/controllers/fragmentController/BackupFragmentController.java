package com.stcodesapp.noteit.controllers.fragmentController;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.CustomApplication;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.common.adController.FullScreenAdController;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.constants.PermissionType;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.tasks.functionalTasks.DialogManagementTask;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.IAPTrackingTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks.BackupRestoringTask;
import com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks.BackupSavingTask;
import com.stcodesapp.noteit.tasks.networkingTasks.GoogleDriveAPITask;
import com.stcodesapp.noteit.tasks.networkingTasks.GoogleSignInHandlingTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.BackupFragmentScreenManipulationTask;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.BackupFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.BackupFragmentScreen;


import static android.app.Activity.RESULT_OK;

public class BackupFragmentController implements BackupFragmentScreen.Listener, BackupSavingTask.Listener, BackupRestoringTask.Listener, BackupFragmentScreenManipulationTask.Listener, DialogManagementTask.DialogOptionListener, AdMob.Listener, GoogleDriveAPITask.BackupToCloudListener, GoogleDriveAPITask.RestoreFromCloudListener
{

    private Activity activity;
    private TasksFactory tasksFactory;
    private BackupFragmentScreenView backupFragmentScreenView;
    private BackupFragmentScreenManipulationTask backupFragmentScreenManipulationTask;
    private int localStorageOption = Constants.LOCAL_STORAGE_PHONE,watchAdPurpose, cloudBackupOption = -1;
    private IAPTrackingTasks iapTrackingTasks;
    private FullScreenAdController fullScreenAdController;
    private GoogleSignInHandlingTask googleSignInHandlingTask;

    public BackupFragmentController(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.backupFragmentScreenManipulationTask = tasksFactory.getBackupFragmentScreenManipulationTask();
        this.iapTrackingTasks = tasksFactory.getIAPTrackingTasks();
        this.googleSignInHandlingTask = tasksFactory.getGoogleSignInHandlingTask();
    }

    public void bindView(BackupFragmentScreenView backupFragmentScreenView)
    {
        this.backupFragmentScreenView = backupFragmentScreenView;
        this.backupFragmentScreenManipulationTask.bindView(backupFragmentScreenView);
        this.backupFragmentScreenManipulationTask.loadBannerAd();
    }


    public void onCreate()
    {
        googleSignInHandlingTask.createGoogleSignInAccount();
    }

    public void onStart()
    {
        fullScreenAdController = ((CustomApplication)activity.getApplication()).getFullScreenAdController();
        fullScreenAdController.getAdMob().setListener(this);
        backupFragmentScreenView.registerListener(this);
    }

    public void onStop()
    {
        backupFragmentScreenView.unregisterListener(this);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == RequestCode.WRITE_EXTERNAL_STORAGE_PERMISSION)
        {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                executeBackupToLocalStorageTask(Constants.LOCAL_STORAGE_BACKUP);
            else backupFragmentScreenManipulationTask.showPermissionRequiredMessage(PermissionType.WRITE_EXTERNAL_STORAGE);
        }
        else if(requestCode == RequestCode.READ_EXTERNAL_STORAGE_PERMISSION)
        {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                executeBackupRestoreTask(Constants.LOCAL_STORAGE_RESTORE);
            else backupFragmentScreenManipulationTask.showPermissionRequiredMessage(PermissionType.READ_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if(requestCode == RequestCode.GOOGLE_SIGN_IN_REQUEST)
            {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                googleSignInHandlingTask.handleSignInResult(task);
                if(googleSignInHandlingTask.isAlreadySignedIn())
                {
                    if(cloudBackupOption == EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED)
                        executeBackupToCloudTask();
                    else if(cloudBackupOption == EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED)
                        executeRestoreFromCloudTask();
                }
            }
            else if(requestCode == RequestCode.REQUEST_AUTHORIZATION_FOR_GOOGLE_DRIVE)
            {
                if(cloudBackupOption == EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED)
                    executeBackupToCloudTask();
                else if(cloudBackupOption == EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED)
                    executeRestoreFromCloudTask();
                /*testGoogleDriveAPI();*/
            }
        }
    }

    private void executeBackupToLocalStorageTask(int backupType)
    {
        backupFragmentScreenManipulationTask.showBackupProgressDialog(Constants.BACKUP);
        BackupSavingTask backupSavingTask = tasksFactory.getBackupSavingTask();
        backupSavingTask.setListener(this);
        backupSavingTask.execute(backupType,localStorageOption);
    }

    private void executeBackupRestoreTask(int backupType)
    {
        backupFragmentScreenManipulationTask.showBackupProgressDialog(Constants.RESTORE);
        BackupRestoringTask backupRestoringTask = tasksFactory.getBackupRestoringTask();
        backupRestoringTask.setListener(this);
        backupRestoringTask.execute(backupType,localStorageOption);
    }

    private void executeBackupToCloudFlow()
    {
        if(!googleSignInHandlingTask.isAlreadySignedIn())
        {
            googleSignInHandlingTask.startGoogleSignInFlow();
            cloudBackupOption = EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED;
        }
        else
        {
            executeBackupToCloudTask();
        }
    }

    private void executeBackupToCloudTask()
    {
        cloudBackupOption = EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED;
        GoogleDriveAPITask googleDriveAPITask = tasksFactory.getGoogleDriveAPITask();
        googleDriveAPITask.setDriveService(googleSignInHandlingTask.getDriveService());
        googleDriveAPITask.setBackupToCloudListener(this);
        googleDriveAPITask.execute(EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED);
    }


    private void executeRestoreFromCloudFlow()
    {
        if(!googleSignInHandlingTask.isAlreadySignedIn())
        {
            googleSignInHandlingTask.startGoogleSignInFlow();
            cloudBackupOption = EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED;
        }
        else
        {
            executeRestoreFromCloudTask();
        }
    }



    private void executeRestoreFromCloudTask()
    {
        GoogleDriveAPITask googleDriveAPITask = tasksFactory.getGoogleDriveAPITask();
        googleDriveAPITask.setDriveService(googleSignInHandlingTask.getDriveService());
        googleDriveAPITask.setRestoreFromCloudListener(this);
        cloudBackupOption = EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED;
        googleDriveAPITask.execute(EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED);
    }


    @Override
    public void onRestoreFromCloudSuccess()
    {
        DialogManagementTask.showBackupToCloudDoneDialog(activity,cloudBackupOption,true);
    }

    @Override
    public void onRestoreFromCloudFailed(boolean showDialog)
    {
        if(showDialog)
            DialogManagementTask.showBackupToCloudDoneDialog(activity,cloudBackupOption,false);
    }


    @Override
    public void onBackupToLocalStorageClicked()
    {
        if(iapTrackingTasks.isPaidUser())
            executeBackupToLocalStorageTask(Constants.LOCAL_STORAGE_BACKUP);
        else
        {
            backupFragmentScreenManipulationTask.showUpgradeDialog(this);
            watchAdPurpose = Constants.LOCAL_STORAGE_BACKUP;
        }
    }

    @Override
    public void onBackupToCloudStorageClicked()
    {
        executeBackupToCloudFlow();
    }



    @Override
    public void onBackupToCloudSuccess()
    {
        DialogManagementTask.showBackupToCloudDoneDialog(activity,cloudBackupOption,true);
    }

    @Override
    public void onBackupToCloudFailed(boolean showDialog)
    {
        if(showDialog)
            DialogManagementTask.showBackupToCloudDoneDialog(activity,cloudBackupOption,false);

    }

    @Override
    public void onRestoreFromLocalStorageClicked()
    {
        if(iapTrackingTasks.isPaidUser())
            backupFragmentScreenManipulationTask.showBackupRestoreWarningDialog(this);
        else
        {
            backupFragmentScreenManipulationTask.showUpgradeDialog(this);
            watchAdPurpose = Constants.LOCAL_STORAGE_RESTORE;
        }
    }

    @Override
    public void onRestoreFromCloudStorageClicked()
    {
        executeRestoreFromCloudFlow();
    }

    @Override
    public void onLocalBackupStorageOptionSelected(int checkedID)
    {
        Logger.logMessage("StorageSelected",checkedID+" ");
        switch (checkedID)
        {
            case R.id.phone_storage_option:
                localStorageOption = Constants.LOCAL_STORAGE_PHONE;
                break;
            case R.id.sd_card_storage_option:
                localStorageOption = Constants.LOCAL_STORAGE_SD_CARD;
                break;
        }
    }

    @Override
    public void onBackupProcessFinished()
    {
        backupFragmentScreenManipulationTask.showBackupDoneView();

    }

    @Override
    public void onBackupProcessFailed()
    {
        backupFragmentScreenManipulationTask.showBackupFailedView();
    }

    @Override
    public void onBackupRestoreFinished()
    {
        backupFragmentScreenManipulationTask.showRestoreDoneView();

    }

    @Override
    public void onBackupRestoreFailed()
    {
        backupFragmentScreenManipulationTask.showRestoreFailedView();

    }

    @Override
    public void onRestoreFromLocalStorageConfirmed()
    {
        if(AppPermissionTrackingTasks.hasReadExternalStoragePermission(activity))
            executeBackupRestoreTask(Constants.LOCAL_STORAGE_RESTORE);
    }

    @Override
    public void onRestoreFromCloudStorageConfirmed() {

    }

    @Override
    public void onWatchAdClicked()
    {
        fullScreenAdController.showRewardedVideoAd();
    }

    @Override
    public void onRewardedFromVideoAd()
    {
        switch (watchAdPurpose)
        {
            case Constants.LOCAL_STORAGE_BACKUP:
                executeBackupToLocalStorageTask(Constants.LOCAL_STORAGE_BACKUP);
                break;
            case Constants.LOCAL_STORAGE_RESTORE:
                backupFragmentScreenManipulationTask.showBackupRestoreWarningDialog(this);
                break;
        }

    }

}
