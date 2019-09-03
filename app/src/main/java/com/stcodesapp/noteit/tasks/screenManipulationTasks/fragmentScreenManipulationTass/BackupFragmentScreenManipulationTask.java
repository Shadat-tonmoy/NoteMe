package com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.PermissionType;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.AdNetwork;
import com.stcodesapp.noteit.tasks.functionalTasks.DialogManagementTask;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.BackupFragmentScreenView;

public class BackupFragmentScreenManipulationTask
{

    public interface Listener{
        void onRestoreFromLocalStorageConfirmed();

        void onRestoreFromCloudStorageConfirmed();
    }

    private Activity activity;
    private BackupFragmentScreenView backupFragmentScreenView;
    private LinearLayout backupProgressView, backupDoneView;
    private TextView backupDoneText, backupProgressText;
    private ImageView backupDoneImage;
    private TasksFactory tasksFactory;

    public BackupFragmentScreenManipulationTask(Activity activity,TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
    }

    public void bindView(BackupFragmentScreenView backupFragmentScreenView) {
        this.backupFragmentScreenView = backupFragmentScreenView;
    }

    public void showPermissionRequiredMessage(PermissionType permissionType)
    {
        String message = activity.getResources().getString(R.string.permission_is_required);
        switch (permissionType)
        {
            case WRITE_EXTERNAL_STORAGE:
                message = activity.getResources().getString(R.string.write_storage_permission_is_required);
                break;
            case READ_EXTERNAL_STORAGE_PERMISSION:
                message = activity.getResources().getString(R.string.write_storage_permission_is_required);
                break;
        }
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void showBackupProgressDialog(int backupProcessType )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.backup_progress_dialog_layout,null,false);
        TextView doneButton = view.findViewById(R.id.done_button);
        backupDoneImage = view.findViewById(R.id.backup_done_image);
        backupProgressView = view.findViewById(R.id.backup_progress_view);
        backupDoneView = view.findViewById(R.id.backup_done_view);
        backupProgressText = view.findViewById(R.id.backup_progress_text);
        backupDoneText = view.findViewById(R.id.backup_done_text);
        if(backupProcessType == Constants.RESTORE)
        {
            backupProgressText.setText(activity.getResources().getString(R.string.restore_progress_text));
            backupDoneText.setText(activity.getResources().getString(R.string.restore_done_msg));
        }
        
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }

    public void showBackupRestoreWarningDialog(final Listener listener, final int restoreOption)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.backup_restore_warning_layout,null,false);
        TextView restoreButton = view.findViewById(R.id.restore_button);
        TextView noThanksButton = view.findViewById(R.id.no_thanks_button);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        noThanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                switch (restoreOption)
                {
                    case Constants.LOCAL_STORAGE_RESTORE:
                        listener.onRestoreFromLocalStorageConfirmed();
                        break;
                    case Constants.CLOUD_STORAGE_RESTORE:
                        listener.onRestoreFromCloudStorageConfirmed();
                        break;
                }


            }
        });
        alertDialog.show();
    }

    public void showBackupDoneView()
    {
        backupProgressView.setVisibility(View.GONE);
        backupDoneView.setVisibility(View.VISIBLE);
    }

    public void showBackupFailedView()
    {
        backupProgressView.setVisibility(View.GONE);
        backupDoneView.setVisibility(View.VISIBLE);
        backupDoneImage.setImageResource(R.drawable.warning_red);
        backupDoneText.setText(activity.getResources().getString(R.string.backup_failed_msg));
    }

    public void showRestoreFailedView()
    {
        backupProgressView.setVisibility(View.GONE);
        backupDoneView.setVisibility(View.VISIBLE);
        backupDoneImage.setImageResource(R.drawable.warning_red);
        backupDoneText.setText(activity.getResources().getString(R.string.restore_failed_msg));
    }
    
    public void showRestoreDoneView()
    {
        backupProgressView.setVisibility(View.GONE);
        backupDoneView.setVisibility(View.VISIBLE);
        backupDoneText.setText(activity.getResources().getString(R.string.backup_restore_success_message));
    }

    public void showUpgradeDialog(DialogManagementTask.DialogOptionListener dialogOptionListener) {
        DialogManagementTask dialogManagementTask = tasksFactory.getDialogManagementTask();
        dialogManagementTask.showUpgradeDialog(dialogOptionListener);
    }

    public void loadBannerAd()
    {
        AdNetwork adNetwork = new AdMob(backupFragmentScreenView.getAdMobBannerAdView(),activity);
        adNetwork.loadBannerAd();

    }
}
