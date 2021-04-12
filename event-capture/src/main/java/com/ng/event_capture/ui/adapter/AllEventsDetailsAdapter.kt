package com.ng.event_capture.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ng.event_capture.R
import kotlinx.android.synthetic.main.details_item_analyics_event.view.*

class AllEventsDetailsAdapter(private val context: Context, private var eventProperties: HashMap<String, String>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.details_item_analyics_event, parent, false)
        return AllEventsNameAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AllEventsNameAdapterViewHolder -> {
                val eventProperty: Map<String, String> = LinkedHashMap(eventProperties)
                val key: String = ArrayList(eventProperty.keys)[position]
                holder.bind(key, eventProperty[key])
            }
        }
    }

    override fun getItemCount() = eventProperties.size

    inner class AllEventsNameAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(key: String, value: String?) {
            itemView.eventKey.text = key
            itemView.eventValue.text = value
        }
    }

}