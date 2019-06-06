package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;
import com.stcodesapp.noteit.R;
public class VoiceInputTasks {

    private Activity activity;

    public VoiceInputTasks(Activity activity) {
        this.activity = activity;

    }

    public void showVoiceInputDialog(String languageCode,int requestCode)
    {

        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode);
            activity.startActivityForResult(intent, requestCode);

        }catch (ActivityNotFoundException e)
        {
            Toast.makeText(activity, activity.getResources().getString(R.string.voice_input_not_supported), Toast.LENGTH_SHORT).show();

        }

    }
}
