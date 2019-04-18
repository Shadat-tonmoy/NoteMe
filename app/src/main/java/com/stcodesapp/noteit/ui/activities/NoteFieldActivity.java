package com.stcodesapp.noteit.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.controllers.activityController.NoteFieldController;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldActivity extends BaseActivity {


    private NoteFieldScreenView noteFieldScreenView;
    private NoteFieldController noteFieldController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        noteFieldScreenView = getCompositionRoot().getViewFactory().getSecondaryScreenView(null);
        noteFieldController = getCompositionRoot().getActivityControllerFactory().getSecondActivityController();
        noteFieldController.bindView(noteFieldScreenView);
        setContentView(noteFieldScreenView.getRootView());
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
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteFieldController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteFieldController.onStop();
    }
}
