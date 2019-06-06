package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;

import java.util.List;

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

    public void showNoEmailFound()
    {
        emailFragmentScreenView.getEmailList().setVisibility(View.GONE);
        emailFragmentScreenView.getNotFoundContainer().setVisibility(View.VISIBLE);
    }

    public void showEmailList()
    {
        emailFragmentScreenView.getEmailList().setVisibility(View.VISIBLE);
        emailFragmentScreenView.getNotFoundContainer().setVisibility(View.GONE);
    }

    public void bindEmails(List<Email> emails) {
        if(emails.size()> Constants.ZERO)
        {
            showEmailList();
            emailFragmentScreenView.getPhoneEmailListAdapter().bindObjects(emails);
        }
        else showNoEmailFound();
    }
}
