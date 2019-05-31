package com.stcodesapp.noteit.ui.views.screens;


import android.view.View;

import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface HomeScreen extends BaseObservableScreen<HomeScreen.Listener> {

    interface Listener {

        void onOptionMenuClicked(int menuId);

        void onNoteAddButtonClicked();

    }

    NoteListAdapter getNoteListAdapter();

    View getNoteAddButton();


}
