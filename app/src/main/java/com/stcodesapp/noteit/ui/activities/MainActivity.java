package com.stcodesapp.noteit.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.controllers.activityController.MainActivityController;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;

public class MainActivity extends BaseNavigationDrawerActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.logMessage("Dir", UtilityTasks.getTempDirectoryPath(this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCompositionRoot().getCurrentFragment().onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
