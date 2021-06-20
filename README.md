
# Event Capture
## _Analytical Events Analysis_

[![](https://jitpack.io/v/nikita-gurwani/androidEventAnalyzer.svg)](https://jitpack.io/#nikita-gurwani/androidEventAnalyzer)

EventCapture is a library to analyze the analytics event for any Android app. EventCapture saves all the event fired from your application provides a UI for inspecting their content.

<p float="left">
<img src="https://user-images.githubusercontent.com/14894852/122685880-ddb53880-d22b-11eb-8598-91d6f4806c77.png" width="250" height = "500">
<img src="https://user-images.githubusercontent.com/14894852/122685889-e4dc4680-d22b-11eb-8711-d2bfffddf954.png" width="250" height = "500">
<img src="https://user-images.githubusercontent.com/14894852/122686200-9d56ba00-d22d-11eb-8345-3f0a41575191.png" width="250" height = "500">
</p>

## Features

- Apps using EventCapture will display a notification showing events sent. Tapping on the notification to your app screen. 
- Apps can launch the EventCapture UI directly from within their own interface. Events and their contents can be exported via a share intent.
- Search events
- When Session is closed the old events will get cleared automatically
- Delete all the events or delete the individual event
- Share the events and its content

## Setup

Based on your IDE you can import library in one of the following ways
Gradle:

Add the dependency in your build.gradle file.
```sh
debugImplementation 'com.github.nikita-gurwani:eventcapture:2.1.8'
```

If you want this in library in both release and debug builds, then try this :
```sh
implementation 'com.github.nikita-gurwani:eventcapture:2.1.8'
```

## Usage

Create a class
```sh
class AnalyticsEventsTracker {

    object EventTracker {
        private var dbManager: AnalyticsTrackingDbManager? = null

        @JvmStatic
        fun handleDbEvent(name: String, properties: Map<String, String>?) {
            AnalyticsTrackingDbManager.instance.let {
                dbManager = it
                dbManager?.insertAnalyticsEvent(name, properties ?: mutableMapOf())
            }
        }
    }

    object StartActivityTracker {
        fun startActivityAnalyticsEvent(context: Context) {
            context.startActivity(AnalyticsTrackingDbManager.instance?.getLaunchIntent(context))
        }
    }
}
```

Then call method to update the database
```sh
AnalyticsEventsTracker.EventTracker.handleDbEvent(name, properties)
```

Add option in your debug app settings and call the activity

```sh
AnalyticsEventsTracker.StartActivityTracker.startActivityAnalyticsEvent(this)
```

## License

Copyright (C) 2021 Nikita Gurwani.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


