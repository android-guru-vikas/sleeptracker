package com.dev.sleeptrackingdemo.sleeptrack

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dev.sleeptrackingdemo.R
import com.dev.sleeptrackingdemo.utils.Constants

class MotionSensorLogger : Service(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    internal lateinit var mydatabase: SQLiteDatabase
    private val binder = LocationServiceBinder()

    override fun onCreate() {
        super.onCreate()
        startForeground(12345678, getNotification())
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        mydatabase = openOrCreateDatabase(Constants.KEY_APP_DB_NAME, Context.MODE_PRIVATE, null)
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS sensor_data (timestamp INTEGER,accelx REAL,accely REAL,accelz REAL);")
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onDestroy() {
        Log.d(DEBUG_TAG, "Stopped")
        sensorManager!!.unregisterListener(this, sensor)
        super.onDestroy()
    }

    override fun onSensorChanged(event: SensorEvent) {
        SensorEventLoggerTask().execute(event)
    }

    @SuppressLint("SimpleDateFormat")
    private inner class SensorEventLoggerTask : AsyncTask<SensorEvent, Void, Void>() {
        override fun doInBackground(vararg events: SensorEvent): Void? {

            val sensorEvent = events[0]
            val timestamp = System.currentTimeMillis()
            val sensorThreshold = 0.05f
            var writeToDb = false

            val x = sensorEvent.values[0]
            if (x > sensorThreshold) {
                writeToDb = true
            }

            val y = sensorEvent.values[1]
            if (y > sensorThreshold) {
                writeToDb = true
            }

            val z = sensorEvent.values[2]
            if (z > sensorThreshold) {
                writeToDb = true
            }

            if (writeToDb) {
                Log.d(DEBUG_TAG, "Write significant movement data")
                mydatabase.execSQL("INSERT INTO sensor_data VALUES(?,?,?,?);", arrayOf(timestamp, x, y, z))
            }
            return null
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    inner class LocationServiceBinder : Binder() {
        val service: MotionSensorLogger get() = this@MotionSensorLogger
    }

    companion object {
        private val DEBUG_TAG = "MotionSensorLogger"
    }

    private fun getNotification(): Notification {
        val name = applicationContext.getString(R.string.app_name)
        var importance = 0
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH
        }
        val mNotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(applicationContext.getString(R.string.default_notification_channel_id), name, importance)
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }

        val notification = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.default_notification_channel_id))
                .setSmallIcon(android.R.drawable.sym_call_outgoing)
                .setContentText("SundayRest")
                .setContentTitle("Observing sleep...")
                .setAutoCancel(false)
                .setOngoing(true)
                .setChannelId(applicationContext.getString(R.string.default_notification_channel_id)).build()
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
        return notification
    }
}
