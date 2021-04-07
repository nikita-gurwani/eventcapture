package com.ng.analyticecentsanalyzer.sample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ng.analyticseventsanalyzer.data.AnalyticsEventsTracker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var values: HashMap<String, String>
        sendEvent1.setOnClickListener {
            values = HashMap()
            values["Event from Button 1"] = "Record event 1"
            handleDbEvent("Button click 1", values)
        }

        sendEvent2.setOnClickListener {
            values = HashMap()
            values["Event from Button 2"] = "Record event 2"
            handleDbEvent("Button click 2", values)
        }

        sendEvent3.setOnClickListener {
            values = HashMap()
            values["Event from Button 3"] = "Record event 3"
            handleDbEvent("Button click 3", values)
        }

        startActivityBtn.setOnClickListener {
            startActivityAnalyticsEvent(this)
        }
    }

    fun handleDbEvent(name: String, properties: Map<String, String>) {
        AnalyticsEventsTracker.eventTracker.handleDbEvent(name, properties)
    }

    fun startActivityAnalyticsEvent(context: Context) {
        AnalyticsEventsTracker.startActivityTracker.startActivityAnalyticsEvent(context)
    }
}
