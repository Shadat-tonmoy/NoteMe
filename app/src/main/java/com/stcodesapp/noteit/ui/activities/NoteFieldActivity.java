package com.stcodesapp.noteit.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.controllers.activityController.NoteFieldController;
import com.stcodesapp.noteit.controllers.adController.NoteFieldAdController;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldActivity extends BaseActivity {


    private NoteFieldScreenView noteFieldScreenView;
    private NoteFieldController noteFieldController;
    private NoteFieldAdController noteFieldAdController;
    private NoteComponents noteComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        noteFieldScreenView = getCompositionRoot().getViewFactory().getNoteFieldScreenView(null);
        noteFieldController = getCompositionRoot().getActivityControllerFactory().getNoteFieldController();
        noteFieldAdController = getCompositionRoot().getActivityControllerFactory().getNoteFieldAdController();
        noteComponents = getCompositionRoot().getModelFactory().getNoteComponents();
        bindComponentsToController();
        noteFieldController.bindNoteComponents(noteComponents);
        noteFieldController.checkBundleForNote(getIntent().getExtras());
        noteFieldAdController.updateAdShowingStrategy();
        setContentView(noteFieldScreenView.getRootView());
    }

    private void bindComponentsToController()
    {
        noteFieldController.bindView(noteFieldScreenView);
        noteFieldAdController.bindView(noteFieldScreenView);
        noteFieldController.bindNoteFieldAdController(noteFieldAdController);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setSupportActionBar(noteFieldScreenView.getToolbar());
        noteFieldController.onPostCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        noteFieldController.onOptionItemSelected(item);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        noteFieldController.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        noteFieldController.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onBackPressed() {
        noteFieldController.onBackPressed();

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteFieldController.onStart();
        noteFieldAdController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteFieldController.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteFieldAdController.onDestroy();
    }

    public NoteFieldController getNoteFieldController() {
        return noteFieldController;
    }
}
