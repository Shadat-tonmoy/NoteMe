package com.stcodesapp.noteit.viewModel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.entities.Note;
import com.stcodesapp.noteit.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.tasks.DatabaseTasks.NoteUpdateTask;
import com.stcodesapp.noteit.ui.screens.NoteFieldScreen;

import java.util.Calendar;
import java.util.UUID;

public class NoteAddViewModel extends AndroidViewModel implements NoteFieldScreen.Listener,ColorPallateBottomSheets.Listener {

    private Activity activity;
    private MutableLiveData<Note> note;
    private ColorPallateBottomSheets colorPallateBottomSheets;



    public NoteAddViewModel(@NonNull Application context) {
        super(context);

    }

    public void bindActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onNoteAddDoneClick(String noteTitle, String noteText) {
        Intent returnIntent = new Intent();
        String noteId = UUID.randomUUID().toString();
        long time = Calendar.getInstance().getTimeInMillis();
        Note note = new Note(noteId,noteTitle,noteText,time,false);
//        Note note = this.note.getValue();
//        note.setId(noteId);
//        note.setNoteTitle(noteTitle);
//        note.setNoteText(noteText);
//        note.setCreationTime(time);
        Log.e("Note",note.toString());
        returnIntent.putExtra("note",note);
        activity.setResult(Activity.RESULT_OK,returnIntent);
        activity.finish();


    }

    public LiveData<Note> getNote() {
        return note;
    }

    public void setNote(MutableLiveData<Note> note) {
        this.note = note;
    }

    public void updateNote(Note note)
    {
        this.note.setValue(note);
    }

    @Override
    public void onNoteEditDoneClick(Note note) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("updated_note",note);
        activity.setResult(Activity.RESULT_OK,returnIntent);
        activity.finish();

    }

    @Override
    public void onNavigationItemClicked() {
        activity.finish();

    }

    public void onOptionItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.choose_color_menu:
                colorPallateBottomSheets = new ColorPallateBottomSheets();
                colorPallateBottomSheets.setListener(this);
                colorPallateBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(),"TAG");
        }

    }


    @Override
    public void onColorClicked(String colorName) {
        NoteUpdateTask noteUpdateTask = new NoteUpdateTask(activity);
        note.getValue().setBackgroundColor(colorName);
        noteUpdateTask.execute(note.getValue());
        updateNote(note.getValue());
        colorPallateBottomSheets.dismiss();



    }
}
