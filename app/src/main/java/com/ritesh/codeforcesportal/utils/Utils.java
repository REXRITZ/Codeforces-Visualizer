package com.ritesh.codeforcesportal.utils;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    @NonNull
    @SuppressLint("DefaultLocale")
    public static String formatTimeInSeconds(Integer seconds) {
        String hours = String.format("%02d",seconds/3600) + ":";
        String minutes = String.format("%02d",(seconds/3600)%60);
        return hours+minutes;
    }

    @NonNull
    public static String formatContestStartTime(Integer startTime) {
//        startTime is in unix
        long time = startTime*1000L;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault());
        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        dateFormat.applyPattern("dd MMMM yyyy HH:mm:ss");
        return dateFormat.format(new Date(time));
    }
}
