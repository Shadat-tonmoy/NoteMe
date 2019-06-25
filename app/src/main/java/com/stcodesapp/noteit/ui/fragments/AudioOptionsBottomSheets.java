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

public class AudioOptionsBottomSheets extends BottomSheetDialogFragment {

    public interface Listener{
        void onAudioOptionSelected(int audioOption);

    }

    private Listener listener;
    private TextView recordAudioOption, pickFromFilesOption;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.audio_option_bottom_sheet_layout,container,false);
        inflateUIElements(view);
        initUserInteractions();
        return view;
    }

    private void inflateUIElements(View view)
    {
        recordAudioOption = view.findViewById(R.id.record_audio_option);
        pickFromFilesOption = view.findViewById(R.id.pick_from_files_option);

    }

    private void initUserInteractions()
    {
        setClickListener(recordAudioOption,EventTypes.RECORD_AUDIO_OPTION_CLICKED);
        setClickListener(pickFromFilesOption,EventTypes.PICK_AUDIO_FROM_FILE_OPTION_CLICKED);

    }

    private void setClickListener(View view, final int eventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAudioOptionSelected(eventType);
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
