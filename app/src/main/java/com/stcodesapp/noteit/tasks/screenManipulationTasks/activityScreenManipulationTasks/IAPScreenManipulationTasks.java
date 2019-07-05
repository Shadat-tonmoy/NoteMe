package com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.IAPIDs;
import com.stcodesapp.noteit.constants.IAPTypes;
import com.stcodesapp.noteit.models.ProductDetail;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.IAPScreenView;

public class IAPScreenManipulationTasks {

    private IAPScreenView iapScreenView;
    private Activity activity;


    public IAPScreenManipulationTasks(Activity activity) {
        this.activity = activity;

    }

    public void bindView(IAPScreenView iapScreenView) {
        this.iapScreenView = iapScreenView;
    }

    private void clearAllForeground()
    {
        iapScreenView.getMonthlySubsBtn().setForeground(activity.getResources().getDrawable(R.drawable.default_package_fg));
        iapScreenView.getHalfYearlySubsBtn().setForeground(activity.getResources().getDrawable(R.drawable.default_package_fg));
        iapScreenView.getLifeTimeSubsBtn().setForeground(activity.getResources().getDrawable(R.drawable.default_package_fg));

        setChildTextColor(iapScreenView.getMonthlySubsBtn(),activity.getResources().getColor(R.color.text_default));
        setChildTextColor(iapScreenView.getHalfYearlySubsBtn(),activity.getResources().getColor(R.color.text_default));
        setChildTextColor(iapScreenView.getLifeTimeSubsBtn(),activity.getResources().getColor(R.color.text_default));
    }

    public void setMonthlyForeground()
    {
        clearAllForeground();
        iapScreenView.getMonthlySubsBtn().setForeground(activity.getResources().getDrawable(R.drawable.selected_package_fg));
        setChildTextColor(iapScreenView.getMonthlySubsBtn(),activity.getResources().getColor(R.color.pumpkin));
    }

    public void setHalfYearlyForeground()
    {
        clearAllForeground();
        iapScreenView.getHalfYearlySubsBtn().setForeground(activity.getResources().getDrawable(R.drawable.selected_package_fg));
        setChildTextColor(iapScreenView.getHalfYearlySubsBtn(),activity.getResources().getColor(R.color.pumpkin));
    }

    public void setYearlyForeground()
    {
        clearAllForeground();
        iapScreenView.getYearlySubsBtn().setForeground(activity.getResources().getDrawable(R.drawable.selected_package_fg));
        setChildTextColor(iapScreenView.getYearlySubsBtn(),activity.getResources().getColor(R.color.pumpkin));
    }

    public void setLifeTimeForeground()
    {
        clearAllForeground();
        iapScreenView.getLifeTimeSubsBtn().setForeground(activity.getResources().getDrawable(R.drawable.selected_package_fg));
        setChildTextColor(iapScreenView.getLifeTimeSubsBtn(),activity.getResources().getColor(R.color.pumpkin));
    }

    public void updateIAPPrice(ProductDetail productDetail)
    {
        String iapID = productDetail.getProductId();
        String price = productDetail.getProductPrice();
        switch (iapID)
        {
            case IAPIDs.MONTHLY_SUBS:
                iapScreenView.getMonthlyPrice().setText(price);
                break;
            case IAPIDs.HALF_YEARLY_SUBS:
                iapScreenView.getHalfYearlyPrice().setText(price);
                break;
            case IAPIDs.YEARLY_SUBS:
                iapScreenView.getYearlyPrice().setText(price);
                break;
            case IAPIDs.LIFE_TIME_PURCHASE:
                iapScreenView.getLifeTimePrice().setText(price);
                break;
        }
    }


    private void setChildTextColor(CardView rootView, int color)
    {
        ConstraintLayout container = (ConstraintLayout) rootView.getChildAt(0);
        for(int i=0;i<container.getChildCount();i++)
        {
            View view = container.getChildAt(i);
            if(view instanceof TextView)
                ((TextView)view).setTextColor(color);
        }
    }

    public void showPurchaseSuccess()
    {
        showSuccessMessage();
        iapScreenView.getSuccessImage().setImageResource(R.drawable.success);
        iapScreenView.getSuccessText().setText(activity.getResources().getString(R.string.purchase_is_succeeded));
    }

    public void showAlreadyPaidUser()
    {
        showSuccessMessage();
        iapScreenView.getSuccessImage().setImageResource(R.drawable.animated_person);
        iapScreenView.getSuccessText().setText(activity.getResources().getString(R.string.already_paid));
    }

    public void showIAPPackage()
    {
        hideSuccessMessage();
        iapScreenView.getIapPackageLayout().setVisibility(View.VISIBLE);
    }

    public void hideIAPPackage()
    {
        iapScreenView.getIapPackageLayout().setVisibility(View.GONE);
    }

    public void showSuccessMessage()
    {
        hideIAPPackage();
        iapScreenView.getSuccessLayout().setVisibility(View.VISIBLE);
    }

    public void hideSuccessMessage()
    {
        iapScreenView.getSuccessLayout().setVisibility(View.GONE);
    }
}
