package com.stcodesapp.noteit.ui.fragments;

import android.support.v4.app.Fragment;

import com.stcodesapp.noteit.common.CustomApplication;
import com.stcodesapp.noteit.common.dependencyInjection.ControllerCompositionRoot;

public class BaseFragment extends Fragment {

    private ControllerCompositionRoot controllerCompositionRoot;

    public ControllerCompositionRoot getCompositionRoot() {
        if(controllerCompositionRoot ==null)
        {
            controllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) requireActivity().getApplication()).getCompositionRoot(),
                    requireActivity());
        }
        return controllerCompositionRoot;
    }
}
