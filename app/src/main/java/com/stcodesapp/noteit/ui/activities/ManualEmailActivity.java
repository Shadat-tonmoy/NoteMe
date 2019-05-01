package com.stcodesapp.noteit.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.ManualEmailScreen;

public class ManualEmailActivity extends BaseActivity {

    private ManualEmailScreen manualEmailScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        manualEmailScreen = getCompositionRoot().getViewFactory().getManualEmailScreenView(null);
        setContentView(manualEmailScreen.getRootView());
    }
}
