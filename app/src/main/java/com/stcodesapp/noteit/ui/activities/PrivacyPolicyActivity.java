package com.stcodesapp.noteit.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.controllers.activityController.NoteFieldController;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.PrivacyPolicyScreenView;

public class PrivacyPolicyActivity extends BaseActivity {


    private PrivacyPolicyScreenView privacyPolicyScreenView;
    private NoteFieldController noteFieldController;
    private NoteComponents noteComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        privacyPolicyScreenView = getCompositionRoot().getViewFactory().getPrivacyPolicyScreenView(null);
        /*noteFieldController = getCompositionRoot().getActivityControllerFactory().getNoteFieldController();
        noteComponents = getCompositionRoot().getModelFactory().getNoteComponents();
        noteFieldController.bindView(noteFieldScreenView);
        noteFieldController.bindNoteComponents(noteComponents);
        noteFieldController.checkBundleForNote(getIntent().getExtras());*/
        setContentView(privacyPolicyScreenView.getRootView());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /*setSupportActionBar(noteFieldScreenView.getToolbar());
        noteFieldController.onPostCreate();*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*noteFieldController.onStart();*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*noteFieldController.onStop();*/
    }
}
