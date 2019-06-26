package com.stcodesapp.noteit.constants;

public class AppMetadata {

    public static final int INITIAL_FREE_CHARS = 1000;
    private static final int ADMOB_REWARDED_PRIORITY = 1;
    private static final int UNITY_REWARDED_PRIORITY = 2;
    public static final int MAX_NOTE_TO_SHOW_RATEUS_POPUP = 3;
    private static boolean USAGE_BALANCE_UPDATED = false;
    private static boolean NEW_BALANCE_ADDED = false;
    private static boolean BROADCAST_RECEIVER_SET = false;
    public static final String FIRST_TIME_TRACKER_NAME = "noteit_first";
    public static final String RATE_US_POPUP_TRACKER_NAME = "rateus_popup";
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public static final String IS_RATE_US_CLICKED = "israteclicked";
    public static final String TOTAL_NOTE = "totalnote";

    public static final String[] TTS_SUPPORTED_LANGUAGES = {"bn","yue","zh","cs","da","nl","en","et","fil","fi","fr","de","el","hi","hu","in","it","ja","jv","km","ko","ne","nb",
    "pl","pt","ro","ru","si","sk","es","su","sv","th","tr","uk","vi"};

    public static void setNewBalanceAdded(boolean newBalanceAdded) {
        NEW_BALANCE_ADDED = newBalanceAdded;
//        Log.e("Updating","NewBalanceAdded "+newBalanceAdded);
    }

    public static boolean isUsageBalanceUpdated() {
        return USAGE_BALANCE_UPDATED;
    }

    public static void setUsageBalanceUpdated(boolean usageBalanceUpdated) {
        USAGE_BALANCE_UPDATED = usageBalanceUpdated;
//        Log.e("Updating","UsageBalance "+usageBalanceUpdated);
    }

    public static boolean isNewBalanceAdded() {
        return NEW_BALANCE_ADDED;
    }

    public static void setBroadcastReceiverSet(boolean broadcastReceiverSet) {
        BROADCAST_RECEIVER_SET = broadcastReceiverSet;
    }

    public static boolean isBroadcastReceiverSet() {
        return BROADCAST_RECEIVER_SET;
    }

    public static int getAdmobRewardedPriority() {
        return ADMOB_REWARDED_PRIORITY;
    }

    public static int getUnityRewardedPriority() {
        return UNITY_REWARDED_PRIORITY;
    }


}
