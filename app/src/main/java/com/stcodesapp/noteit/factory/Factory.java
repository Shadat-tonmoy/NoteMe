package com.stcodesapp.noteit.factory;

import android.app.Activity;
import android.content.Context;

public class Factory {

    private ViewFactory viewFactory;
    private ModelFactory modelFactory;
    private ViewModelFactory viewModelFactory;
    private TasksFactory tasksFactory;
    private Activity activity;

    public Factory(Activity activity) {
        this.activity = activity;
    }

    public Context getContext()
    {
        return activity;
    }

    public ViewFactory getViewFactory() {
        return viewFactory = viewFactory == null ? new ViewFactory():viewFactory;
    }

    public ModelFactory getModelFactory() {
        return modelFactory = modelFactory == null ? new ModelFactory():modelFactory;
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory = viewModelFactory == null ?  new ViewModelFactory(activity): viewModelFactory;
    }

    public TasksFactory getTasksFactory() {
        return tasksFactory = tasksFactory == null ? new TasksFactory(activity):tasksFactory;
    }
}
