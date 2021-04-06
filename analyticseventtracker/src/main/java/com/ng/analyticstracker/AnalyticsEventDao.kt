package com.ng.analyticstracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "AnalyticsEventTable")
data class AnalyticsEventDao(@PrimaryKey() @ColumnInfo(name = "eventName") @SerializedName("eventName") var eventName: String = "",
                             @ColumnInfo(name = "timeStamp") @SerializedName("timeStamp") var timeStamp: String = "",
                             @ColumnInfo(name = "eventProperties") @SerializedName("eventProperties") var eventProperties: String = "") {

}