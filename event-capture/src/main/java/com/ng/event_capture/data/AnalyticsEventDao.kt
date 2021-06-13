package com.ng.event_capture.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "AnalyticsEventTable")
data class AnalyticsEventDao(@ColumnInfo(name = "eventName") @SerializedName("eventName") var eventName: String = "",
                             @ColumnInfo(name = "timeStamp") @SerializedName("timeStamp") var timeStamp: String = " ",
                             @ColumnInfo(name = "eventProperties") @SerializedName("eventProperties") var eventProperties: String = "",
                             @PrimaryKey(autoGenerate = true) val id: Long) {
    constructor(eventName: String, timeStamp: String, eventProperties: String) : this(eventName, timeStamp, eventProperties, 0)

}