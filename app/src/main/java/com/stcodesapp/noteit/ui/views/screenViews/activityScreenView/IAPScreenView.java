package com.stcodesapp.noteit.ui.views.screenViews.activityScreenView;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.IAPScreen;

public class IAPScreenView extends BaseObservableScreenView<IAPScreen.Listener> implements IAPScreen {

    private CardView monthlySubsBtn, halfYearlySubsBtn, yearlySubsBtn, lifeTimeSubsBtn;
    private TextView doneButton,closeButton,monthlyPrice,halfYearlyPrice,yearlyPrice,lifeTimePrice;
    private ImageView backButton;

    public IAPScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.iap_screen_layout,parent,false));
        inflateUIElements();
        initUserInteractions();
    }


    @Override
    public void initUserInteractions() {
        setClickListener(monthlySubsBtn,EventTypes.MONTHLY_SUBSCRIPTION_CLICKED);
        setClickListener(halfYearlySubsBtn,EventTypes.HALF_YEARLY_SUBSCRIPTION_CLICKED);
        setClickListener(yearlySubsBtn,EventTypes.YEARLY_SUBSCRIPTION_CLICKED);
        setClickListener(lifeTimeSubsBtn,EventTypes.LIFETIME_SUBSCRIPTION_CLICKED);
        setClickListener(doneButton,EventTypes.IAP_DONE_BUTTON_CLICKED);
        setClickListener(backButton,EventTypes.IAP_BACK_BUTTON_CLICKED);
        setClickListener(closeButton,EventTypes.IAP_BACK_BUTTON_CLICKED);

    }

    @Override
    public void inflateUIElements() {
        monthlySubsBtn = findViewById(R.id.monthly_subs_btn);
        halfYearlySubsBtn = findViewById(R.id.half_yearly_subs_btn);
        yearlySubsBtn = findViewById(R.id.yearly_subs_btn);
        lifeTimeSubsBtn = findViewById(R.id.life_time_subs_btn);
        monthlyPrice = findViewById(R.id.monthly_price);
        halfYearlyPrice = findViewById(R.id.half_yearly_price);
        yearlyPrice = findViewById(R.id.yearly_price);
        lifeTimePrice = findViewById(R.id.life_time_price);
        doneButton = findViewById(R.id.pro_continue_btn);
        closeButton = findViewById(R.id.pro_close_btn);
        backButton = findViewById(R.id.back_icon);
    }

    private void setClickListener(View view, final int eventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener:getListeners())
                {

                    switch (eventType)
                    {
                        case EventTypes.MONTHLY_SUBSCRIPTION_CLICKED:
                            listener.onMonthlySubsClicked();
                            break;
                        case EventTypes.HALF_YEARLY_SUBSCRIPTION_CLICKED:
                            listener.onHalfYearlySubsClicked();
                            break;
                        case EventTypes.YEARLY_SUBSCRIPTION_CLICKED:
                            listener.onYearlySubsClicked();
                            break;
                        case EventTypes.LIFETIME_SUBSCRIPTION_CLICKED:
                            listener.onLifetimeSubsClicked();
                            break;
                        case EventTypes.IAP_BACK_BUTTON_CLICKED:
                            listener.onNavigateUp();
                            break;
                        case EventTypes.IAP_DONE_BUTTON_CLICKED:
                            listener.onDoneButtonClicked();
                            break;

                    }
                }
            }
        });
    }

    public CardView getMonthlySubsBtn() {
        return monthlySubsBtn;
    }
    public CardView getHalfYearlySubsBtn() {
        return halfYearlySubsBtn;
    }

    public CardView getYearlySubsBtn() {
        return yearlySubsBtn;
    }


    public CardView getLifeTimeSubsBtn() {
        return lifeTimeSubsBtn;
    }


    public TextView getMonthlyPrice() {
        return monthlyPrice;
    }

    public TextView getHalfYearlyPrice() {
        return halfYearlyPrice;
    }

    public TextView getYearlyPrice() {
        return yearlyPrice;
    }

    public TextView getLifeTimePrice() {
        return lifeTimePrice;
    }
}
