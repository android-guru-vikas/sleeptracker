package com.dev.sleeptrackingdemo.sleeptrack

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dev.sleeptrackingdemo.R
import com.dev.sleeptrackingdemo.activity.MainActivity

class AlarmService : Service() {
    private var alarmNotificationManager: NotificationManager? = null
    internal lateinit var ringtone: Ringtone


    override fun onDestroy() {
        Log.d("alarmService", "stopping alarm")
        ringtone.stop()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        sendNotification("Wake Up! Wake Up!")
        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        this.ringtone = RingtoneManager.getRingtone(this, alarmUri)
        ringtone.play()
        Log.d("alarmService", "starting alarm")
        return Service.START_NOT_STICKY
    }

    private fun sendNotification(msg: String) {
        Log.d("AlarmService", "Preparing to send notification...: $msg")
        alarmNotificationManager = this
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val contentIntent = PendingIntent.getActivity(this, 0,
                Intent(this, MainActivity::class.java), 0)

        val alarmNotificationBuilder = NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)
                .setDefaults(Notification.DEFAULT_VIBRATE)

        alarmNotificationBuilder.setContentIntent(contentIntent)
        alarmNotificationManager!!.notify(1, alarmNotificationBuilder.build())
        Log.d("AlarmService", "Notification sent.")
    }
}
