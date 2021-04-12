package com.ng.event_capture.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ng.event_capture.R
import com.ng.event_capture.model.AnalyticsTrackingDbManager
import com.ng.event_capture.ui.adapter.AllEventsDetailsAdapter
import kotlinx.android.synthetic.main.activity_analyics_event_details.*

class AnalyticsEventsDetailsActivity : BaseAnalyticsActivity() {

    private lateinit var dbManager: AnalyticsTrackingDbManager
    private lateinit var allEventsDetailsAdapter: AllEventsDetailsAdapter
    private lateinit var eventName: String

    companion object {
        var eventNameArg = "event_name_arg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyics_event_details)
        handleIntentArgs()
        AnalyticsTrackingDbManager.instance?.let {
            dbManager = it
        }
        dbManager.fetchAllEventProperties(eventName)
        setupRecyclerView()
        handleShareButton()
    }

    private fun handleShareButton() {
        shareAll.setOnClickListener {
            dbManager.eventPropertiesList.value.let {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, dbManager.getEventPropertiesAsText(it));
                startActivity(intent);
            }

        }
    }

    private fun handleIntentArgs() {
        intent?.let { intent ->
            intent.getStringExtra(eventNameArg)?.let {
                eventName = it
                detailTitleText.text = eventName
            }
        }
    }

    private fun setupRecyclerView() {
        dbManager.eventPropertiesList.observe(this, Observer {
            eventDetailsList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            allEventsDetailsAdapter = AllEventsDetailsAdapter(this, it)
            eventDetailsList.adapter = allEventsDetailsAdapter
            allEventsDetailsAdapter.notifyDataSetChanged()
        })

    }

    override fun onResume() {
        super.onResume()
        dbManager.dismiss()
    }
}
