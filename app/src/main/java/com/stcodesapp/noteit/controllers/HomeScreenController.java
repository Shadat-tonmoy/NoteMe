package com.stcodesapp.noteit.controllers;

import android.os.Bundle;
import android.util.Log;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.NoteSelectTask;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.HomeScreenManipulationTasks;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

import java.util.List;

public class HomeScreenController implements HomeScreen.Listener, NoteSelectTask.Listener, NoteListAdapter.Listener {


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

    public void onStart()
    {
        homeScreenView.registerListener(this);
        Log.e("onStart","Called");
        startFetchingNote();
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

    @Override
    public void onNoteAddButtonClicked() {
        activityNavigationTasks.toNoteFieldScreen(new Bundle());
    }

    @Override
    public void onNoteFetched(List<Note> fetchedNotes) {
       homeScreenManipulationTasks.bindNotes(fetchedNotes);
    }

    @Override
    public void onEditNoteClicked(Note note) {

    }

    @Override
    public void onDeleteNoteClicked(Note note) {

    }

    @Override
    public void onMoreClicked(Note note) {

    }

    @Override
    public void onNoteClicked(Note note) {
        Bundle args = new Bundle();
        args.putSerializable(Tags.NOTE,note);
        activityNavigationTasks.toNoteFieldScreen(args);

    }
}
