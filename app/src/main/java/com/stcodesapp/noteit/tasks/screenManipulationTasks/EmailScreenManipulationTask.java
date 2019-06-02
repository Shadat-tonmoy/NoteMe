package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;

import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;

public class EmailScreenManipulationTask {

    private Activity activity;
    private EmailFragmentScreenView emailFragmentScreenView;

    public EmailScreenManipulationTask(Activity activity) {
        this.activity = activity;
    }

    public void bindView(EmailFragmentScreenView emailFragmentScreenView) {
        this.emailFragmentScreenView = emailFragmentScreenView;
    }

    public void performFilter(String newText) {
        emailFragmentScreenView.getPhoneEmailListAdapter().getEmailFilteringTask().getFilter().filter(newText);
    }

    public boolean closeSearchView() {
        if(emailFragmentScreenView.getSearchView().isSearchOpen())
        {
            emailFragmentScreenView.getSearchView().closeSearch();
            return true;
        }
        return false;
    }
}
