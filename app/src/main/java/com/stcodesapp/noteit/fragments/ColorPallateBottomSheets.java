package com.stcodesapp.noteit.fragments;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.ColorGridAdapter;
import com.stcodesapp.noteit.constants.BackgroundColors;

public class ColorPallateBottomSheets extends BottomSheetDialogFragment {

    public interface Listener{
        void onColorClicked(String colorName);
    }

    private Listener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_pallete_layout,container,false);
        GridView colorGrid = view.findViewById(R.id.color_grid);
        ColorGridAdapter colorGridAdapter = new ColorGridAdapter(getContext());
        colorGrid.setAdapter(colorGridAdapter);
        colorGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ClickedOn", BackgroundColors.colorNames[position]);
                listener.onColorClicked(BackgroundColors.colorNames[position]);

            }
        });
        return view;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
