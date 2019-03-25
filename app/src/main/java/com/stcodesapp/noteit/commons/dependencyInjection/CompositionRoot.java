package com.stcodesapp.noteit.commons.dependencyInjection;

import android.app.Activity;

import com.stcodesapp.noteit.factory.ControllerFactory;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.factory.ViewFactory;

public class CompositionRoot {

    private ViewFactory viewFactory;

    public ViewFactory getViewFactory() {
        return viewFactory = viewFactory == null ? new ViewFactory() : viewFactory;
    }

    public ControllerFactory getControllerFactory(Activity activity)
    {
        return new ControllerFactory(getTasksFactory(activity));
    }

    private TasksFactory getTasksFactory(Activity activity)
    {
        return new TasksFactory(activity);
    }
}
