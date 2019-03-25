package com.stcodesapp.noteit.ui.screenViews;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.ui.screens.BaseObservableScreenViews;
import com.stcodesapp.noteit.ui.screens.NoteListScreen;

public class NoteListScreenView extends BaseObservableScreenViews<NoteListScreen.Listener> implements NoteListScreen {

    private RecyclerView noteListView;
    private FloatingActionButton noteAddFab;
    private NoteListAdapter noteListAdapter;


    public NoteListScreenView(LayoutInflater inflater, ViewGroup container)
    {
        setRootView(inflater.inflate(R.layout.note_list,container,false));
        initialize();
    }

    private void initialize()
    {
        inflateUIElements();
        initUserInteractions();
    }

    @Override
    public void inflateUIElements() {
        noteListView = findViewById(R.id.note_list);
        noteAddFab = findViewById(R.id.note_add_fab);
        noteListAdapter = new NoteListAdapter(getContext());
        noteListView.setAdapter(noteListAdapter);
        noteListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initUserInteractions() {
        noteAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener:getListener())
                {
                    listener.onNoteAddFabClick();
                }
            }
        });
    }

    public NoteListAdapter getNoteListAdapter()
    {
        return noteListAdapter;
    }
}
