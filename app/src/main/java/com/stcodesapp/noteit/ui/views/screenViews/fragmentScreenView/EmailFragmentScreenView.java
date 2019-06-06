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

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.PhoneEmailListAdapter;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.EmailFragmentScreen;

import java.util.List;

public class EmailFragmentScreenView extends BaseObservableScreenView<EmailFragmentScreen.Listener> implements EmailFragmentScreen {


    private PhoneEmailListAdapter phoneEmailListAdapter;
    private RecyclerView emailList;
    private MaterialSearchView searchView;
    private ConstraintLayout notFoundContainer;
    private TextView notFoundText;
    private ImageView notFoundImage;


    public EmailFragmentScreenView (LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.email_fragment_screen,parent,false));
        inflateUIElements();
        initUserInteractions();
    }

    @Override
    public void initUserInteractions() {

    }

    @Override
    public void inflateUIElements() {

        emailList= findViewById(R.id.email_list);
        notFoundContainer = findViewById(R.id.not_found_container);
        notFoundText = findViewById(R.id.not_found_text);
        notFoundImage = findViewById(R.id.not_found_image);
        phoneEmailListAdapter = new PhoneEmailListAdapter(getContext(),false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        emailList.setLayoutManager(layoutManager);
        emailList.setAdapter(phoneEmailListAdapter);
        notFoundImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.no_email));
        notFoundText.setText(getContext().getResources().getString(R.string.no_email_found));
    }

    public void onCreateOptionMenu(Menu menu, MaterialSearchView searchView) {
        MenuItem item = menu.findItem(R.id.email_search);
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

    public RecyclerView getEmailList() {
        return emailList;
    }

    public void bindEmails(List<Email> emails)
    {
        phoneEmailListAdapter.bindObjects(emails);
    }
}
