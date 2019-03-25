package com.stcodesapp.noteit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.BackgroundColors;

public class ColorGridAdapter extends BaseAdapter {
    private Context mContext;
    // Constructor
    public ColorGridAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return BackgroundColors.colorIds.length;
    }

    @Override
    public Object getItem(int position) {
        return BackgroundColors.colorIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View roundedLayout = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.rounded_view,parent,false);
        View roundedView = roundedLayout.findViewById(R.id.shape);
        roundedView.setBackground(mContext.getResources().getDrawable(BackgroundColors.colorIds[position]));
        return roundedView;
    }

}
