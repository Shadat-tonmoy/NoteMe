package com.stcodesapp.noteit.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.controllers.HomeScreenController;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

public class HomeScreenFragment extends BaseFragment {

    private HomeScreen homeScreenView;
    private HomeScreenController homeScreenController;
    private NoteListAdapter noteListAdapter;

    public static HomeScreenFragment newInstance()
    {
        Bundle args = new Bundle();
        HomeScreenFragment homeScreenFragment = new HomeScreenFragment();
        homeScreenFragment.setArguments(args);
        return homeScreenFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        noteListAdapter = getCompositionRoot().getViewFactory().getNoteListAdapte(getContext(),homeScreenController);
        homeScreenView = getCompositionRoot().getViewFactory().getHomeScreenView(null,noteListAdapter);
        homeScreenController.bindView(homeScreenView);
        setHasOptionsMenu(true);
        return homeScreenView.getRootView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeScreenController = getCompositionRoot().getFragmentControllerFactory().getHomeScreenController();
        homeScreenController.onnAttach();
    }

    @Override
    public void onStart() {
        super.onStart();
        homeScreenController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        homeScreenController.onStop();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_popup_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        homeScreenController.onOptionMenuClicked(item.getItemId());
        return true;
    }


}
