package org.ukrida.hmifukridamobile.data.model

import com.google.gson.annotations.SerializedName

data class EventRegistrant(
    val id: Int,
    @SerializedName("registration_id") val registrationId: Int,
    val name: String,
    val nim: String,
    val email: String,
    @SerializedName("registered_at") val registeredAt: String,
    val attended: Boolean = false
)