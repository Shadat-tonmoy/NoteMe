package com.stcodesapp.noteit.controllers.fragmentController;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

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

public class BackupFragmentController implements BackupFragmentScreen.Listener, BackupSavingTask.Listener, BackupRestoringTask.Listener, BackupFragmentScreenManipulationTask.Listener, DialogManagementTask.DialogOptionListener, AdMob.Listener
{

    private Activity activity;
    private TasksFactory tasksFactory;
    private BackupFragmentScreenView backupFragmentScreenView;
    private BackupFragmentScreenManipulationTask backupFragmentScreenManipulationTask;
    private int localStorageOption = Constants.LOCAL_STORAGE_PHONE,watchAdPurpose;
    private IAPTrackingTasks iapTrackingTasks;
    private FullScreenAdController fullScreenAdController;

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
    public void onBackupToCloudStorageClicked() {

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
    public void onRestoreFromCloudStorageClicked() {

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
                backupFragmentScreenManipulationTask.showUpgradeDialog(this);
                break;
        }

    }
}
