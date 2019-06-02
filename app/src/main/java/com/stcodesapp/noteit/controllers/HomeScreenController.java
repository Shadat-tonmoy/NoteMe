package com.stcodesapp.noteit.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.constants.SortingType;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.controllers.dialogController.SortingOptionDialogController;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.databaseTasks.NoteUpdateTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ImportantNoteSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.NoteSelectTask;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.HomeScreenManipulationTasks;
import com.stcodesapp.noteit.ui.fragments.PhoneNoListBottomSheets;
import com.stcodesapp.noteit.ui.fragments.SortingOptionDialog;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeScreenController implements HomeScreen.Listener, NoteSelectTask.Listener, NoteListAdapter.Listener, ImportantNoteSelectTask.Listener, MaterialSearchView.OnQueryTextListener, SortingOptionDialog.Listener {


    private TasksFactory tasksFactory;
    private HomeScreen homeScreenView;
    private ActivityNavigationTasks activityNavigationTasks;
    private FragmentNavigationTasks fragmentNavigationTasks;
    private HomeScreenManipulationTasks homeScreenManipulationTasks;

    public HomeScreenController(TasksFactory tasksFactory)
    {
        this.tasksFactory = tasksFactory;
        this.activityNavigationTasks = tasksFactory.getActivityNavigationTasks();
        this.fragmentNavigationTasks = tasksFactory.getFragmentNavigationTasks();
        this.homeScreenManipulationTasks = tasksFactory.getHomeScreenManipulationTasks();
    }

    public void bindView(HomeScreen homeScreenView) {
        this.homeScreenView = homeScreenView;
        homeScreenManipulationTasks.bindView(homeScreenView);
    }

    public void onAttach() {
//        startFetchingNote();
    }


    public void onStart()
    {
        homeScreenView.registerListener(this);
        startFetchingNote();
    }

    public void onStartImportant()
    {
        homeScreenView.registerListener(this);
        startFetchingImportantNote();
    }

    public void onStop()
    {
        homeScreenView.unregisterListener(this);
    }

    private void startFetchingNote()
    {
        tasksFactory.getDatabaseTasks()
                .getNoteSelectTask(this)
                .execute();
    }

    private void startFetchingImportantNote()
    {
        homeScreenManipulationTasks.hideAddButton();
        tasksFactory.getDatabaseTasks()
                .getImportantNoteSelectTask(this)
                .execute();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            /*Log.e("onActivityResult","FromController result ok request code"+requestCode);
            switch (requestCode)
            {
                case RequestCode.ADD_NEW_NOTE:
                    checkForAddedNote(data);
                    break;
                case RequestCode.UPDATE_NOTE:
                    checkForUpdatedNote(data);
                    break;
            }*/
        }
    }

    /*private void checkForAddedNote(Intent data)
    {
        boolean isAdded = data.getBooleanExtra(Tags.NOTE_ADDED,false);
        Log.e("NoteAdded",isAdded+" is result");
        if(isAdded)
            startFetchingNote();
    }

    private void checkForUpdatedNote(Intent data)
    {
        boolean isUpdated = data.getBooleanExtra(Tags.NOTE_UPDATED,false);
        Log.e("NoteUpdated",isUpdated+" is result");
        if(isUpdated)
            startFetchingNote();
    }*/

    public void onOptionMenuClicked(int menuId) {
        switch (menuId)
        {
            case R.id.note_sort:
                homeScreenManipulationTasks.showSortingOptionDialog(this);
                break;

        }
    }

    public boolean onBackPressed()
    {
        return homeScreenManipulationTasks.closeSearchView();
    }

    private void updateNote(Note note)
    {
        tasksFactory.getDatabaseTasks().getNoteUpdateTask().execute(note);
    }

    @Override
    public void onNoteAddButtonClicked() {
        activityNavigationTasks.toNoteFieldScreen(new Bundle(),false);
    }

    @Override
    public void onNoteFetched(List<Note> fetchedNotes) {
        Log.e("NoteFetched",fetchedNotes.size()+" i sisze");
       homeScreenManipulationTasks.bindNotes(fetchedNotes);
    }

    @Override
    public void onContactBadgeClicked(Note note) {
        Bundle args = new Bundle();
        args.putBoolean(FragmentTags.IS_CONTACT,true);
        args.putLong(FragmentTags.NOTE_ID,note.getId());
        PhoneNoListBottomSheets phoneNoListBottomSheets = PhoneNoListBottomSheets.newInstance(args);
        homeScreenManipulationTasks.showContactBottomSheet(phoneNoListBottomSheets);
    }

    @Override
    public void onEmailBadgeClicked(Note note) {
        Log.e("WillFindEmailFor",note.toString());
        Bundle args = new Bundle();
        args.putBoolean(FragmentTags.IS_CONTACT,false);
        args.putLong(FragmentTags.NOTE_ID,note.getId());
        PhoneNoListBottomSheets phoneNoListBottomSheets = PhoneNoListBottomSheets.newInstance(args);
        homeScreenManipulationTasks.showEmailBottomSheet(phoneNoListBottomSheets);

    }

    @Override
    public void onNoteClicked(Note note) {
        Bundle args = new Bundle();
        args.putSerializable(Tags.NOTE,note);
        activityNavigationTasks.toNoteFieldScreen(args,true);

    }

    @Override
    public void onAddToFavoriteClicked(Note note) {
        updateNote(note);
        homeScreenManipulationTasks.showAddToFavoriteToast(note);
    }

    @Override
    public void onImportantNoteFetched(List<Note> fetchedNotes) {
        homeScreenManipulationTasks.bindNotes(fetchedNotes);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        homeScreenManipulationTasks.performFilter(newText);
        return false;
    }

    @Override
    public void onNoteTitleOptionSelected(int position) {
        Log.e("selected","title "+position);
        boolean ascending = position == 0;
        homeScreenManipulationTasks.sortNote(SortingType.NOTE_TITLE,ascending);

    }

    @Override
    public void onNoteTextOptionSelected(int position) {
        Log.e("selected","text "+position);

    }

    @Override
    public void onNoteTimeOptionSelected(int position) {
        Log.e("selected","time "+position);

    }

    @Override
    public void onNoteImportantOptionSelected(int position) {
        Log.e("selected","important "+position);

    }
}
