package com.stcodesapp.noteit.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.controllers.dialogController.SortingOptionDialogController;
import com.stcodesapp.noteit.ui.activities.BaseActivity;
import com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView.SortingOptionDialogScreenView;

public class SortingOptionDialog  extends DialogFragment implements SortingOptionDialogController.Listener{

    /*@Override
    public void onDismissDialog() {
        this.dismiss();
    }*/

    public interface Listener{
        void onNoteTitleOptionSelected(int position);
        void onNoteTextOptionSelected(int position);
        void onNoteTimeOptionSelected(int position);
        void onNoteImportantOptionSelected(int position);
    }

    private Activity activity;
    private SortingOptionDialogScreenView sortingOptionDialogScreenView;
    private SortingOptionDialogController sortingOptionDialogController;
    private Listener listener;


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
        sortingOptionDialogController.setListener(this);

        sortingOptionDialogController.bindView(sortingOptionDialogScreenView);
        sortingOptionDialogController.bindBundle(getArguments());
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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onNoteTitleOptionSelected(int position) {
        listener.onNoteTitleOptionSelected(position);
        dismissDialog();

    }

    @Override
    public void onNoteTextOptionSelected(int position) {
        listener.onNoteTextOptionSelected(position);
        dismissDialog();

    }

    @Override
    public void onNoteTimeOptionSelected(int position) {
        listener.onNoteTimeOptionSelected(position);
        dismissDialog();

    }

    @Override
    public void onNoteImportantOptionSelected(int position) {
        listener.onNoteImportantOptionSelected(position);
        dismissDialog();
    }

    private void dismissDialog()
    {
        this.dismiss();
    }
}
