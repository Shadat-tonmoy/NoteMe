package com.stcodesapp.noteit.controllers.activityController;

import android.app.Activity;

import com.android.billingclient.api.Purchase;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.IAPTypes;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.ProductDetail;
import com.stcodesapp.noteit.tasks.functionalTasks.IAPBillingTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks.IAPScreenManipulationTasks;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.IAPScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.IAPScreen;

import java.util.List;
import java.util.Set;

public class IAPActivityController implements IAPScreen.Listener, IAPBillingTasks.OnProductDetailFetchListener, IAPBillingTasks.OnPurchaseSuccessListener, IAPBillingTasks.OnExistingPurchaseFetchListener {

    private Activity activity;
    private TasksFactory tasksFactory;
    private IAPScreenManipulationTasks iapScreenManipulationTasks;
    private IAPScreenView iapScreenView;
    private int IAPType = IAPTypes.HALF_YEARLY_SUBS;
    private IAPBillingTasks iapBillingTasks;


    public IAPActivityController(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.iapScreenManipulationTasks = tasksFactory.getIAPScreenManipulationTasks();
        this.iapBillingTasks = tasksFactory.getIAPBillingTasks();
    }


    public void bindView(IAPScreenView iapScreenView) {
        this.iapScreenView = iapScreenView;
        iapScreenManipulationTasks.bindView(iapScreenView);
    }

    public void onStart()
    {
        iapScreenView.registerListener(this);
        iapBillingTasks.setOnProductDetailFetchListener(this);
        iapBillingTasks.setOnExistingPurchaseFetchListener(this);
        iapBillingTasks.setOnPurchaseSuccessListener(this);
        iapBillingTasks.setupBillingClient();
    }

    public void onStop()
    {
        iapScreenView.unregisterListener(this);
    }

    @Override
    public void onNavigateUp() {
        activity.finish();
    }

    @Override
    public void onMonthlySubsClicked() {
        iapScreenManipulationTasks.setMonthlyForeground();
        IAPType = IAPTypes.MONTHLY_SUBS;

    }

    @Override
    public void onHalfYearlySubsClicked() {
        iapScreenManipulationTasks.setHalfYearlyForeground();
        IAPType = IAPTypes.HALF_YEARLY_SUBS;

    }

    @Override
    public void onYearlySubsClicked() {
        iapScreenManipulationTasks.setYearlyForeground();
        IAPType = IAPTypes.YEARLY_SUBS;
    }

    @Override
    public void onLifetimeSubsClicked() {
        iapScreenManipulationTasks.setLifeTimeForeground();
        IAPType = IAPTypes.LIFE_TIME_PURCHASE;

    }

    @Override
    public void onDoneButtonClicked() {
        iapBillingTasks.setIAPModel(IAPType);


    }

    @Override
    public void onProductDetailFetched(List<ProductDetail> productDetails) {
        for(ProductDetail productDetail:productDetails)
        {
            iapScreenManipulationTasks.updateIAPPrice(productDetail);
        }

    }

    @Override
    public void onPurchaseSuccess(List<Purchase> purchases) {
        if(purchases.size()> Constants.ZERO)
        {
            iapScreenManipulationTasks.showPurchaseSuccess();
        }

    }

    @Override
    public void onExistingSubscriptionFetched(Set<Purchase> purchases) {
        if(purchases.size()> Constants.ZERO)
        {
            iapScreenManipulationTasks.showAlreadyPaidUser();
        }
        else
        {
            iapScreenManipulationTasks.showIAPPackage();
            iapBillingTasks.fetchAllProduct();
        }

    }
}
