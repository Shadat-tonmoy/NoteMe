package com.stcodesapp.noteit.ui;

import com.stcodesapp.noteit.entities.Note;
import com.stcodesapp.noteit.ui.screens.UIScreen;

public interface DialogUI extends UIScreen {

    interface Listener {
        void onDeleteConfirm(DeleteConfirmationDialog deleteConfirmationDialog,Note note);

        void onDeleteCancel(DeleteConfirmationDialog deleteConfirmationDialog);
    }

    void generateDialogUI();

    void setListener(Listener listener);
}
