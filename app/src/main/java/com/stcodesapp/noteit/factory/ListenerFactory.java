package com.stcodesapp.noteit.factory;

import android.view.View;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayout;
import com.stcodesapp.noteit.listeners.RemoveImageListener;

public class ListenerFactory {

    public RemoveImageListener getRemoveImageListener(FlexboxLayout container, View image)
    {
        return new RemoveImageListener(container,image);
    }
}
