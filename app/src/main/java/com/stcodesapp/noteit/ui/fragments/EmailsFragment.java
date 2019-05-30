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
import com.stcodesapp.noteit.controllers.fragmentController.EmailFragmentController;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;

public class EmailsFragment extends BaseFragment {

    private EmailFragmentScreenView emailFragmentScreenView;
    private EmailFragmentController emailFragmentController;

    public static EmailsFragment newInstance()
    {
        Bundle args = new Bundle();
        EmailsFragment emailsFragment = new EmailsFragment();
        emailsFragment.setArguments(args);
        return emailsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        emailFragmentScreenView = getCompositionRoot().getViewFactory().getEmailFragmentScreenView(null);
        emailFragmentController= getCompositionRoot().getFragmentControllerFactory().getEmailFragmentController();
        emailFragmentController.bindView(emailFragmentScreenView);
//        ((AppCompatActivity)requireActivity()).setSupportActionBar(secondFragmentScreenView.getToolbar());
        setHasOptionsMenu(true);
        return emailFragmentScreenView.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        emailFragmentController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        emailFragmentController.onStop();
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
