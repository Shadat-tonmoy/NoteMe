package com.stcodesapp.noteit.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.stcodesapp.noteit.controllers.dialogController.SortingOptionDialogController;
import com.stcodesapp.noteit.ui.activities.BaseActivity;
import com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView.SortingOptionDialogScreenView;

public class SortingOptionDialog  extends DialogFragment/* implements CharacterLimitDialogController.Listener*/{

    /*@Override
    public void onDismissDialog() {
        this.dismiss();
    }*/

    private Activity activity;
    private SortingOptionDialogScreenView sortingOptionDialogScreenView;
    private SortingOptionDialogController sortingOptionDialogController;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        sortingOptionDialogScreenView = ((BaseActivity)requireActivity()).getCompositionRoot().getViewFactory().getSortingOptionDialogScreenView(null);
        sortingOptionDialogController = ((BaseActivity)requireActivity()).getCompositionRoot().getFragmentControllerFactory().getSortingOptionDialogController();
        sortingOptionDialogController.bindView(sortingOptionDialogScreenView);
//        sortingOptionDialogController.setListener(this);
        builder.setView(sortingOptionDialogScreenView.getRootView());
        return builder.create();

    }

    @Override
    public void onStart() {
        super.onStart();
        sortingOptionDialogController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        sortingOptionDialogController.onStop();
    }
}
