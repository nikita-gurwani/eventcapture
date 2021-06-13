package com.ng.event_capture.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ng.event_capture.R
import com.ng.event_capture.data.AnalyticsEventDao
import com.ng.event_capture.model.AnalyticsTrackingDbManager
import com.ng.event_capture.ui.adapter.AllEventsAdapter
import kotlinx.android.synthetic.main.activity_analyics_event.*
import kotlinx.android.synthetic.main.activity_analyics_event_details.*
import kotlinx.android.synthetic.main.search_layout.*

class AnalyticsEventsListActivity : AppCompatActivity() {

    private lateinit var dbManager: AnalyticsTrackingDbManager
    private lateinit var allEventsAdapter: AllEventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyics_event)
        setUpViews()
        AnalyticsTrackingDbManager.instance?.let {
            dbManager = it
        }
        dbManager.fetchAllEvents()
        dbManager.allEventsList.observe(this, Observer {
            setupRecyclerView(it)
        })
        handleSearchView()
        handleShareButton()
    }

    private fun setUpViews() {
        val toolbar = findViewById<Toolbar>(R.id.titleText)
        toolbar.title = getString(R.string.action_bar_txt)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primaryColor))
        setSupportActionBar(toolbar)
    }

    private fun setupRecyclerView(fetchAllEvents: List<AnalyticsEventDao>) {
        eventList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        allEventsAdapter = AllEventsAdapter(this, dbManager.sort(fetchAllEvents))
        eventList.adapter = allEventsAdapter
        allEventsAdapter.notifyDataSetChanged()
        addItemClickListener()
    }

    private fun addItemClickListener() {
        allEventsAdapter.onItemClick = { events: AnalyticsEventDao ->
            val intent = Intent(this, AnalyticsEventsDetailsActivity::class.java)
            intent.putExtra(AnalyticsEventsDetailsActivity.eventNameArg, events.eventName)
            intent.putExtra(AnalyticsEventsDetailsActivity.eventIdArg, events.id)
            startActivity(intent)
        }

        allEventsAdapter.onDeleteClick = {
            dbManager.deleteParticularData(it)
        }
    }

    private fun handleSearchView() {
        dbManager.filteredList.observe(this, Observer {
            setupRecyclerView(it)
        })

        searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Handler().postDelayed({
                    dbManager.searchEvents(s.toString(), dbManager.allEventsList)
                }, 500)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        deleteAll.setOnClickListener {
            dbManager.deleteAllEvents()
        }
    }

    private fun handleShareButton() {
        shareJsonAll.setOnClickListener {
            dbManager.eventPropertiesList.value.let {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, dbManager.getAllEventPropertiesAsText());
                startActivity(intent);
            }

        }
    }
}
