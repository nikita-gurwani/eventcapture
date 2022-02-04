package com.ng.event_capture.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ng.event_capture.R
import com.ng.event_capture.application.EventCaptureApp
import com.ng.event_capture.databinding.ActivityAnalyicsEventDetailsBinding
import com.ng.event_capture.model.AnalyticsTrackingDbManager
import com.ng.event_capture.ui.adapter.AllEventsDetailsAdapter


internal class AnalyticsEventsDetailsActivity : BaseAnalyticsActivity() {

    private lateinit var binding: ActivityAnalyicsEventDetailsBinding

    private lateinit var dbManager: AnalyticsTrackingDbManager
    private lateinit var allEventsDetailsAdapter: AllEventsDetailsAdapter
    private lateinit var eventName: String

    companion object {
        var eventNameArg = "event_name_arg"
        var eventIdArg = "event_id_arg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyicsEventDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        handleIntentArgs()
        dbManager = EventCaptureApp.instance.dbManager
        handleEventId()
    }

    private fun handleEventId() {
        intent?.let {
            it.getLongExtra(eventIdArg, 0).let { eventId ->
                dbManager.fetchAllEventProperties(eventId)
                setupRecyclerView()
                handleShareButton()
            }
        }
    }

    private fun handleShareButton() {
        binding.shareAll.setOnClickListener {
            dbManager.eventPropertiesList.value.let {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    dbManager.getEventPropertiesAsText(eventName, it)
                )
                startActivity(intent)
            }
        }
    }

    private fun handleIntentArgs() {
        intent?.let { intent ->
            intent.getStringExtra(eventNameArg)?.let {
                eventName = it
                val toolbar = findViewById<Toolbar>(R.id.detailTitleText)
                toolbar.title = it
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primaryColor))
                setSupportActionBar(toolbar)
            }
        }
    }

    private fun setupRecyclerView() {
        dbManager.eventPropertiesList.observe(this) {
            binding.eventDetailsList.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            allEventsDetailsAdapter = AllEventsDetailsAdapter(this, it)
            binding.eventDetailsList.adapter = allEventsDetailsAdapter
            allEventsDetailsAdapter.notifyDataSetChanged()
        }

    }

    override fun onResume() {
        super.onResume()
        dbManager.dismiss()
    }
}
