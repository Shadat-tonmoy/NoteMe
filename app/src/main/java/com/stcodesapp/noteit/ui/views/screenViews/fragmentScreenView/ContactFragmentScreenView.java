package com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.PhoneEmailListAdapter;
import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.ContactFragmentScreen;

import java.util.List;

public class ContactFragmentScreenView extends BaseObservableScreenView<ContactFragmentScreen.Listener> implements ContactFragmentScreen {


    private PhoneEmailListAdapter phoneEmailListAdapter;
    private RecyclerView contactList;
    private MaterialSearchView searchView;
    private ConstraintLayout notFoundContainer;
    private TextView notFoundText;
    private ImageView notFoundImage;
    private AdView adMobBannerAdView;



    public ContactFragmentScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.contact_fragment_screen,parent,false));
        inflateUIElements();
        initUserInteractions();
    }

    @Override
    public void initUserInteractions() {

    }

    @Override
    public void inflateUIElements() {
        contactList= findViewById(R.id.contact_list);
        notFoundContainer = findViewById(R.id.not_found_container);
        notFoundText = findViewById(R.id.not_found_text);
        notFoundImage = findViewById(R.id.not_found_image);
        phoneEmailListAdapter = new PhoneEmailListAdapter(getContext(), ComponentType.CONTACT);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        contactList.setLayoutManager(layoutManager);
        contactList.setAdapter(phoneEmailListAdapter);
        adMobBannerAdView = findViewById(R.id.admob_banner_ad_view);
        notFoundImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.no_contact));
        notFoundText.setText(getContext().getResources().getString(R.string.no_contact_found));

    }

    public void onCreateOptionMenu(Menu menu, MaterialSearchView searchView) {
        MenuItem item = menu.findItem(R.id.contact_search);
        this.searchView = searchView;
        searchView.setMenuItem(item);
    }

    public MaterialSearchView getSearchView() {
        return searchView;
    }

    public PhoneEmailListAdapter getPhoneEmailListAdapter() {
        return phoneEmailListAdapter;
    }

    public ConstraintLayout getNotFoundContainer() {
        return notFoundContainer;
    }

    public RecyclerView getContactList() {
        return contactList;
    }

    public void bindContacts(List<Contact> contacts)
    {
        phoneEmailListAdapter.bindObjects(contacts);
    }

    public AdView getAdMobBannerAdView() {
        return adMobBannerAdView;
    }
}
