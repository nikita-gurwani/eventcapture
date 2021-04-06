package com.ng.analyticeventsanalyzer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ng.analyticeventsanalyzer.ui.adapter.AllEventsDetailsAdapter
import com.ng.analyticeventsanalyzer.model.AnalyticsTrackingDbManager
import com.nikitagurwani.analyticstracker.R
import kotlinx.android.synthetic.main.activity_analyics_event_details.*

class AnalyticsEventsDetailsActivity : AppCompatActivity() {

    private lateinit var dbManager: AnalyticsTrackingDbManager
    private lateinit var allEventsDetailsAdapter: AllEventsDetailsAdapter
    private lateinit var eventName: String
    companion object { var eventNameArg = "event_name_arg" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyics_event_details)
        handleIntentArgs()
        dbManager = AnalyticsTrackingDbManager.instance
        setupRecyclerView()
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
        eventDetailsList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        allEventsDetailsAdapter = AllEventsDetailsAdapter(this, dbManager.fetchAllEventProperties(eventName))
        eventDetailsList.adapter = allEventsDetailsAdapter
    }
}
