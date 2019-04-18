package com.stcodesapp.noteit.ui.activities;

import android.support.v7.app.AppCompatActivity;

import com.stcodesapp.noteit.common.CustomApplication;
import com.stcodesapp.noteit.common.dependencyInjection.ControllerCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private ControllerCompositionRoot controllerCompositionRoot;

    public ControllerCompositionRoot getCompositionRoot() {
        if(controllerCompositionRoot ==null)
        {
            controllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),this);
        }
        return controllerCompositionRoot;
    }
}
