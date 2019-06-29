package com.stcodesapp.noteit.tasks.functionalTasks.phoneFunctionAccessTasks;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

import com.stcodesapp.noteit.constants.RequestCode;

import static android.app.Activity.RESULT_OK;

public class VoiceRecordTask {

    private Activity activity;

    public VoiceRecordTask(Activity activity) {
        this.activity = activity;
    }

    public void startVoiceRecorder()
    {
        Intent recordIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        activity.startActivityForResult(recordIntent, RequestCode.OPEN_VOICE_RECORDER);
    }
}
