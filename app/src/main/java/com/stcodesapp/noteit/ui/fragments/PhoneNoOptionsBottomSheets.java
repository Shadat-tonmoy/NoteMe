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
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.ColorGridAdapter;
import com.stcodesapp.noteit.constants.BackgroundColors;
import com.stcodesapp.noteit.constants.EventTypes;

public class PhoneNoOptionsBottomSheets extends BottomSheetDialogFragment {

    public interface Listener{
        void onPhoneNoOptionSelected(int phoneNoOption);

    }

    private Listener listener;
    private TextView manualPhoneNumberOption, pickFromContactOption;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phone_no_option_bottom_sheet_layout,container,false);
        inflateUIElements(view);
        initUserInteractions();
        return view;
    }

    private void inflateUIElements(View view)
    {
        manualPhoneNumberOption = view.findViewById(R.id.manual_phone_number_option);
        pickFromContactOption = view.findViewById(R.id.pick_from_contact_option);

    }

    private void initUserInteractions()
    {
        setClickListener(manualPhoneNumberOption,EventTypes.MANUAL_PHONE_NO_OPTION_CLICKED);
        setClickListener(pickFromContactOption,EventTypes.PICK_FROM_CONTACT_OPTION_CLICKED);

    }

    private void setClickListener(View view, final int eventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhoneNoOptionSelected(eventType);
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
