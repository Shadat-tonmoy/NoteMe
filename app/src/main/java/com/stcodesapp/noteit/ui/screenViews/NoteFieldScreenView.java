package com.stcodesapp.noteit.ui.screenViews;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.BackgroundColors;
import com.stcodesapp.noteit.entities.Note;
import com.stcodesapp.noteit.ui.screens.BaseObservableScreenViews;
import com.stcodesapp.noteit.ui.screens.NoteFieldScreen;

import java.util.Calendar;

public class NoteFieldScreenView extends BaseObservableScreenViews<NoteFieldScreen.Listener> implements NoteFieldScreen {


    private EditText noteHeaderField,noteTextField;
    private FloatingActionButton noteDoneFab;
    private Toolbar toolbar;

    public NoteFieldScreenView(LayoutInflater layoutInflater, ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.activity_note_add,parent,false));
        initialize();
    }

    private void initialize()
    {
        inflateUIElements();
        initUserInteractions();
        setAddFabBehavior();

    }

    @Override
    public void populateNoteAddForm(Note note) {
        if(note!=null)
        {
            noteHeaderField.setText(note.getNoteTitle());
            noteTextField.setText(note.getNoteText());
            if(note.getBackgroundColor()!=null)
            {
                Log.e("BG",note.getBackgroundColor()+" IDS "+BackgroundColors.getColorMaps().get(note.getBackgroundColor()));
                getRootView().setBackgroundColor(getContext().getResources().getColor(BackgroundColors.getColorMaps().get(note.getBackgroundColor())));
            }
            setEditFabBehavior(note);
        }

    }

    private void setAddFabBehavior()
    {
        noteDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteHeaderField.getText().toString();
                String noteText = noteTextField.getText().toString();
                for(Listener listener:getListener())
                    listener.onNoteAddDoneClick(title,noteText);
            }
        });
    }

    private void setEditFabBehavior(Note note)
    {
        noteDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteHeaderField.getText().toString();
                String noteText = noteTextField.getText().toString();
                note.setNoteText(noteText);
                note.setNoteTitle(title);
                note.setCreationTime(Calendar.getInstance().getTimeInMillis());
                for(Listener listener:getListener())
                    listener.onNoteEditDoneClick(note);
            }
        });
    }

    @Override
    public void inflateUIElements() {
        noteHeaderField = findViewById(R.id.note_title);
        noteTextField = findViewById(R.id.note_text);
        noteDoneFab = findViewById(R.id.note_done_fab);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void initUserInteractions() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Clicked","Yes");
                for(Listener listener:getListener())
                    listener.onNavigationItemClicked();
            }
        });

    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
