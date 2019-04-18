package com.stcodesapp.noteit.ui.fragments;

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
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.NavigationMenu2FragmentScreenView;

public class NavigationMenu2Fragment extends BaseFragment {

    private NavigationMenu2FragmentScreenView navigationMenu2FragmentScreenView
            ;
//    private SeconFr secondaryScreenController;

    public static NavigationMenu2Fragment newInstance()
    {
        Bundle args = new Bundle();
        NavigationMenu2Fragment navigationMenu2Fragment = new NavigationMenu2Fragment();
        navigationMenu2Fragment.setArguments(args);
        return navigationMenu2Fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        navigationMenu2FragmentScreenView = getCompositionRoot().getViewFactory().getNavigationMenu2FragmentScreenView(null);
//        secondaryScreenController= getCompositionRoot().getFragmentControllerFactory().getSecondaryController();
//        secondaryScreenController.bindView((NoteFieldScreenView) secondaryActivityScreenView);
//        ((AppCompatActivity)requireActivity()).setSupportActionBar(secondFragmentScreenView.getToolbar());
        setHasOptionsMenu(true);
        return navigationMenu2FragmentScreenView.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
//        secondaryScreenController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
//        secondaryScreenController.onStop();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_popup_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        secondaryScreenController.onOptionMenuClicked(item.getItemId());
        return true;
    }
}
