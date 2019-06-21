package com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.constants.SortingType;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.AdNetwork;
import com.stcodesapp.noteit.ui.fragments.MoreOptionsBottomSheets;
import com.stcodesapp.noteit.ui.fragments.PhoneOrEmailListBottomSheets;
import com.stcodesapp.noteit.ui.fragments.SortingOptionDialog;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

import java.util.List;
import java.util.Objects;

public class HomeScreenManipulationTasks {


    public interface Listener{
        void onClearNoteClicked();
    }

    private Activity activity;
    private HomeScreen homeScreenView;
    private Listener listener;


    public HomeScreenManipulationTasks(Activity activity) {
        this.activity = activity;
    }

    public void bindView(HomeScreen homeScreenView) {
        this.homeScreenView = homeScreenView;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void bindNotes(List<Note> notes)
    {
        if(notes.size()> Constants.ZERO)
        {
            showNoteList();
            homeScreenView.getNoteListAdapter().bindNotes(notes);
        }
        else showNoNoteFound();

    }

    public void showContactBottomSheet(PhoneOrEmailListBottomSheets phoneOrEmailListBottomSheets)
    {
        phoneOrEmailListBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(), FragmentTags.CONTACT_BOTTOM_SHEET);
    }

    public void showEmailBottomSheet(PhoneOrEmailListBottomSheets phoneOrEmailListBottomSheets)
    {
        phoneOrEmailListBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(), FragmentTags.EMAIL_BOTTOM_SHEET);
    }

    public void showAddToFavoriteToast(Note note) {
        String message = activity.getResources().getString(R.string.added_to_favorite);
        if(!note.isImportant())
            message = activity.getResources().getString(R.string.removed_from_favorite);
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
    }

    public void showNoteRemovedToas()
    {
        Toast.makeText(activity,activity.getResources().getString(R.string.note_is_removed),Toast.LENGTH_SHORT).show();

    }

    public void hideAddButton() {
        homeScreenView.getNoteAddButton().setVisibility(View.GONE);
    }

    public void performFilter(String newText) {
        try {
            homeScreenView.getNoteListAdapter().getNoteFilteringTask().getFilter().filter(newText);
        }catch (Exception e)
        {

        }

    }

    public boolean closeSearchView() {
        if(homeScreenView.getSearchView().isSearchOpen())
        {
            homeScreenView.getSearchView().closeSearch();
            return true;
        }
        return false;
    }

    public void showSortingOptionDialog(SortingOptionDialog.Listener listener, Bundle args) {
        SortingOptionDialog sortingOptionDialog = new SortingOptionDialog();
        sortingOptionDialog.setArguments(args);
        sortingOptionDialog.setListener(listener);
        sortingOptionDialog.show(((AppCompatActivity)activity).getSupportFragmentManager(),FragmentTags.SORTING_OPTIONS);
    }

    public void setNoNoteFoundText(boolean isImportant)
    {
        String text = activity.getResources().getString(R.string.no_note_found);
        if(isImportant)
            text = activity.getResources().getString(R.string.no_important_note_found);
        homeScreenView.getNotFoundText().setText(text);
    }

    public void showNoNoteFound()
    {
        homeScreenView.getNoteList().setVisibility(View.GONE);
        homeScreenView.getNotFoundContainer().setVisibility(View.VISIBLE);
    }

    public void showNoteList()
    {
        homeScreenView.getNoteList().setVisibility(View.VISIBLE);
        homeScreenView.getNotFoundContainer().setVisibility(View.GONE);
    }

    public void sortNote(SortingType sortingType,boolean ascending)
    {
        homeScreenView.getNoteListAdapter().getNoteSortingTask().sortNotes(sortingType,ascending);

    }

    public void showMoreOptionBottomSheet(Note note,MoreOptionsBottomSheets.Listener listener) {
        Bundle args = new Bundle();
        args.putSerializable(Tags.NOTE,note);
        MoreOptionsBottomSheets moreOptionsBottomSheets = MoreOptionsBottomSheets.newInstance(args);
        moreOptionsBottomSheets.setListener(listener);
        moreOptionsBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(), FragmentTags.MORE_OPTION_BOTTOM_SHEET);
    }

    public void hideMoreOptionBottomSheet()
    {
        try {
            ((MoreOptionsBottomSheets) Objects.requireNonNull(((AppCompatActivity) activity)
                    .getSupportFragmentManager()
                    .findFragmentByTag(FragmentTags.MORE_OPTION_BOTTOM_SHEET)))
                    .dismiss();
        }catch (Exception e)
        {

        }
    }


    public void showClearNoteConfirmationDialog() {

        new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.clear_note))
                .setMessage(activity.getResources().getString(R.string.clear_note_msg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClearNoteClicked();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setIcon(activity.getResources().getDrawable(R.drawable.warning_red))
                .show();
    }

    public void loadBannerAd()
    {
        AdNetwork adNetwork = new AdMob((AdView) homeScreenView.getAdMobBannerAdContainer(),activity);
        adNetwork.loadBannerAd();

    }
}
