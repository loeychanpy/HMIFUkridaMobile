package org.ukrida.hmifukridamobile.data.model

data class AuthResponse(
    val status: String,
    val token: String?,
    val data: User?,
    val message: String?
)