package com.stcodesapp.noteit.ui.views.screens;


import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface HomeScreen extends BaseObservableScreen<HomeScreen.Listener> {

    interface Listener {

        void onOptionMenuClicked(int menuId);

        void onNoteAddButtonClicked();

    }

    NoteListAdapter getNoteListAdapter();

    View getNoteAddButton();

    MaterialSearchView getSearchView();

    RecyclerView getNoteList();

    ConstraintLayout getNotFoundContainer();


}
