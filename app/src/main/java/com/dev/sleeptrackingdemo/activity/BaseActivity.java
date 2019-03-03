package com.dev.sleeptrackingdemo.activity;

import android.content.Context;
import android.os.Bundle;

import com.dev.sleeptrackingdemo.utils.AppLogs;
import com.dev.sleeptrackingdemo.utils.AppToast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    public Context pContext;
    public AppToast pAppToast;
    public AppLogs pAppLogs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initObjects();
    }

    private void initObjects() {
        pContext = this;
        pAppToast = AppToast.getInstance();
        pAppLogs = AppLogs.getInstance();
    }
}
