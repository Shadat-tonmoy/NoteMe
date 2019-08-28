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
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.CustomApplication;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.common.adController.FullScreenAdController;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.PermissionType;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.tasks.functionalTasks.DialogManagementTask;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.IAPTrackingTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks.BackupRestoringTask;
import com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks.BackupSavingTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.BackupFragmentScreenManipulationTask;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.BackupFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.BackupFragmentScreen;

import static android.app.Activity.RESULT_OK;

public class BackupFragmentController implements BackupFragmentScreen.Listener, BackupSavingTask.Listener, BackupRestoringTask.Listener, BackupFragmentScreenManipulationTask.Listener, DialogManagementTask.DialogOptionListener, AdMob.Listener
{

    private Activity activity;
    private TasksFactory tasksFactory;
    private BackupFragmentScreenView backupFragmentScreenView;
    private BackupFragmentScreenManipulationTask backupFragmentScreenManipulationTask;
    private int localStorageOption = Constants.LOCAL_STORAGE_PHONE,watchAdPurpose;
    private IAPTrackingTasks iapTrackingTasks;
    private FullScreenAdController fullScreenAdController;
    private GoogleSignInClient googleSignInClient;
    private boolean isAlreadySignedIn = false;

    public BackupFragmentController(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.backupFragmentScreenManipulationTask = tasksFactory.getBackupFragmentScreenManipulationTask();
        this.iapTrackingTasks = tasksFactory.getIAPTrackingTasks();
    }

    public void bindView(BackupFragmentScreenView backupFragmentScreenView)
    {
        this.backupFragmentScreenView = backupFragmentScreenView;
        this.backupFragmentScreenManipulationTask.bindView(backupFragmentScreenView);
        this.backupFragmentScreenManipulationTask.loadBannerAd();
    }


    public void onCreate()
    {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if(account!=null)
            isAlreadySignedIn = true;


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
        if (requestCode == RESULT_OK)
        {
            if(requestCode == RequestCode.GOOGLE_SIGN_IN_REQUEST)
            {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try
        {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(activity, "Successfully SignedIn", Toast.LENGTH_SHORT).show();

        } catch (ApiException e)
        {
            Toast.makeText(activity, "Google Sign In Failed", Toast.LENGTH_LONG).show();
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

    private void startGoogleSignInFlow()
    {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RequestCode.GOOGLE_SIGN_IN_REQUEST);
    }

    @Override
    public void onBackupToLocalStorageClicked()
    {
        /*executeBackupToLocalStorageTask(Constants.LOCAL_STORAGE_BACKUP);*/
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
        if(!isAlreadySignedIn)
        {
            startGoogleSignInFlow();
        }
        else
        {
            Toast.makeText(activity, "Backup to cloud", Toast.LENGTH_SHORT).show();
        }


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
        if(!isAlreadySignedIn)
        {
            startGoogleSignInFlow();
        }
        else
        {
            Toast.makeText(activity, "Restore From Cloud", Toast.LENGTH_SHORT).show();
        }

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
