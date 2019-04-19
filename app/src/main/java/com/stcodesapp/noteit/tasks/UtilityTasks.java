package com.stcodesapp.noteit.tasks;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;

import java.util.Calendar;
import java.util.Random;

public class UtilityTasks {

    public static String getHumanReadableTime(long timeInMillis)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int ampm = calendar.get(Calendar.AM_PM);
        return Constants.DAYS_OF_WEEK[day-1]+" , "+addLeadingZero(date)+" "+ Constants.FULL_MONTHS[month]+" "+year+"\n"+addLeadingZero(hours)+":"+addLeadingZero(minutes)+":"+addLeadingZero(seconds)+" "+Constants.AM_PM[ampm];
    }

    public static String getRandomImage()
    {
        Random random = new Random();
        return "bg_"+random.nextInt(6);

    }

    public static String addLeadingZero(int n)
    {
        if(n<10)
            return "0"+n;
        return n+"";
    }

    public static void makeCall(Context context,String phoneNumber)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    public static void copyToClipboard(Context context,String text)
    {
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(context.getPackageName(), text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, context.getResources().getText(R.string.text_copied), Toast.LENGTH_SHORT).show();
    }
}
