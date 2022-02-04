package com.ng.analyticecentsanalyzer.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ng.analyticecentsanalyzer.sample.databinding.ActivityMainBinding
import com.ng.event_capture.EventsCapture
import com.ng.event_capture.EventsVerifier


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val event1 = "Button click 1"
        val event2 = "Button click 2"
        val event3 = "Button click 3"
        val value1 = mapOf("Event from Button 1" to "Record event 1")
        val value2 = mapOf("Event from Button 2" to "Record event 2")
        val value3 = mapOf("Event from Button 3" to "Record event 3")
        binding.sendEvent1.setOnClickListener {
            EventsCapture.captureEvent(event1, value1)
        }
        binding.verifyEvent1.setOnClickListener {
            val verifyResult = EventsVerifier.verifyEvent(event1, value1)
            processResult(verifyResult.first, event1)
        }

        binding.sendEvent2.setOnClickListener {
            EventsCapture.captureEvent(event2, value2)
        }
        binding.verifyEvent2.setOnClickListener {
            val verifyResult = EventsVerifier.verifyEvent(event2, value2)
            processResult(verifyResult.first, event2)
        }

        binding.sendEvent3.setOnClickListener {
            EventsCapture.captureEvent(event3, value3)
        }
        binding.verifyEvent3.setOnClickListener {
            val verifyResult = EventsVerifier.verifyEvent(event3, value3)
            processResult(verifyResult.first, event3)
        }

        binding.startActivityBtn.setOnClickListener {
            EventsCapture.start(this@MainActivity)
        }
    }

    private fun processResult(verified: Boolean, action: String) {
        val msg = if (verified) "$action Verified"
        else "Event not found"
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }
}