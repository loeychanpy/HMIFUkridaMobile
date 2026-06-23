package org.ukrida.hmifukridamobile.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val name: String,
    val nim: String,
    val email: String,
    val role: String
)