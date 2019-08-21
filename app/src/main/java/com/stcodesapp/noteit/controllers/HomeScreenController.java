package com.stcodesapp.noteit.controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.constants.AppMetadata;
import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.constants.SortingType;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.allDeletionTask.AllDeletionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.NoteDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllAudioSelectionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllImageSelectionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ImportantNoteSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.NoteSelectTask;
import com.stcodesapp.noteit.tasks.functionalTasks.DialogManagementTask;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileDeletingTask;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.RateUSPopupTrackingTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.HomeScreenManipulationTasks;
import com.stcodesapp.noteit.ui.fragments.MoreOptionsBottomSheets;
import com.stcodesapp.noteit.ui.fragments.PhoneOrEmailListBottomSheets;
import com.stcodesapp.noteit.ui.fragments.SortingOptionDialog;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeScreenController implements HomeScreen.Listener, NoteSelectTask.Listener, NoteListAdapter.Listener, ImportantNoteSelectTask.Listener, MaterialSearchView.OnQueryTextListener, SortingOptionDialog.Listener, MoreOptionsBottomSheets.Listener, AllDeletionTasks.Listener, NoteDeleteTask.Listener, HomeScreenManipulationTasks.Listener,AllImageSelectionTasks.Listener,AllAudioSelectionTasks.Listener,FileDeletingTask.Listener{


    private TasksFactory tasksFactory;
    private HomeScreen homeScreenView;
    private ActivityNavigationTasks activityNavigationTasks;
    private FragmentNavigationTasks fragmentNavigationTasks;
    private HomeScreenManipulationTasks homeScreenManipulationTasks;
    private boolean titleAsc = true, textAsc = true, timeAsc = true, importantAsc = true;

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
        homeScreenManipulationTasks.setListener(this);
        homeScreenManipulationTasks.loadBannerAd();
    }

    public void onAttach() {

    }


    public void onStart()
    {
        homeScreenView.registerListener(this);
        startFetchingNote();
    }

    public void onStartImportant()
    {
        homeScreenView.registerListener(this);
        homeScreenManipulationTasks.setNoNoteFoundText(true);
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
                homeScreenManipulationTasks.showSortingOptionDialog(this,getSortingSelectionBundle());
                break;
            case R.id.note_clear:
                homeScreenManipulationTasks.showClearNoteConfirmationDialog();
                break;

        }
    }

    public boolean onBackPressed()
    {
        boolean searchViewClosed = homeScreenManipulationTasks.closeSearchView();
        if(searchViewClosed)
            return true;
        else {
            RateUSPopupTrackingTasks rateUSPopupTrackingTasks = tasksFactory.getRateUSPopupTrackingTasks();
            if (!rateUSPopupTrackingTasks.isRateUsClicked() && rateUSPopupTrackingTasks.getTotalNote() >= AppMetadata.MAX_NOTE_TO_SHOW_RATEUS_POPUP)
            {
                DialogManagementTask dialogManagementTask = tasksFactory.getDialogManagementTask();
                dialogManagementTask.showRateUsDialog(true);
                return true;
            }
            else return false;

        }
    }

    private void updateNote(Note note)
    {
        tasksFactory.getDatabaseTasks().getNoteUpdateTask().execute(note);
    }

    private Bundle getSortingSelectionBundle()
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean(FragmentTags.TITILE_ASC,titleAsc);
        bundle.putBoolean(FragmentTags.TEXT_ASC,textAsc);
        bundle.putBoolean(FragmentTags.TIME_ASC,timeAsc);
        bundle.putBoolean(FragmentTags.IMPORTANT_ASC,importantAsc);
        return bundle;
    }

    private void clearNote()
    {
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        databaseTasks
                .getAllDeletionTasks(this, ComponentType.ALL_NOTE)
                .execute((long)Constants.ZERO);

    }

    @Override
    public void onNoteAddButtonClicked() {
        activityNavigationTasks.toNoteFieldScreen(new Bundle(),false);
    }

    @Override
    public void onNoteFetched(List<Note> fetchedNotes) {
//        Log.e("NoteFetched",fetchedNotes.size()+" i sisze");
       homeScreenManipulationTasks.bindNotes(fetchedNotes);
    }

    @Override
    public void onContactBadgeClicked(Note note) {
        Bundle args = new Bundle();
        args.putBoolean(FragmentTags.IS_CONTACT,true);
        args.putLong(FragmentTags.NOTE_ID,note.getId());
        PhoneOrEmailListBottomSheets phoneOrEmailListBottomSheets = PhoneOrEmailListBottomSheets.newInstance(args);
        homeScreenManipulationTasks.showContactBottomSheet(phoneOrEmailListBottomSheets);
    }

    @Override
    public void onEmailBadgeClicked(Note note) {
//        Log.e("WillFindEmailFor",note.toString());
        Bundle args = new Bundle();
        args.putBoolean(FragmentTags.IS_CONTACT,false);
        args.putLong(FragmentTags.NOTE_ID,note.getId());
        PhoneOrEmailListBottomSheets phoneOrEmailListBottomSheets = PhoneOrEmailListBottomSheets.newInstance(args);
        homeScreenManipulationTasks.showEmailBottomSheet(phoneOrEmailListBottomSheets);

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
    public void onMoreButtonClicked(Note note) {
        homeScreenManipulationTasks.showMoreOptionBottomSheet(note,this);

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
//        Log.e("selected","title "+position);
        boolean ascending = position == 0;
        titleAsc = ascending;
        homeScreenManipulationTasks.sortNote(SortingType.NOTE_TITLE,ascending);

    }

    @Override
    public void onNoteTextOptionSelected(int position) {
//        Log.e("selected","text "+position);
        boolean ascending = position == 0;
        textAsc = ascending;
        homeScreenManipulationTasks.sortNote(SortingType.NOTE_TEXT,ascending);

    }

    @Override
    public void onNoteTimeOptionSelected(int position) {
//        Log.e("selected","time "+position);
        boolean ascending = position == 0;
        timeAsc = ascending;
        homeScreenManipulationTasks.sortNote(SortingType.NOTE_TIME,ascending);

    }

    @Override
    public void onNoteImportantOptionSelected(int position) {
//        Log.e("selected","important "+position);
        boolean ascending = position == 0;
        importantAsc = ascending;
        homeScreenManipulationTasks.sortNote(SortingType.NOTE_IMPORTANT,ascending);

    }

    @Override
    public void onDeleteNoteClicked(Note note) {
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        databaseTasks
                .getNoteDeleteTask(this)
                .execute(note);
        AllAudioSelectionTasks allAudioSelectionTasks = tasksFactory.getAllAudioSelectionTasks(this,Constants.FOR_DELETING_AUDIOS);
        allAudioSelectionTasks.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,note.getId());
        AllImageSelectionTasks allImageSelectionTasks = tasksFactory.getAllImageSelectionTasks(this,Constants.FOR_DELETING_IMAGES);
        allImageSelectionTasks.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,note.getId());
        homeScreenManipulationTasks.hideMoreOptionBottomSheet();
    }

    @Override
    public void onDeleteContactClicked(Note note) {
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        note.setContactPriority(Constants.ZERO);
        databaseTasks.getNoteUpdateTask().execute(note);
        databaseTasks
                .getAllDeletionTasks(this, ComponentType.CONTACT)
                .execute(note.getId());
        homeScreenManipulationTasks.hideMoreOptionBottomSheet();
    }

    @Override
    public void onDeleteEmailClicked(Note note) {
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        note.setEmailPriority(Constants.ZERO);
        databaseTasks.getNoteUpdateTask().execute(note);
        databaseTasks
                .getAllDeletionTasks(this, ComponentType.EMAIL)
                .execute(note.getId());
        homeScreenManipulationTasks.hideMoreOptionBottomSheet();
    }

    @Override
    public void onDeleteImageClicked(Note note) {
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        note.setImagePriority(Constants.ZERO);
        databaseTasks.getNoteUpdateTask().execute(note);
        databaseTasks
                .getAllDeletionTasks(this, ComponentType.IMAGE)
                .execute(note.getId());
        homeScreenManipulationTasks.hideMoreOptionBottomSheet();
    }

    @Override
    public void onDeleteAudioClicked(Note note) {
        DatabaseTasks databaseTasks = tasksFactory.getDatabaseTasks();
        note.setAudioPriority(Constants.ZERO);
        databaseTasks.getNoteUpdateTask().execute(note);
        databaseTasks
                .getAllDeletionTasks(this, ComponentType.AUDIO)
                .execute(note.getId());
        homeScreenManipulationTasks.hideMoreOptionBottomSheet();
    }

    @Override
    public void onAllElementOfSingleNoteDeleted() {
        startFetchingNote();

    }

    @Override
    public void onNoteDeleted(Note note) {
        homeScreenManipulationTasks.showNoteRemovedToas();
        startFetchingNote();
    }


    @Override
    public void onClearNoteClicked() {
        clearNote();
    }

    @Override
    public void onAllAudioFetchedFetched(List<Audio> audios, int purpose) {
        if(purpose==Constants.FOR_DELETING_AUDIOS)
        {
            List<File> audioToBeDeleted = new ArrayList<>();
            for(Audio audio:audios)
            {
                if(audio.isFilePath())
                    audioToBeDeleted.add(new File(audio.getAudioUri()));
            }
            File[] audioArray = audioToBeDeleted.toArray(new File[0]);
            FileDeletingTask fileDeletingTask = tasksFactory.getFileDeletingTask(this);
            fileDeletingTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,audioArray);
        }
    }

    @Override
    public void onAllImageFetchedFetched(List<Image> images, int purpose)
    {
        if(purpose==Constants.FOR_DELETING_IMAGES)
        {
            List<File> imageToBeDeleted = new ArrayList<>();
            for(Image image:images)
            {
                if(image.isCaptured())
                    imageToBeDeleted.add(new File(image.getImageFilePath()));
            }
            File[] imageArray = imageToBeDeleted.toArray(new File[0]);
            FileDeletingTask fileDeletingTask = tasksFactory.getFileDeletingTask(this);
            fileDeletingTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,imageArray);
        }

    }

    @Override
    public void onFileDeleted(File file) {


    }
}
