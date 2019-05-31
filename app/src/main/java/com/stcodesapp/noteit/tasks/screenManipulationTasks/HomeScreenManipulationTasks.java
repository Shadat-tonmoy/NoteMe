package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.ui.fragments.PhoneNoListBottomSheets;
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

    public void showContactBottomSheet(PhoneNoListBottomSheets phoneNoListBottomSheets)
    {
        phoneNoListBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(), FragmentTags.CONTACT_BOTTOM_SHEET);
    }

    public void showEmailBottomSheet(PhoneNoListBottomSheets phoneNoListBottomSheets)
    {
        phoneNoListBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(), FragmentTags.EMAIL_BOTTOM_SHEET);
    }

    public void showAddToFavoriteToast(Note note) {
        String message = activity.getResources().getString(R.string.added_to_favorite);
        if(!note.isImportant())
            message = activity.getResources().getString(R.string.removed_from_favorite);
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
    }

    public void hideAddButton() {
        homeScreenView.getNoteAddButton().setVisibility(View.GONE);
    }
}
