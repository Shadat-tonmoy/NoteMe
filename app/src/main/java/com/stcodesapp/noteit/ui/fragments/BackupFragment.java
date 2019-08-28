package com.stcodesapp.noteit.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.controllers.fragmentController.BackupFragmentController;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.BackupFragmentScreenView;

public class BackupFragment extends BaseFragment 
{

    private BackupFragmentScreenView backupFragmentScreenView;
    private BackupFragmentController backupFragmentController;


    public static BackupFragment newInstance() {
        
        Bundle args = new Bundle();
        
        BackupFragment fragment = new BackupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void init(ViewGroup parent)
    {
        backupFragmentScreenView = getCompositionRoot().getViewFactory().getBackupFragmentScreenView(parent);
        backupFragmentController = getCompositionRoot().getFragmentControllerFactory().getBackupFragmentController();
        backupFragmentController.bindView(backupFragmentScreenView);
        backupFragmentController.onCreate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        backupFragmentController.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init(container);
        return backupFragmentScreenView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        backupFragmentController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        backupFragmentController.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
