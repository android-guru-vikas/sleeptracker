package com.dev.sleeptrackingdemo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.sleeptrackingdemo.R
import com.dev.sleeptrackingdemo.design.SundayTextView
import com.dev.sleeptrackingdemo.utils.AppUtils

class SleepAdapter(private val mItemList: List<String>?, private val context: Context) : RecyclerView.Adapter<SleepAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_recycler_view_layout, parent, false)
        return ViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItemList!![position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return mItemList?.size ?: 0
    }

    inner class ViewHolder(itemView: View, ViewType: Int) : RecyclerView.ViewHolder(itemView) {
        var timeTextView: SundayTextView
        var dateTextView: SundayTextView

        init {
            timeTextView = itemView.findViewById<View>(R.id.timeTextView) as SundayTextView
            dateTextView = itemView.findViewById<View>(R.id.dateTextView) as SundayTextView
        }

        fun bind(item: String) {
            try {
                val sleepTime = AppUtils.getInstance().getValueFromData(item).toString()
                val fromDate = sleepTime.substring(8, 10)
                val toDate = sleepTime.substring(27, 29)
                if (fromDate.equals(toDate, ignoreCase = true)) {
                    dateTextView.setText(sleepTime.substring(0, 10))
                } else {
                    dateTextView.setText(sleepTime.substring(0, 10) + " - " + sleepTime.substring(19, 29))
                }
                timeTextView.setText(sleepTime.substring(10, 18) + "" + sleepTime.substring(29))
            } catch (e: Exception) {
                Log.e(TAG, "Inside ex : " + e.message)
            }

        }
    }

    companion object {
        private val TAG = "SleepAdapter"
    }
}
