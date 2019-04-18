package com.stcodesapp.noteit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.ColorGridAdapter;
import com.stcodesapp.noteit.constants.BackgroundColors;

public class PhoneNoOptionsBottomSheets extends BottomSheetDialogFragment {

    public interface Listener{

    }

    private Listener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phone_no_option_bottom_sheet_layout,container,false);

        return view;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
