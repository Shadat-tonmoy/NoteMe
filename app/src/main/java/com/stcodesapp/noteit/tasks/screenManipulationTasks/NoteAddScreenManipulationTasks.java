package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.entities.Note;
import com.stcodesapp.noteit.ui.screenViews.NoteFieldScreenView;

public class NoteAddScreenManipulationTasks {

    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;


    public NoteAddScreenManipulationTasks(Activity activity) {
        this.activity = activity;
    }

    public void bindView(NoteFieldScreenView noteFieldScreenView) {
        this.noteFieldScreenView = noteFieldScreenView;
        ((AppCompatActivity)activity).setSupportActionBar(noteFieldScreenView.getToolbar());
        Log.e("ActionBar","set");
        ((AppCompatActivity)activity).getSupportActionBar().setTitle("");
        ((AppCompatActivity)activity).getSupportActionBar().setIcon(activity.getResources().getDrawable(R.drawable.back_white));
        noteFieldScreenView.initUserInteractions();
    }



    public void populateScreenFromNote(Note note)
    {
        noteFieldScreenView.populateNoteAddForm(note);

    }
}
