package com.stcodesapp.noteit.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.SubscriptionType;
import com.stcodesapp.noteit.ui.activities.BaseActivity;


public class InAppPurchaseActivity extends BaseActivity {

    public interface IAPClickListener {
        void onMonthlySubsClicked();
        void onHalfYearlySubsClicked();
        void onLifetimeSubsClicked();
    }

    public static final String MONTHLY_SUBS = "MONTHLY_SUBS";
    public static final String HALF_YEARLY_SUBS = "HALF_YEARLY_SUBS";
    public static final String YEARLY_SUBS = "YEARLY_SUBS";
    public static final String AD_REMOVE = "AD_REMOVE";
    public static final String WATERMARK_REMOVE = "WATERMARK_REMOVE";
    public SubscriptionType subscriptionType = SubscriptionType.HALF_YEARLY_SUBS;

    private CardView monthlySubsBtn, halfYearlySubsBtn, /*yearlySubsBtn,*/ lifeTimeSubsBtn;
    private TextView doneButton,closeButton;
    private ImageView backButton;

    private IAPClickListener iapClickListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dialog_iap);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void init()
    {
        monthlySubsBtn = findViewById(R.id.monthly_subs_btn);
        halfYearlySubsBtn = findViewById(R.id.half_yearly_subs_btn);
        lifeTimeSubsBtn = findViewById(R.id.life_time_subs_btn);
        doneButton = findViewById(R.id.pro_continue_btn);
        closeButton = findViewById(R.id.pro_close_btn);
        backButton = findViewById(R.id.back_icon);

        monthlySubsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllForground();
                setMonthlyForeground();
                if(iapClickListener != null) {
                    clearAllForground();
                    setMonthlyForeground();
                }
            }
        });

        halfYearlySubsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllForground();
                setHalfYearlyForeground();
                if(iapClickListener != null) {
                    clearAllForground();
                    setHalfYearlyForeground();
                }
            }
        });

        lifeTimeSubsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllForground();
                setLifeTimeForeground();
                if(iapClickListener != null) {
                    clearAllForground();
                    setLifeTimeForeground();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iapClickListener != null) {
                    finish();
//                    getDialog().dismiss();
                }
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iapClickListener != null) {
                    switch (subscriptionType)
                    {
                        case MONTHLY_SUBS:
                            iapClickListener.onMonthlySubsClicked();
                            break;
                        case HALF_YEARLY_SUBS:
                            iapClickListener.onHalfYearlySubsClicked();
                            break;
                        case LIFETIME_SUBS:
                            iapClickListener.onLifetimeSubsClicked();
                            break;
                    }
//                    getDialog().dismiss();
                }
            }
        });
    }


    private void clearAllForground()
    {
        monthlySubsBtn.setForeground(getResources().getDrawable(R.drawable.default_package_fg));
        halfYearlySubsBtn.setForeground(getResources().getDrawable(R.drawable.default_package_fg));
        lifeTimeSubsBtn.setForeground(getResources().getDrawable(R.drawable.default_package_fg));

        setChildTextColor(monthlySubsBtn,getResources().getColor(R.color.text_default));
        setChildTextColor(halfYearlySubsBtn,getResources().getColor(R.color.text_default));
        setChildTextColor(lifeTimeSubsBtn,getResources().getColor(R.color.text_default));
    }

    private void setMonthlyForeground()
    {
        monthlySubsBtn.setForeground(getResources().getDrawable(R.drawable.selected_package_fg));
        setChildTextColor(monthlySubsBtn,getResources().getColor(R.color.pumpkin));
        subscriptionType = SubscriptionType.MONTHLY_SUBS;
    }

    private void setHalfYearlyForeground()
    {
        halfYearlySubsBtn.setForeground(getResources().getDrawable(R.drawable.selected_package_fg));
        setChildTextColor(halfYearlySubsBtn,getResources().getColor(R.color.pumpkin));
        subscriptionType = SubscriptionType.HALF_YEARLY_SUBS;
    }

    private void setLifeTimeForeground()
    {
        lifeTimeSubsBtn.setForeground(getResources().getDrawable(R.drawable.selected_package_fg));
        setChildTextColor(lifeTimeSubsBtn,getResources().getColor(R.color.pumpkin));
        subscriptionType = SubscriptionType.LIFETIME_SUBS;
    }


    private void setChildTextColor(CardView rootView, int color)
    {
        ConstraintLayout container = (ConstraintLayout) rootView.getChildAt(0);
        Log.e("TotalChild", container.getChildCount()+" ");
        for(int i=0;i<container.getChildCount();i++)
        {
            View view = container.getChildAt(i);
            if(view instanceof TextView)
            ((TextView)view).setTextColor(color);
        }
    }
}