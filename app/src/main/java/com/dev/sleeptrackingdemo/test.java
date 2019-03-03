package com.dev.sleeptrackingdemo;

import android.util.Log;

public class test {

    String time = "23:52:50.267";

    void getIntTime() {
        int t = Integer.parseInt(time.substring(0, 2));
        Log.d("TAG", "Inside log time : " + t);
    }
}
