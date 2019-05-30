package com.stcodesapp.noteit.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.stcodesapp.noteit.adapter.NoteListAdapter;
import com.stcodesapp.noteit.ui.commons.NavigationDrawerView;
import com.stcodesapp.noteit.ui.views.screenViews.HomeScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.ManualContactScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.ManualEmailScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;

public class ViewFactory {

    private final LayoutInflater layoutInflater;

    public ViewFactory(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }


    public NavigationDrawerView getNavigationDrawerView(ViewGroup parent)
    {
        return new NavigationDrawerView(layoutInflater,parent);
    }

    public HomeScreenView getHomeScreenView(ViewGroup parent, NoteListAdapter noteListAdapter)
    {
        return new HomeScreenView(layoutInflater,parent, noteListAdapter);
    }

    public NoteFieldScreenView getSecondaryScreenView(ViewGroup parent)
    {
        return new NoteFieldScreenView(layoutInflater,parent);
    }

    public EmailFragmentScreenView getEmailFragmentScreenView(ViewGroup parent)
    {
        return new EmailFragmentScreenView(layoutInflater,parent);
    }

    public ContactFragmentScreenView getContactFragmentScreenView(ViewGroup parent)
    {
        return new ContactFragmentScreenView(layoutInflater,parent);
    }

    public ManualContactScreenView getManualContactScreenView(ViewGroup parent)
    {
        return new ManualContactScreenView(layoutInflater,parent);
    }

    public ManualEmailScreenView getManualEmailScreenView(ViewGroup parent)
    {
        return new ManualEmailScreenView(layoutInflater,parent);
    }

    public NoteListAdapter getNoteListAdapte(Context context, NoteListAdapter.Listener listener)
    {
        return new NoteListAdapter(context,listener);
    }

}
