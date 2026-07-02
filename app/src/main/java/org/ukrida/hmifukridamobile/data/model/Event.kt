package org.ukrida.hmifukridamobile.data.model

import com.google.gson.annotations.SerializedName

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val category: String = "",
    @SerializedName("event_date") val eventDate: String,
    @SerializedName("created_by") val createdBy: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("participant_count") val participantCount: Int = 0,
    @SerializedName("registration_id") val registrationId: Int? = null
)
