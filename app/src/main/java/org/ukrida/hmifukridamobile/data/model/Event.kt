package org.ukrida.hmifukridamobile.data.model

import com.google.gson.annotations.SerializedName

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    @SerializedName("event_date") val eventDate: String,
    @SerializedName("created_by") val createdBy: Int,
    @SerializedName("created_at") val createdAt: String
)
