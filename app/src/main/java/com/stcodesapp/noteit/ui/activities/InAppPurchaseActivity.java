package com.stcodesapp.noteit.ui.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.SubscriptionType;
import com.stcodesapp.noteit.controllers.activityController.IAPActivityController;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.IAPScreenView;


public class InAppPurchaseActivity extends BaseActivity {


    private IAPScreenView iapScreenView;
    private IAPActivityController iapActivityController;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        iapScreenView = getCompositionRoot().getViewFactory().getIAPScreenView(null);
        iapActivityController = getCompositionRoot().getActivityControllerFactory().getIAPActivityController();
        iapActivityController.bindView(iapScreenView);
        setContentView(iapScreenView.getRootView());

    }

    @Override
    public void onStart() {
        super.onStart();
        iapActivityController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        iapActivityController.onStop();
    }
}