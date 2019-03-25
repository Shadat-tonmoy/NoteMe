package com.stcodesapp.noteit.ui;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.entities.Note;

public class DeleteConfirmationDialog extends DialogFragment implements DialogUI{

    private Listener listener;
    private Activity activity;
    private View rootView;
    private Note note;
    private Button okButton,cancelButton;
    private DeleteConfirmationDialog deleteConfirmationDialog;

    public DeleteConfirmationDialog()
    {
        deleteConfirmationDialog = this;
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
        LayoutInflater inflater = activity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.delete_confirmation_dialog,null);
        generateDialogUI();
        retrieveBundle();
        builder.setView(rootView);
        return builder.create();

    }

    @Override
    public void generateDialogUI() {
        okButton = rootView.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteConfirm(deleteConfirmationDialog,note);
            }
        });


        cancelButton = rootView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteCancel(deleteConfirmationDialog);
            }
        });

    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    private void retrieveBundle()
    {
        Bundle bundle = getArguments();
        note = (Note) bundle.getSerializable("note");

    }


}
