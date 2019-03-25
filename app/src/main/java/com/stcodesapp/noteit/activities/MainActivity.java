package com.stcodesapp.noteit.activities;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.stcodesapp.noteit.controllers.NoteListController;
import com.stcodesapp.noteit.lifecycleObserver.MainActivityObserver;
import com.stcodesapp.noteit.ui.screenViews.NoteListScreenView;

public class MainActivity extends BaseActivity{

    private NoteListController noteListController;
    private NoteListScreenView noteListScreenView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        noteViewModel.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteListController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteListController.onStop();
    }

    private void init()
    {
        noteListScreenView = getCompositionRoot().getViewFactory().getNoteList(getLayoutInflater(),null);
        noteListController = getCompositionRoot().getControllerFactory().getNoteListController();
        noteListController.bindView(noteListScreenView);
        setContentView(noteListScreenView.getRootView());
    }

    private void initObserver()
    {
        getLifecycle().addObserver(new MainActivityObserver());
    }

}
