package com.stcodesapp.noteit.activities;

import android.support.v7.app.AppCompatActivity;

import com.stcodesapp.noteit.commons.CustomApplication;
import com.stcodesapp.noteit.commons.dependencyInjection.ControllerCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private ControllerCompositionRoot controllerCompositionRoot;

    public ControllerCompositionRoot getCompositionRoot() {
        if(controllerCompositionRoot==null)
            controllerCompositionRoot = new ControllerCompositionRoot(((CustomApplication) getApplication()).getCompositionRoot(),this);
        return controllerCompositionRoot;
    }
}
