package com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.AdNetwork;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.CheckListFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.ContactFragmentScreenView;

import java.util.List;

public class CheckListScreenManipulationTask {

    private Activity activity;
    private CheckListFragmentScreenView checkListFragmentScreenView;

    public CheckListScreenManipulationTask(Activity activity) {
        this.activity = activity;
    }

    public void bindView(CheckListFragmentScreenView checkListFragmentScreenView) {
        this.checkListFragmentScreenView= checkListFragmentScreenView;
    }

    public void performFilter(String newText) {
        try {
            checkListFragmentScreenView.getPhoneEmailListAdapter().getCheckListFilteringTask().getFilter().filter(newText);
        }catch (Exception e)
        {

        }

    }

    public void showNoCheckListFoundFound()
    {
        checkListFragmentScreenView.getChecklistList().setVisibility(View.GONE);
        checkListFragmentScreenView.getNotFoundContainer().setVisibility(View.VISIBLE);
    }

    public void showCheckListList()
    {
        checkListFragmentScreenView.getChecklistList().setVisibility(View.VISIBLE);
        checkListFragmentScreenView.getNotFoundContainer().setVisibility(View.GONE);
    }

    public boolean closeSearchView() {
        if(checkListFragmentScreenView.getSearchView().isSearchOpen())
        {
            checkListFragmentScreenView.getSearchView().closeSearch();
            return true;
        }
        return false;
    }

    public void bindCheckLists(List<CheckList> checkLists) {
        if(checkLists.size()> Constants.ZERO)
        {
            showCheckListList();
            checkListFragmentScreenView.getPhoneEmailListAdapter().bindObjects(checkLists);
        }
        else showNoCheckListFoundFound();
    }

    public void loadBannerAd()
    {
        AdNetwork adNetwork = new AdMob(checkListFragmentScreenView.getAdMobBannerAdView(),activity);
        adNetwork.loadBannerAd();

    }
}
