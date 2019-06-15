package com.stcodesapp.noteit.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.controllers.activityController.CheckListActivityController;
import com.stcodesapp.noteit.controllers.activityController.ManualContactController;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.CheckListScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.ManualContactScreenView;

public class CheckListActivity extends BaseActivity {

    private CheckListScreenView checkListScreenView;
    private CheckListActivityController checkListActivityController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    private void init()
    {
        checkListScreenView = getCompositionRoot().getViewFactory().getCheckListScreenView(null);
        setContentView(checkListScreenView.getRootView());
        checkListActivityController = getCompositionRoot().getActivityControllerFactory().getCheckListActivityController();
        checkListActivityController.bindView(checkListScreenView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checklist_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return checkListActivityController.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkListActivityController.onStart();
        checkListActivityController.checkIntentForExtra(getIntent());
    }

    @Override
    protected void onStop() {
        super.onStop();
        checkListActivityController.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setSupportActionBar(checkListScreenView.getToolbar());
        checkListActivityController.onPostCreate();
    }


}
