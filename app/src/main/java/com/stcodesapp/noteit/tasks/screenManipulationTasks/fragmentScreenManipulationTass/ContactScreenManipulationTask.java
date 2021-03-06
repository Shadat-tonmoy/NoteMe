package com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.AdNetwork;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;

import java.util.List;

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
        try {
            contactFragmentScreenView.getPhoneEmailListAdapter().getContactFilteringTask().getFilter().filter(newText);
        }catch (Exception e)
        {

        }

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

    public void bindContacts(List<Contact> contacts) {
        if(contacts.size()> Constants.ZERO)
        {
            showContactList();
            contactFragmentScreenView.getPhoneEmailListAdapter().bindObjects(contacts);
        }
        else showNoConntactFound();
    }

    public void loadBannerAd()
    {
        AdNetwork adNetwork = new AdMob(contactFragmentScreenView.getAdMobBannerAdView(),activity);
        adNetwork.loadBannerAd();

    }
}
