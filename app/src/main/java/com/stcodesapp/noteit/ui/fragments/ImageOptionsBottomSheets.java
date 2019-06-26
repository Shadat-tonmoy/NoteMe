package com.stcodesapp.noteit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.EventTypes;

public class ImageOptionsBottomSheets extends BottomSheetDialogFragment {

    public interface Listener{
        void onImageOptionSelected(int imageOption);

    }

    private Listener listener;
    private TextView takePhotoOption, pickFromFilesOption;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_option_bottom_sheet_layout,container,false);
        inflateUIElements(view);
        initUserInteractions();
        return view;
    }

    private void inflateUIElements(View view)
    {
        takePhotoOption = view.findViewById(R.id.capture_image_option);
        pickFromFilesOption = view.findViewById(R.id.pick_from_files_option);

    }

    private void initUserInteractions()
    {
        setClickListener(takePhotoOption,EventTypes.TAKE_PHOTO_OPTION_CLICKED);
        setClickListener(pickFromFilesOption,EventTypes.PICK_IMAGE_FROM_FILE_OPTION_CLICKED);

    }

    private void setClickListener(View view, final int eventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageOptionSelected(eventType);
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
