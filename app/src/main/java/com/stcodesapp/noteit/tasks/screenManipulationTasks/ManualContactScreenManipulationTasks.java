package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;

import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.ManualContactScreenView;

public class ManualContactScreenManipulationTasks {

    private Activity activity;
    private ManualContactScreenView manualContactScreenView;

    public ManualContactScreenManipulationTasks(Activity activity) {
        this.activity = activity;
    }

    public void bindView(ManualContactScreenView manualContactScreenView) {
        this.manualContactScreenView = manualContactScreenView;
    }

    public void initToolbar() {
        manualContactScreenView.initUserInteractions();
    }
}
