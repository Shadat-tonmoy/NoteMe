package com.stcodesapp.noteit.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.FrameLayout;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.controllers.commons.FragmentFrameWrapper;
import com.stcodesapp.noteit.controllers.commons.NavigationDrawerController;
import com.stcodesapp.noteit.ui.commons.NavigationDrawerView;

public class BaseNavigationDrawerActivity extends BaseActivity implements FragmentFrameWrapper {

    private NavigationDrawerView navigationDrawerView;
    private NavigationDrawerController navigationDrawerController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNavigationDrawerLayout();
    }

    private void initNavigationDrawerLayout()
    {
        navigationDrawerView = getCompositionRoot().getViewFactory().getNavigationDrawerView(null);
        navigationDrawerController = getCompositionRoot().getFragmentControllerFactory().getNavigationDrawerController();
        navigationDrawerController.bindView(navigationDrawerView);
        setActionBart();
        setContentView(navigationDrawerView.getRootView());
    }

    private void setActionBart()
    {
        setSupportActionBar(navigationDrawerView.getToolbar());
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,navigationDrawerView.getDrawerLayout(),navigationDrawerView.getToolbar(),R.string.drawer_open, R.string.drawer_close);
        navigationDrawerView.getDrawerLayout().setDrawerListener(actionBarDrawerToggle);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        actionBarDrawerToggle.syncState();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navigationDrawerController.onPostCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationDrawerController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        navigationDrawerController.onStop();
    }

    @Override
    public void onBackPressed() {
        if(navigationDrawerView.isDrawerOpen())
        {
            navigationDrawerView.closeNavDrawer();
        }
        else
        {
            super.onBackPressed();
            navigationDrawerController.onBackPressed();
        }
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return navigationDrawerView.getFragmentFrame();
    }
}
