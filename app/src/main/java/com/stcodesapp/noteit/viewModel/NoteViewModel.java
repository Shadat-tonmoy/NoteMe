package com.stcodesapp.noteit.viewModel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.activities.NoteAddActivity;
import com.stcodesapp.noteit.dao.NotesDao;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.entities.Note;
import com.stcodesapp.noteit.tasks.NoteDeleteTask;
import com.stcodesapp.noteit.tasks.NoteInsertTask;
import com.stcodesapp.noteit.tasks.NoteUpdateTask;
import com.stcodesapp.noteit.ui.DeleteConfirmationDialog;
import com.stcodesapp.noteit.ui.DialogUI;
import com.stcodesapp.noteit.ui.screens.NoteListScreen;
import com.stcodesapp.noteit.utils.RequestCodes;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NoteViewModel extends AndroidViewModel implements NoteListScreen.Listener, NoteListAdapter.Listener, DialogUI.Listener {

    private NoteDatabase noteDatabase;
    private NotesDao notesDao;
    private Context context;
    private LiveData<List<Note> > notes;
    private Activity activity;



    public NoteViewModel(Application context)
    {
        super(context);
        noteDatabase = NoteDatabase.getInstance(context);
        notesDao = noteDatabase.notesDao();
        notes = notesDao.getAllNoes();
        this.context = context;

    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insertNote(Note note)
    {
        new NoteInsertTask(context).execute(note);
    }

    public void updateNote(Note note)
    {
        new NoteUpdateTask(context).execute(note);
    }

    public void deleteNote(Note note)
    {
        new NoteDeleteTask(context).execute(note);
    }

    public void bindActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }

    @Override
    public void onNoteAddFabClick() {
        Intent intent = new Intent(context, NoteAddActivity.class);
        intent.putExtra("note",new Note());
        activity.startActivityForResult(intent, RequestCodes.ADD_NOTE);

    }

    @Override
    public void onEditNoteClick(Note note) {
        Intent intent = new Intent(activity,NoteAddActivity.class);
        intent.putExtra("note",note);
        activity.startActivityForResult(intent,RequestCodes.EDIT_NOTE);

    }

    @Override
    public void onDeleteNoteClick(Note note) {
        DeleteConfirmationDialog deleteConfirmationDialog = new DeleteConfirmationDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("note",note);
        deleteConfirmationDialog.setArguments(bundle);
        deleteConfirmationDialog.setListener(this);
        deleteConfirmationDialog.show(((AppCompatActivity)activity).getSupportFragmentManager(),"");

    }

    @Override
    public void onMoreClick(Note note) {
        Toast.makeText(activity, "MoreOption "+note.getNoteText(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteConfirm(DeleteConfirmationDialog deleteConfirmationDialog, Note note) {
        deleteNote(note);
        deleteConfirmationDialog.dismiss();
    }

    @Override
    public void onDeleteCancel(DeleteConfirmationDialog deleteConfirmationDialog) {
        deleteConfirmationDialog.dismiss();

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==RequestCodes.ADD_NOTE && resultCode==RESULT_OK)
        {
            Note addedNote = (Note) data.getSerializableExtra("note");
            insertNote(addedNote);
        }
        else if(requestCode==RequestCodes.EDIT_NOTE && resultCode==RESULT_OK)
        {
            Note updatedNote = (Note) data.getSerializableExtra("updated_note");
            updateNote(updatedNote);
        }

    }
}
