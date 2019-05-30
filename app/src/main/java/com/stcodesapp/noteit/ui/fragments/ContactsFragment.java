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
import com.stcodesapp.noteit.controllers.fragmentController.ContactFragmentController;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;

public class ContactsFragment extends BaseFragment {

    private ContactFragmentScreenView contactFragmentScreenView;
    private ContactFragmentController contactFragmentController;

    public static ContactsFragment newInstance()
    {
        Bundle args = new Bundle();
        ContactsFragment contactsFragment = new ContactsFragment();
        contactsFragment.setArguments(args);
        return contactsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contactFragmentScreenView = getCompositionRoot().getViewFactory().getContactFragmentScreenView(null);
        contactFragmentController = getCompositionRoot().getFragmentControllerFactory().getContactFragmentController();
        contactFragmentController.bindView(contactFragmentScreenView);
        /*setHasOptionsMenu(true);*/
        return contactFragmentScreenView.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        contactFragmentController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        contactFragmentController.onStop();
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
