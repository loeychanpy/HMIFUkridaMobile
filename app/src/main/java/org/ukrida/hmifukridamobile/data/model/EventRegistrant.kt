package org.ukrida.hmifukridamobile.data.model

import com.google.gson.annotations.SerializedName

data class EventRegistrant(
    val id: Int,
    val name: String,
    val nim: String,
    val email: String,
    @SerializedName("registered_at") val registeredAt: String
)