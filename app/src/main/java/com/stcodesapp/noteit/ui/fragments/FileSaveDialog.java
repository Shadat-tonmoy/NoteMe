package com.stcodesapp.noteit.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.controllers.fragmentController.FileSaveScreenController;
import com.stcodesapp.noteit.ui.activities.BaseActivity;
import com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView.FileSaveDialogScreenView;
import com.stcodesapp.noteit.ui.views.screens.dialogScreen.FileSaveDialogScreen;

import java.io.File;

public class FileSaveDialog extends DialogFragment implements FileSaveScreenController.Listener{


    private Activity activity;
    private FileSaveDialogScreenView fileSaveDialogScreenView;
    private FileSaveScreenController fileSaveScreenController;


    public static FileSaveDialog newInstance(Bundle args) {
        FileSaveDialog fragment = new FileSaveDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        fileSaveDialogScreenView = ((BaseActivity)requireActivity()).getCompositionRoot().getViewFactory().getFileSaveDialogScreenView(null);
        fileSaveScreenController = ((BaseActivity)requireActivity()).getCompositionRoot().getActivityControllerFactory().getFileSaveScreenController();
        fileSaveScreenController.bindView(fileSaveDialogScreenView);
        fileSaveScreenController.setListener(this);
        Bundle args = getArguments();
        if(args!=null)
        {
            String inputFilePath = args.getString(Tags.INPUT_FILE_PATH);
            fileSaveScreenController.bindInputFile(new File(inputFilePath));
        }
        builder.setView(fileSaveDialogScreenView.getRootView());
        return builder.create();

    }

    @Override
    public void onStart() {
        super.onStart();
        fileSaveScreenController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        fileSaveScreenController.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDismissDialog() {
        this.dismiss();
    }

    @Override
    public void onDisableCancellable()
    {
        this.setCancelable(false);
    }
}
