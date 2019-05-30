package com.stcodesapp.noteit.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.databaseTasks.NoteUpdateTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.NoteSelectTask;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.HomeScreenManipulationTasks;
import com.stcodesapp.noteit.ui.fragments.PhoneNoListBottomSheets;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeScreenController implements HomeScreen.Listener, NoteSelectTask.Listener, NoteListAdapter.Listener{


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
        startFetchingNote();
    }


    public void onStart()
    {
        homeScreenView.registerListener(this);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            Log.e("onActivityResult","FromController result ok request code"+requestCode);
            switch (requestCode)
            {
                case RequestCode.ADD_NEW_NOTE:
                    checkForAddedNote(data);
                    break;
                case RequestCode.UPDATE_NOTE:
                    checkForUpdatedNote(data);
                    break;
            }
        }
    }

    private void checkForAddedNote(Intent data)
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
    }

    public void onOptionMenuClicked(int menuId) {
        switch (menuId)
        {
            case R.id.popup_menu_1:
                activityNavigationTasks.toSettingsScreen(new Bundle());
                break;
            case R.id.popup_menu_2:
                break;
            case R.id.popup_menu_3:
                break;
        }
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
}
