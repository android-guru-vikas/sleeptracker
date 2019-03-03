package com.dev.sleeptrackingdemo.activity

import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import com.dev.sleeptrackingdemo.R
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_graph.*
import java.text.SimpleDateFormat
import java.util.*

class GraphActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        val r = resources
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val mydatabase = SQLiteDatabase.openDatabase("/data/data/com.dev.sleeptrackingdemo/databases/sleep_data", null, Context.MODE_PRIVATE)
        val resultSet = mydatabase.rawQuery("Select * from sensor_data", null)
        val sdf = SimpleDateFormat("HH:mm:ss.SSS")
        resultSet.moveToFirst()
        val accelX = ArrayList<Entry>()
        val accelY = ArrayList<Entry>()
        val accelZ = ArrayList<Entry>()
        val xVals = ArrayList<String>()
        var j = 0
        while (!resultSet.isAfterLast) {
            Log.d("Time", sdf.format(Date(resultSet.getLong(0))))

            for (i in 1..3) {
                Log.d("Accel", java.lang.Float.toString(resultSet.getFloat(i)))
            }

            accelX.add(Entry(resultSet.getFloat(1), j))
            accelY.add(Entry(resultSet.getFloat(2), j))
            accelZ.add(Entry(resultSet.getFloat(3), j))
            xVals.add(Integer.toString(j))
            j++
            resultSet.moveToNext()
        }
        val Xset = LineDataSet(accelX, "X Axis")
        Xset.axisDependency = YAxis.AxisDependency.LEFT
        Xset.color = r.getColor(R.color.red)
        Xset.lineWidth = 1.0f
//        val Yset = LineDataSet(accelY, "Y Axis")
//        Yset.axisDependency = YAxis.AxisDependency.LEFT
//        Yset.color = r.getColor(R.color.colorPrimary)
//        Yset.lineWidth = 3.0f
//        val Zset = LineDataSet(accelZ, "Z Axis")
//        Zset.axisDependency = YAxis.AxisDependency.LEFT
//        Zset.color = r.getColor(R.color.green)
//        Zset.lineWidth = 3.0f

        val dataSets = ArrayList<LineDataSet>()
        dataSets.add(Xset)
//        dataSets.add(Yset)
//        dataSets.add(Zset)
        val data = LineData(xVals, dataSets)
        chart.data = data
        chart.isDragEnabled = true
        chart.setTouchEnabled(true)
        chart.invalidate()
    }
}
