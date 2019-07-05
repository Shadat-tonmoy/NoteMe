package com.stcodesapp.noteit.tasks.functionalTasks.phoneFunctionAccessTasks;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.RequestCode;

import static android.app.Activity.RESULT_OK;

public class VoiceRecordTask {

    private Activity activity;

    public VoiceRecordTask(Activity activity) {
        this.activity = activity;
    }

    public void startVoiceRecorder()
    {
        try {
            Intent recordIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            activity.startActivityForResult(recordIntent, RequestCode.OPEN_VOICE_RECORDER);    
        } catch (Exception e)
        {
            Toast.makeText(activity, activity.getResources().getString(R.string.audio_record_not_supported), Toast.LENGTH_SHORT).show();
        }
        
    }
}
