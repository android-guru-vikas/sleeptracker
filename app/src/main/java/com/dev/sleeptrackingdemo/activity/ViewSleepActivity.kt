package com.dev.sleeptrackingdemo.activity

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.sleeptrackingdemo.R
import com.dev.sleeptrackingdemo.adapter.SleepAdapter
import com.dev.sleeptrackingdemo.utils.AppUtils
import com.dev.sleeptrackingdemo.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class ViewSleepActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_sleep)
        val recyclerView = findViewById<View>(R.id.sleepRecyclerView) as RecyclerView
        val mydatabase = SQLiteDatabase.openDatabase(Constants.KEY_APP_DB_PATH, null, Context.MODE_PRIVATE)
        val resultSet = mydatabase.rawQuery(Constants.KEY_APP_DB_QUERY, null)
        val sdf = SimpleDateFormat(Constants.KEY_APP_DATE_FORMAT)
        resultSet.moveToFirst()
        val timeList = ArrayList<String>()
        while (!resultSet.isAfterLast) {
            Log.d(javaClass.name, sdf.format(Date(resultSet.getLong(0))))
            val time = sdf.format(Date(resultSet.getLong(0)))
            timeList.add(time)
            resultSet.moveToNext()
        }
        resultSet.close()

        val time = AppUtils.getInstance().getHourDataFromSmsList(timeList)
        val resId = com.dev.sleeptrackingdemo.R.anim.layout_anim_fall_down
        val adapter = SleepAdapter(time, this)
        val animation = AnimationUtils.loadLayoutAnimation(this, resId)
        recyclerView.layoutAnimation = animation
        recyclerView.layoutManager = LinearLayoutManager(this@ViewSleepActivity)
        recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
