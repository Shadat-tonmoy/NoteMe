package com.stcodesapp.noteit.ui.commons;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.commons.NavigationDrawerScreen;

public class NavigationDrawerView extends BaseObservableScreenView<NavigationDrawerScreen.Listener> implements NavigationDrawerScreen {

    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    public NavigationDrawerView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        super.setRootView(layoutInflater.inflate(R.layout.navigation_drawer_layout,parent,false));
        inflateUIElements();
        initUserInteractions();
    }

    public void closeNavDrawer() {
        drawerLayout.closeDrawers();
    }

    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(Gravity.START);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return frameLayout;
    }


    @Override
    public void initUserInteractions() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                for(Listener listener : getListeners())
                {
                    listener.onNavigationDrawerItemClicked(menuItem.getItemId());
                }
                return true;
            }
        });

    }

    @Override
    public void inflateUIElements() {
        toolbar = findViewById(R.id.app_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        frameLayout = findViewById(R.id.frame_content);
        navigationView = findViewById(R.id.navigation_view);

    }

    public void checkMenuItem(int itemId)
    {
        navigationView.setCheckedItem(itemId);
    }

    private void uncheckOtherMenu(int menuId)
    {
        Menu natvigationMenus = navigationView.getMenu();
        for(int i=0;i<natvigationMenus.size();i++)
        {
            MenuItem menuItem = natvigationMenus.getItem(i);
            if(menuItem.getItemId()!=menuId)
                natvigationMenus.getItem(i).setChecked(false);
        }
    }


}
