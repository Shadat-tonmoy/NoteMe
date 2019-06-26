package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.promotionalTask.RateUSPopupTrackingTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.SharingTasks;

public class DialogManagementTask {

    private Activity activity;
    private TasksFactory tasksFactory;
    private SharingTasks sharingTasks;
    private RateUSPopupTrackingTasks rateUSPopupTrackingTasks;

    public DialogManagementTask(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.sharingTasks = tasksFactory.getSharingTasks();
        this.rateUSPopupTrackingTasks = tasksFactory.getRateUSPopupTrackingTasks();
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
}