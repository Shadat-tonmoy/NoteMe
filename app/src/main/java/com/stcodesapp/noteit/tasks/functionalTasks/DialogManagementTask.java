package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.RateUSPopupTrackingTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.SharingTasks;

public class DialogManagementTask {

    private Activity activity;
    private TasksFactory tasksFactory;
    private SharingTasks sharingTasks;
    private RateUSPopupTrackingTasks rateUSPopupTrackingTasks;
    private ActivityNavigationTasks activityNavigationTasks;

    public interface DialogOptionListener{
        void onWatchAdClicked();
    }


    public DialogManagementTask(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.sharingTasks = tasksFactory.getSharingTasks();
        this.rateUSPopupTrackingTasks = tasksFactory.getRateUSPopupTrackingTasks();
        this.activityNavigationTasks = tasksFactory.getActivityNavigationTasks();
    }

    public void showRateUsDialog(final boolean exitApp) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rate_app_popup_dialog,null,false);
        TextView rateUsButton = view.findViewById(R.id.rate_button);
        TextView noThanksButton = view.findViewById(R.id.no_thanks_button);
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        noThanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if(exitApp)
                    activity.finish();
            }
        });
        rateUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateUSPopupTrackingTasks.setRateUsClicked(true);
                sharingTasks.openAppLink(activity.getPackageName());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void showUpgradeDialog(final DialogOptionListener listener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.upgrade_to_pro_popup_dialog,null,false);
        TextView upgradeButton = view.findViewById(R.id.upgrade_button);
        TextView noThanksButton = view.findViewById(R.id.no_thanks_button);
        TextView watchAdButton = view.findViewById(R.id.watch_add_button);
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        noThanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        watchAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWatchAdClicked();
                alertDialog.dismiss();

            }
        });
        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityNavigationTasks.toProVersionScreen();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public static void showBackupToCloudDoneDialog(Activity activity,int cloudBackupEventType, boolean isSuccess) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cloud_backup_done_popup_dialog,null,false);
        ImageView cloudBackupDoneIcon = view.findViewById(R.id.cloud_backup_done_icon);
        TextView gotItButton = view.findViewById(R.id.got_it_button);
        TextView cloudBackupDoneTitle = view.findViewById(R.id.cloud_backup_done_title);
        TextView cloudBackupDoneMessage = view.findViewById(R.id.cloud_backup_done_message);
        if(!isSuccess)
            cloudBackupDoneIcon.setImageResource(R.drawable.close_white);
        if(cloudBackupEventType== EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED)
        {
            if(isSuccess)
            {
                cloudBackupDoneTitle.setText(activity.getResources().getString(R.string.cloud_restore_done_title));
                cloudBackupDoneMessage.setText(activity.getResources().getString(R.string.cloud_restore_done_message));
            }
            else
            {
                cloudBackupDoneTitle.setText(activity.getResources().getString(R.string.cloud_restore_failed_title));
                cloudBackupDoneMessage.setText(activity.getResources().getString(R.string.cloud_restore_failed_message));
            }
        }
        else
        {
            if(!isSuccess)
            {
                cloudBackupDoneTitle.setText(activity.getResources().getString(R.string.cloud_backup_failed_title));
                cloudBackupDoneMessage.setText(activity.getResources().getString(R.string.cloud_backup_failed_message));
                //replace with failure message
            }
        }
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        gotItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


}
