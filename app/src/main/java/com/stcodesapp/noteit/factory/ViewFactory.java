package com.stcodesapp.noteit.factory;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.stcodesapp.noteit.ui.commons.NavigationDrawerView;
import com.stcodesapp.noteit.ui.views.screenViews.HomeScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.NavigationMenu1FragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.NavigationMenu2FragmentScreenView;

public class ViewFactory {

    private final LayoutInflater layoutInflater;

    public ViewFactory(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }


    public NavigationDrawerView getNavigationDrawerView(ViewGroup parent)
    {
        return new NavigationDrawerView(layoutInflater,parent);
    }

    public HomeScreenView getHomeScreenView(ViewGroup parent)
    {
        return new HomeScreenView(layoutInflater,parent);
    }

    public NoteFieldScreenView getSecondaryScreenView(ViewGroup parent)
    {
        return new NoteFieldScreenView(layoutInflater,parent);
    }

    public NavigationMenu1FragmentScreenView getNavigationMenu1Screen(ViewGroup parent)
    {
        return new NavigationMenu1FragmentScreenView(layoutInflater,parent);
    }

    public NavigationMenu2FragmentScreenView getNavigationMenu2FragmentScreenView(ViewGroup parent)
    {
        return new NavigationMenu2FragmentScreenView(layoutInflater,parent);
    }
}
