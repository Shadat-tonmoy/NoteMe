package com.stcodesapp.noteit.ui.views.screens.dialogScreen;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface SortingOptionDialogScreen extends BaseObservableScreen<SortingOptionDialogScreen.Listener> {

    interface Listener{

        void onNoteTitleOptionSelected(int position);

        void onNoteTextOptionSelected(int position);

        void onNoteTimeOptionSelected(int position);

        void onNoteImportantOptionSelected(int position);
    }
}
