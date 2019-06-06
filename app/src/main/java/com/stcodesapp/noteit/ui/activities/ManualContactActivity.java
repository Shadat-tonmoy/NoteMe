package com.stcodesapp.noteit.ui.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.controllers.activityController.ManualContactController;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.ManualContactScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class ManualContactActivity extends BaseActivity {


    ManualContactScreenView manualContactScreenView;
    ManualContactController manualContactController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    private void init()
    {
        manualContactScreenView = getCompositionRoot().getViewFactory().getManualContactScreenView(null);
        setContentView(manualContactScreenView.getRootView());
        manualContactController = getCompositionRoot().getActivityControllerFactory().getManualContactController();
        manualContactController.bindView(manualContactScreenView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        manualContactController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        manualContactController.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manualContactController.onDestroy();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setSupportActionBar(manualContactScreenView.getToolbar());
        manualContactController.onPostCreate();
    }
}
