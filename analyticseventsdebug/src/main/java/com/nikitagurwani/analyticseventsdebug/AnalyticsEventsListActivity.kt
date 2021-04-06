package com.nikitagurwani.analyticseventsdebug

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_analyics_event.*
import kotlinx.android.synthetic.main.search_layout.*

class AnalyticsEventsListActivity : AppCompatActivity() {

    private lateinit var dbManager: AnalyticsTrackingDbManager
    private lateinit var allEventsAdapter: AllEventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyics_event)
        dbManager = AnalyticsTrackingDbManager.instance
        setupRecyclerView(dbManager.fetchAllEvents())
        handleSearchView()
    }

    private fun setupRecyclerView(fetchAllEvents: List<AnalyticsEventDao>) {
        eventList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        allEventsAdapter = AllEventsAdapter(this, fetchAllEvents)
        eventList.adapter = allEventsAdapter
        addItemClickListener()
    }

    private fun addItemClickListener() {
        allEventsAdapter.onItemClick = { events: AnalyticsEventDao ->
            val intent = Intent(this, AnalyticsEventsDetailsActivity::class.java)
            intent.putExtra(AnalyticsEventsDetailsActivity.eventNameArg, events.eventName)
            startActivity(intent)
        }
    }

    private fun handleSearchView() {
        dbManager.filteredList.observe(this, Observer {
            setupRecyclerView(it)
        })

        searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Handler().postDelayed({
                    dbManager.searchEvents(s.toString(), dbManager.fetchAllEvents())
                }, 500)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        deleteAll.setOnClickListener {

        }
    }
}
