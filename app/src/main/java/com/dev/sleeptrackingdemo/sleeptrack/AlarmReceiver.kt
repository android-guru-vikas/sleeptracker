package com.dev.sleeptrackingdemo.sleeptrack

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast

import androidx.legacy.content.WakefulBroadcastReceiver

class AlarmReceiver : WakefulBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Alarm! Wake up!", Toast.LENGTH_SHORT).show()
        //this will send a notification message
        val comp = ComponentName(context.packageName, AlarmService::class.java.name)
        WakefulBroadcastReceiver.startWakefulService(context, intent.setComponent(comp))
        resultCode = Activity.RESULT_OK
    }

}