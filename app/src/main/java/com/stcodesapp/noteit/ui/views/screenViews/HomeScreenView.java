package com.stcodesapp.noteit.ui.views.screenViews;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

public class HomeScreenView extends BaseObservableScreenView<HomeScreen.Listener> implements HomeScreen{

    private FloatingActionButton noteAddButton;
    private RecyclerView noteList;
    private NoteListAdapter noteListAdapter;
    private MaterialSearchView searchView;
    private ConstraintLayout notFoundContainer;
    private TextView notFoundText;
    private ImageView notFoundImage;

    public HomeScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent, NoteListAdapter noteListAdapter)
    {
        this.noteListAdapter = noteListAdapter;
        setRootView(layoutInflater.inflate(R.layout.home_screen,parent,false));
        inflateUIElements();
        initUserInteractions();
    }


    @Override
    public void inflateUIElements() {
        noteAddButton = findViewById(R.id.note_add_button);
        noteList = findViewById(R.id.note_list);
        notFoundContainer = findViewById(R.id.not_found_container);
        notFoundText = findViewById(R.id.not_found_text);
        notFoundImage = findViewById(R.id.not_found_image);
        searchView = findViewById(R.id.search_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        noteList.setLayoutManager(layoutManager);
        noteList.setAdapter(noteListAdapter);
    }

    @Override
    public void initUserInteractions()
    {
        setClickListener(noteAddButton,EventTypes.NOTE_ADD_BUTTON_CLICKED);
    }

    private void setClickListener(View view, final int EventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener:getListeners())
                {
                    switch (EventType)
                    {
                        case EventTypes.NOTE_ADD_BUTTON_CLICKED:
                            listener.onNoteAddButtonClicked();
                            break;
                    }
                }

            }
        });
    }


    public void onCreateOptionMenu(Menu menu,MaterialSearchView searchView) {
        MenuItem item = menu.findItem(R.id.note_search);
        this.searchView = searchView;
        searchView.setMenuItem(item);
    }

    @Override
    public NoteListAdapter getNoteListAdapter() {
        return noteListAdapter;
    }

    @Override
    public View getNoteAddButton() {
        return noteAddButton;
    }

    @Override
    public MaterialSearchView getSearchView() {
        return searchView;
    }


    @Override
    public ConstraintLayout getNotFoundContainer() {
        return notFoundContainer;
    }

    @Override
    public RecyclerView getNoteList() {
        return noteList;
    }
}
