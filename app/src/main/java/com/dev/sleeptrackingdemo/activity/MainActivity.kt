package com.dev.sleeptrackingdemo.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dev.sleeptrackingdemo.R
import com.dev.sleeptrackingdemo.sleeptrack.MotionSensorLogger
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var gpsService: MotionSensorLogger? = null
    private var m_serviceBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        viewGraphButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(this@MainActivity, ViewSleepActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(applicationContext, MotionSensorLogger::class.java)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        startService(intent)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val name = className.className
            if (name.endsWith("MotionSensorLogger")) {
                m_serviceBound = true
                gpsService = (service as MotionSensorLogger.LocationServiceBinder).service
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {
            if (className.className == "MotionSensorLogger") {
                gpsService = null
                m_serviceBound = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (m_serviceBound) {
            unbindService(serviceConnection)
            m_serviceBound = false
        }
    }
}
