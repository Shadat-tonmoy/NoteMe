package com.stcodesapp.noteit.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.controllers.HomeScreenController;
import com.stcodesapp.noteit.ui.views.screenViews.HomeScreenView;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

public class HomeScreenFragment extends BaseFragment {

    public HomeScreenView homeScreenView;
    public HomeScreenController homeScreenController;
    public NoteListAdapter noteListAdapter;

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
        homeScreenController.onAttach();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        homeScreenController.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu,menu);
        homeScreenView.onCreateOptionMenu(menu, (MaterialSearchView) requireActivity().findViewById(R.id.search_view));
        homeScreenView.getSearchView().setOnQueryTextListener(homeScreenController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        homeScreenController.onOptionMenuClicked(item.getItemId());
        return true;
    }

    public boolean onBackPressed()
    {
        return homeScreenController.onBackPressed();
    }


}
