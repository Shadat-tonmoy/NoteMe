package com.stcodesapp.noteit.tasks.utilityTasks;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.stcodesapp.noteit.BuildConfig;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

public class UtilityTasks {

    private static final boolean DEBUG = BuildConfig.DEBUG;

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
        return Constants.DAYS_OF_WEEK[day-1]+" , "+addLeadingZero(date)+" "+ Constants.FULL_MONTHS[month]+" "+year+" "+addLeadingZero(hours)+":"+addLeadingZero(minutes)+":"+addLeadingZero(seconds)+" "+Constants.AM_PM[ampm];
    }

    public static long getCurrentTime()
    {
        return Calendar.getInstance().getTimeInMillis();
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

    public static void sendEmail(Activity activity,String emailId)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType(Constants.TEXT_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, Constants.EMPTY_STRING);
        intent.setData(Uri.parse(Constants.MAIL_TO+emailId));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static String getFileSizeString(double fileSize)
    {
        return String.format("%.2f",fileSize/Constants.MEGABYTE_DIVISOR )+ Constants.MEGABYTE;
    }
    public static String truncateText(String text,int length,String suffix)
    {
        if(text.length()>length)
        {
            text = text.substring(0,length);
            text += (Constants.DOTS+suffix);
            return text;
        }
        else return text;
    }

    public static String getFilePathFromURI(String uriPath)
    {
        return uriPath.substring(uriPath.indexOf(Constants.STORAGE));
    }

    public static boolean isValidString(String text)
    {
        return text!=null && !text.isEmpty() && text.length()>Constants.ZERO && !text.equals(Constants.EMPTY_STRING);
    }

    public static String getStoragePath(String rootPath)
    {
        return rootPath.substring(0,rootPath.indexOf(Constants.ANDROID));
    }

    public static String getBannerAdID(Activity activity)
    {
        if(DEBUG)
        {
            return activity.getResources().getString(R.string.admob_banner_ad_test);
        }
        else return activity.getResources().getString(R.string.admob_banner_ad_real);
    }

    public static String getInterstitialAdID(Activity activity)
    {
        if(DEBUG)
        {
            return activity.getResources().getString(R.string.admob_interstitial_ad_test);
        }
        else return activity.getResources().getString(R.string.admob_interstitial_ad_real);
    }

    public static String getRewardedVideAdID(Activity activity)
    {
        if(DEBUG)
        {
            return activity.getResources().getString(R.string.admob_rewarded_video_ad_test);
        }
        else return activity.getResources().getString(R.string.admob_rewarded_video_ad_real);
    }

    public static String getAdmobAppId(Activity activity)
    {
        if(DEBUG)
        {
            return activity.getResources().getString(R.string.admob_app_id_test);
        }
        else return activity.getResources().getString(R.string.admob_app_id_real);
    }

    public static String getTempDirectoryPath(Activity activity)
    {
        String tempDirPath = activity.getApplication().getFilesDir().getAbsolutePath()+Constants.TEMP_DIR_NAME;
        File tempDir = new File(tempDirPath);
        if(!tempDir.exists())
        {
            boolean result = tempDir.mkdirs();
            if(!result)
                return null;
        }
        return tempDir.getAbsolutePath();

    }

}
