package com.ritesh.codeforcesportal.utils;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.ritesh.codeforcesportal.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
    public static final Integer[] ratingXaxis = {500,1000,1500,2000,2500,3000,3500,4000};
    @NonNull
    @SuppressLint("DefaultLocale")
    public static String formatTimeInSeconds(Integer seconds) {
        Integer hour = seconds / 3600;
        Integer min = (seconds % 3600) / 60;
        return String.format("Length: %02d hr %02d min",hour, min);
    }

    @NonNull
    public static String formatContestStartTime(Integer startTime) {
//        startTime is in unix
        long time = startTime*1000L;
        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        dateFormat.applyPattern("dd MMMM yyyy HH:mm:ss");
        return "Start time: " + dateFormat.format(new Date(time));
    }

    public static int getRankColor(int rating) {
        int color;
        if(rating >= 0 && rating <= 1199)
            color = R.color.gray;
        else if (rating >= 1200 && rating <= 1399)
            color = R.color.green;
        else if (rating >= 1400 && rating <= 1599)
            color = R.color.cyan;
        else if (rating >= 1600 && rating <= 1899)
            color = R.color.blue;
        else if (rating >= 1900 && rating <= 2199)
            color = R.color.violet;
        else if (rating >= 2200 && rating <= 2399)
            color = R.color.orange;
        else
            color = R.color.red;
        return color;
    }
}
