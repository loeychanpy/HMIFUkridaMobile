package org.ukrida.hmifukridamobile.data.model

import com.google.gson.annotations.SerializedName

data class Announcement(
    val id: Int,
    val title: String,
    val content: String,
    @SerializedName("created_by") val createdBy: Int,
    @SerializedName("created_at") val createdAt: String
)