package com.stcodesapp.noteit.listeners;

import android.view.View;

import com.google.android.flexbox.FlexboxLayout;

public class RemoveImageListener implements View.OnClickListener {

    private FlexboxLayout container;
    private View imageView;

    public RemoveImageListener(FlexboxLayout container, View imageView) {
        this.container = container;
        this.imageView = imageView;
    }

    @Override
    public void onClick(View v) {
        container.removeView(imageView);
    }
}
