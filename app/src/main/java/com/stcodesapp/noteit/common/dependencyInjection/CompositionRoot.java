package com.stcodesapp.noteit.common.dependencyInjection;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import com.stcodesapp.noteit.common.FragmentFrameHelper;
import com.stcodesapp.noteit.factory.ControllerFactory;
import com.stcodesapp.noteit.factory.ModelFactory;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.factory.ViewFactory;

public class CompositionRoot {

    public ViewFactory getViewFactory(LayoutInflater layoutInflater) {
        return new ViewFactory(layoutInflater);
    }

    public ControllerFactory getActivityControllerFactory(FragmentActivity activity) {
        return new ControllerFactory(getTasksFactory(activity),activity);
    }


    public ControllerFactory getFragmentControllerFactory(FragmentActivity activity,FragmentFrameHelper fragmentFrameHelper) {
        return new ControllerFactory(getTasksFactory(activity,fragmentFrameHelper),activity);
    }

    public TasksFactory getTasksFactory(FragmentActivity activity) {
        return new TasksFactory(activity);
    }

    public TasksFactory getTasksFactory(FragmentActivity activity, FragmentFrameHelper fragmentFrameHelper) {
        return new TasksFactory(activity,fragmentFrameHelper);
    }

    public ModelFactory getModelFactory(){
        return new ModelFactory();
    }
}
