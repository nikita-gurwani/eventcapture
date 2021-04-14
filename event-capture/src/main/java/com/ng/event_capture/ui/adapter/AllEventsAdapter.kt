package com.ng.event_capture.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ng.event_capture.R
import com.ng.event_capture.data.AnalyticsEventDao
import kotlinx.android.synthetic.main.list_item_analyics_event.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

class AllEventsAdapter(private val context: Context, private var events: List<AnalyticsEventDao>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((events: AnalyticsEventDao) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_analyics_event, parent, false)
        return AllEventsNameAdapterViewHolder(view)
    }

    @ExperimentalTime
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AllEventsNameAdapterViewHolder -> {
                events[position].let {
                    holder.bind(it.eventName, it.timeStamp)
                }
            }
        }
    }

    override fun getItemCount() = events.size

    inner class AllEventsNameAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                onItemClick?.invoke(events[adapterPosition])
            }
        }

        @ExperimentalTime
        fun bind(name: String, time: String) {
            itemView.eventNameText.text = name
            itemView.timeFired.text = handleTime(time)
        }
    }

    @ExperimentalTime
    fun handleTime(time: String): String {
        try {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.getDefault())
            val date = SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.getDefault()).format(Date())
            val diff = dateFormat.parse(date).time - dateFormat.parse(time).time
            val dateDifference = DurationUnit.MILLISECONDS.convert(diff, DurationUnit.MILLISECONDS)
            if (TimeUnit.MILLISECONDS.toSeconds(dateDifference) < 60) {
                println("Seconds " + TimeUnit.MILLISECONDS.toSeconds(dateDifference))
                return TimeUnit.MILLISECONDS.toSeconds(dateDifference).toString() + "sec"
            } else if (TimeUnit.MILLISECONDS.toMinutes(dateDifference) < 60) {
                println("Min " + TimeUnit.MILLISECONDS.toMinutes(dateDifference))
                return TimeUnit.MILLISECONDS.toMinutes(dateDifference).toString() + "min"
            } else if (TimeUnit.MILLISECONDS.toHours(dateDifference) < 24) {
                println("Hours " + TimeUnit.MILLISECONDS.toHours(dateDifference))
                return TimeUnit.MILLISECONDS.toHours(dateDifference).toString() + "hr"
            }else {
                return TimeUnit.MILLISECONDS.toDays(dateDifference).toString() + "days"
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return time
        }
    }

}