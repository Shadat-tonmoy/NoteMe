package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;

import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.ui.views.screenViews.HomeScreenView;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

import java.util.List;

public class HomeScreenManipulationTasks {

    private Activity activity;
    private HomeScreen homeScreenView;


    public HomeScreenManipulationTasks(Activity activity) {
        this.activity = activity;
    }

    public void bindView(HomeScreen homeScreenView) {
        this.homeScreenView = homeScreenView;
    }

    public void bindNotes(List<Note> notes)
    {
        homeScreenView.getNoteListAdapter().bindNotes(notes);
    }

}
