package com.stcodesapp.noteit.ui.screens;

import com.stcodesapp.noteit.entities.Note;

public interface NoteFieldScreen extends BaseScreen {

    interface Listener {
        void onNoteAddDoneClick(String noteTitle,String noteText);

        void onNoteEditDoneClick(Note note);

        void onNavigationItemClicked();
    }

    void populateNoteAddForm(Note note);





}
