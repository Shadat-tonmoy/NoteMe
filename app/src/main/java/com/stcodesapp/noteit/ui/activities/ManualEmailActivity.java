package com.stcodesapp.noteit.ui.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.controllers.activityController.ManualEmailActivityController;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.ManualEmailScreen;

public class ManualEmailActivity extends BaseActivity {

    private ManualEmailScreen manualEmailScreen;
    private ManualEmailActivityController manualEmailActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        manualEmailScreen = getCompositionRoot().getViewFactory().getManualEmailScreenView(null);
        manualEmailActivityController = getCompositionRoot().getActivityControllerFactory().getManualEmailActivityController();
        manualEmailActivityController.bindView(manualEmailScreen);
        setContentView(manualEmailScreen.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        manualEmailActivityController.onStart();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        manualEmailActivityController.onPostCreate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        manualEmailActivityController.onStop();
    }
}
