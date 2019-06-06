package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;

public class ContactScreenManipulationTask {

    private Activity activity;
    private ContactFragmentScreenView contactFragmentScreenView;

    public ContactScreenManipulationTask(Activity activity) {
        this.activity = activity;
    }

    public void bindView(ContactFragmentScreenView contactFragmentScreenView) {
        this.contactFragmentScreenView = contactFragmentScreenView;
    }

    public void performFilter(String newText) {
        contactFragmentScreenView.getPhoneEmailListAdapter().getContactFilteringTask().getFilter().filter(newText);
    }

    public void showNoConntactFound()
    {
        contactFragmentScreenView.getContactList().setVisibility(View.GONE);
        contactFragmentScreenView.getNotFoundContainer().setVisibility(View.VISIBLE);
    }

    public void showContactList()
    {
        contactFragmentScreenView.getContactList().setVisibility(View.VISIBLE);
        contactFragmentScreenView.getNotFoundContainer().setVisibility(View.GONE);
    }

    public boolean closeSearchView() {
        if(contactFragmentScreenView.getSearchView().isSearchOpen())
        {
            contactFragmentScreenView.getSearchView().closeSearch();
            return true;
        }
        return false;
    }
}
