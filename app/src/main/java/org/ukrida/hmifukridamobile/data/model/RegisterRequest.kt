package org.ukrida.hmifukridamobile.data.model

data class RegisterRequest(
    val name: String,
    val nim: String,
    val email: String,
    val password: String
)