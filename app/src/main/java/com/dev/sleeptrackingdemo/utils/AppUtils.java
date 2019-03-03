package com.dev.sleeptrackingdemo.utils;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import com.dev.sleeptrackingdemo.R;
import com.dev.sleeptrackingdemo.application.SundayApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class AppUtils {
    private static AppUtils instance;

    private AppUtils() {

    }

    public static AppUtils getInstance() {
        if (instance == null) {
            synchronized (AppUtils.class) {
                if (instance == null) {
                    instance = new AppUtils();
                }
            }
        }
        return instance;
    }

    public Object getValueFromData(Object data) {
        return (data == null) ? "" : capitalizeString(data.toString());
    }

    private String capitalizeString(String s) {
        if (s != null && s.length() > 0) {
            return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
        } else
            return s;
    }

    public void showToast(String message) {
        if (AppUtils.isNotEmptyNotNull(message)) {
            Toast.makeText(SundayApp.getAppContext(), message, Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(SundayApp.getAppContext(), SundayApp.getAppContext().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
    }

    private static boolean isNotEmptyNotNull(String text) {
        return text != null && !text.isEmpty();
    }

    @SuppressLint("SimpleDateFormat")
    private int getTimeDifference(String time, String t) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.KEY_APP_DATE_FORMAT);
        int timeAgo = 0;
        try {
            Date date = dateFormat.parse(time);
            Date currentDate = dateFormat.parse(t);
            long difference = currentDate.getTime() - date.getTime();
            long seconds = difference / 1000;
            long minutes = seconds / 60;
            long hour = minutes / 60;
            timeAgo = (int) minutes;
        } catch (Exception e) {
            Log.d("TAG", "Current time ex : " + e.getMessage());
        }
        return timeAgo;
    }

    public List<String> getHourDataFromSmsList(List<String> smsList) {
        List<String> t = new ArrayList<>();
        for (int i = 0; i < smsList.size() - 1; i++) {
            int timeDiff = getTimeDifference(smsList.get(i), smsList.get(i + 1));
            if (timeDiff > 1) {
                t.add(smsList.get(i) + " - " + smsList.get(i + 1));
            }
        }
        return t;
    }
}